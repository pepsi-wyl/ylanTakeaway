package com.ylan.ylantakeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.dto.DishDto;
import com.ylan.ylantakeaway.entity.Dish;
import com.ylan.ylantakeaway.entity.DishFlavor;
import com.ylan.ylantakeaway.service.DishFlavorService;
import com.ylan.ylantakeaway.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品控制器
 *
 * @author by ylan
 * @date 2022-12-19 20:26
 */

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    /**
     * 注入服服务层类
     */
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto) {
        log.info("添加菜品{}", dishDto.toString());
        return dishService.addDish(dishDto);
    }

    /**
     * 分页查询菜品
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<DishDto>> pageList(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "name", required = false) String name) {
        log.info("查询菜品page={},pageSize={},name={}", page, pageSize, name);
        return dishService.pageList(page, pageSize, name);
    }

    /**
     * 根据ID查询菜品和口位
     *
     * @param id
     * @return
     */
    @GetMapping({"/{id}"})
    public R<DishDto> getDish(@PathVariable Long id) {
        log.info("查询菜品{}", id);
        return dishService.getDish(id);
    }

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto) {
        log.info("修改菜品{}", dishDto.toString());
        return dishService.updateDish(dishDto);
    }

    /**
     * 菜品删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteDish(@RequestParam(value = "ids", required = true) Long[] ids) {
        for (Long id : ids) {
            log.info("菜品{}删除", id);
        }
        return dishService.deleteDish(ids);
    }

    /**
     * 菜品启售和停售
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> dishStatus(@PathVariable Integer status, @RequestParam(value = "ids", required = true) Long[] ids) {
        for (Long id : ids) {
            log.info("菜品{}{}", id, status == 0 ? "停售" : "启售");
        }

        ArrayList<Dish> list = new ArrayList<>();
        for (Long id : ids) {
            Dish dish = new Dish();
            dish.setId(id);
            dish.setStatus(status);
            list.add(dish);
        }
        // 批量更新
        dishService.updateBatchById(list);

        return R.success("菜品启售和停售成功");
    }

    /**
     * 根据categoryId或者name获取菜品
     *
     * @param categoryId
     * @return
     */
//    @GetMapping("/list")
//    public R<List<Dish>> list(@RequestParam(value = "categoryId", required = false) Long categoryId, @RequestParam(value = "name", required = false) String name) {
//        log.info("根据categoryId或者name获取菜品{},{}", categoryId, name);
//        // 添加查询条件
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(categoryId != null, Dish::getCategoryId, categoryId).like(name != null, Dish::getName, name).eq(Dish::getStatus, 1).orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        // 查询数据
//        List<Dish> list = dishService.list(queryWrapper);
//        return R.success(list);
//    }
    @GetMapping("/list")
    public R<List<DishDto>> list(@RequestParam(value = "categoryId", required = false) Long categoryId, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "status", required = false) Long status) {
        log.info("根据categoryId或者name获取菜品{},{},{}", categoryId, name, status);
        // 添加查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(categoryId != null, Dish::getCategoryId, categoryId).like(name != null, Dish::getName, name).eq(Dish::getStatus, 1).orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        // 查询数据
        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            List<DishFlavor> flavors = dishFlavorService.list(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, item.getId()));
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            dishDto.setFlavors(flavors);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }
}
