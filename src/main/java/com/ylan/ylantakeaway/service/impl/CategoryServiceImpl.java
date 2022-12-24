package com.ylan.ylantakeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.exception.ServiceException;
import com.ylan.ylantakeaway.entity.Category;
import com.ylan.ylantakeaway.entity.Dish;
import com.ylan.ylantakeaway.entity.Setmeal;
import com.ylan.ylantakeaway.mapper.CategoryMapper;
import com.ylan.ylantakeaway.service.CategoryService;
import com.ylan.ylantakeaway.service.DishService;
import com.ylan.ylantakeaway.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by ylan
 * @date 2022-12-19 16:51
 */

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 注入DishService和SetmealService
     */
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    @Override
    public R<String> addCategory(Category category) {
        this.save(category);
        return R.success("分类信息添加成功");
    }

    /**
     * 分类查询分类
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public R<Page<Category>> pageList(int page, int pageSize) {
        // 分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);

        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 添加排序条件
        queryWrapper.orderByAsc(Category::getSort);

        // 执行查询
        this.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @Override
    public R<String> deleteCategory(Long id) {
        // 是否关联菜品
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId, id);
        int countDish = dishService.count(dishQueryWrapper);
        if (countDish > 0) {
            // 抛出业务异常
            throw new ServiceException("当前分类下关联了菜品，不能删除");
        }
        // 是否关联套餐
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId, id);
        int countSetmeal = setmealService.count(setmealQueryWrapper);
        if (countSetmeal > 0) {
            // 抛出业务异常
            throw new ServiceException("当前套餐下关联了菜品，不能删除");
        }
        // 删除分类
        this.removeById(id);
        return R.success("分类信息删除成功");
    }

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @Override
    public R<String> updateCategory(Category category) {
        this.updateById(category);
        return R.success("分类信息修改成功");
    }

    /**
     * 获取菜品分类或者套餐分类
     *
     * @param type
     * @return
     */
    @Override
    public R<List<Category>> listCategory(Integer type) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件
        queryWrapper.eq(type != null, Category::getType, type).orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        // 执行查询
        List<Category> list = this.list(queryWrapper);
        return R.success(list);
    }
}
