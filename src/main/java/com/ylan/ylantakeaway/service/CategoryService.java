package com.ylan.ylantakeaway.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.entity.Category;

import java.util.List;

/**
 * @author by yaln
 * @date 2022-12-19 16:50
 */

public interface CategoryService extends IService<Category> {

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    R<String> addCategory(Category category);

    /**
     * 分页查询分类
     *
     * @param page
     * @param pageSize
     * @return
     */
    R<Page<Category>> pageList(int page, int pageSize);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    R<String> deleteCategory(Long id);

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    R<String> updateCategory(Category category);

    /**
     * 获取菜品分类或者套餐分类
     *
     * @param type
     * @return
     */
    R<List<Category>> listCategory(Integer type);
}
