package com.ylan.ylantakeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ylan.ylantakeaway.common.BaseContext;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.entity.ShoppingCart;
import com.ylan.ylantakeaway.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by ylan
 * @date 2022-12-24 17:02
 */

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class shoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 查询全部购物车信息
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        Long userId = BaseContext.getCurrentId();
        log.info("查询全部购物车信息{}", userId);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId).orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> addShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        // 设置用户ID
        shoppingCart.setUserId(BaseContext.getCurrentId());
        log.info("添加购物车{}", shoppingCart);

        // 查询器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
        // 查询购物车中是否存在该种类的菜品或者套餐
        if (shoppingCart.getDishId() != null) {
            // 购物车中为菜品
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }
        if (shoppingCart.getSetmealId() != null) {
            // 购物车中为套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart shoppingCartServiceOne = shoppingCartService.getOne(queryWrapper);

        if (shoppingCartServiceOne != null) {
            // 存在则数量加一
            shoppingCartServiceOne.setNumber(shoppingCartServiceOne.getNumber() + 1);
            shoppingCartService.updateById(shoppingCartServiceOne);
        } else {
            // 不存在则插入数据
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            shoppingCartServiceOne = shoppingCart;
        }

        return R.success(shoppingCartServiceOne);
    }

    /**
     * 数量减一
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart) {
        log.info("数量减一", shoppingCart);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        // 菜品
        if (shoppingCart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }
        // 套餐
        if (shoppingCart.getSetmealId() != null) {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart shoppingCartServiceOne = shoppingCartService.getOne(queryWrapper);

        if (shoppingCartServiceOne != null) {
            if (shoppingCartServiceOne.getNumber() > 1) {
                // 减一
                shoppingCartServiceOne.setNumber(shoppingCartServiceOne.getNumber() - 1);
                shoppingCartService.updateById(shoppingCartServiceOne);
            } else {
                // 直接移除
                shoppingCartService.removeById(shoppingCartServiceOne.getId());
            }
            return R.success("数量减一成功");
        }

        return R.error("数量减一失败");
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        Long userId = BaseContext.getCurrentId();
        log.info("清空全部购物车信息{}", userId);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        shoppingCartService.remove(queryWrapper);

        return R.success("清空购物车成功");
    }


}
