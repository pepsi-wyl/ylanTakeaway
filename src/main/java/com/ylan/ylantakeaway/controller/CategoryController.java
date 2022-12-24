package com.ylan.ylantakeaway.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.entity.Category;
import com.ylan.ylantakeaway.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 *
 * @author by ylan
 * @date 2022-12-19 16:56
 */

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    /**
     * 注入服服务层类
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> addCategory(@RequestBody Category category) {
        log.info("添加分类{}", category.toString());
        return categoryService.addCategory(category);
    }

    /**
     * 分页查询分类
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<Category>> pageList(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "pageSize", required = true) int pageSize) {
        log.info("查询分类page={},pageSize={}", page, pageSize);
        return categoryService.pageList(page, pageSize);
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> deleteCategory(@RequestParam(value = "ids", required = true) Long id) {
        log.info("删除分类，id:{}", id);
        return categoryService.deleteCategory(id);
    }

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @PutMapping
    public R<String> updateCategory(@RequestBody Category category) {
        log.info("修改分类{}", category.toString());
        return categoryService.updateCategory(category);
    }

    /**
     * 获取菜品分类或者套餐分类
     *
     * @param type
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> listCategory(@RequestParam(value = "type", required = false) Integer type) {
        if (type != null) {
            log.info("获取{}分类", type == 1 ? "菜品" : "套餐");
        }
        if (type == null) {
            log.info("获取套餐和菜品分类");
        }
        return categoryService.listCategory(type);
    }
}
