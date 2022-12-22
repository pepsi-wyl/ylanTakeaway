package com.ylan.ylantakeaway.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.dto.DishDto;
import com.ylan.ylantakeaway.entity.Dish;
import com.ylan.ylantakeaway.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
}
