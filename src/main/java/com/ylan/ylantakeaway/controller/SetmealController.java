package com.ylan.ylantakeaway.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.dto.DishDto;
import com.ylan.ylantakeaway.dto.SetmealDto;
import com.ylan.ylantakeaway.entity.Dish;
import com.ylan.ylantakeaway.entity.Setmeal;
import com.ylan.ylantakeaway.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * 套餐控制器
 *
 * @author by ylan
 * @date 2022-12-19 20:26
 */

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    /**
     * 注入服服务层类
     */
    @Autowired
    private SetmealService setmealService;

    /**
     * 添加套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> addSetmeal(@RequestBody SetmealDto setmealDto) {
        log.info("添加套餐{}", setmealDto.toString());
        return setmealService.addSetmeal(setmealDto);
    }

    /**
     * 分页查询套餐
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> pageList(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "name", required = false) String name) {
        log.info("查询套餐page={},pageSize={},name={}", page, pageSize, name);
        return setmealService.pageList(page, pageSize, name);
    }

    /**
     * 根据ID查询套餐和菜品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getSetmeal(@PathVariable Long id) {
        log.info("查询套餐{}", id);
        return setmealService.getSetmeal(id);
    }

    /**
     * 修改套餐
     *
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> updateSetmeal(@RequestBody SetmealDto setmealDto) {
        log.info("{}", setmealDto.toString());
        return setmealService.updateSetmeal(setmealDto);
    }

    /**
     * 套餐删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteSetmeal(@RequestParam(value = "ids", required = true) Long[] ids) {
        for (Long id : ids) {
            log.info("套餐{}删除", id);
        }
        return setmealService.deleteSetmeal(ids);
    }

    /**
     * 套餐启售和停售
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> setmealStatus(@PathVariable Integer status, @RequestParam(value = "ids", required = true) Long[] ids) {
        for (Long id : ids) {
            log.info("套餐{}{}", id, status == 0 ? "停售" : "启售");
        }

        ArrayList<Setmeal> list = new ArrayList<>();
        for (Long id : ids) {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(id);
            setmeal.setStatus(status);
            list.add(setmeal);
        }
        // 批量更新
        setmealService.updateBatchById(list);

        return R.success("套餐启售和停售成功");
    }

}
