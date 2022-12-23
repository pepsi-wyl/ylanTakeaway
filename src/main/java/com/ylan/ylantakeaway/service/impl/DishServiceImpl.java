package com.ylan.ylantakeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.dto.*;
import com.ylan.ylantakeaway.entity.*;
import com.ylan.ylantakeaway.mapper.*;
import com.ylan.ylantakeaway.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by ylan
 * @date 2022-12-19 20:21
 */

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    /**
     * 注入DishFlavorService和CategoryService
     */
    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    @Transactional
    @Override
    public R<String> addDish(DishDto dishDto) {
        // 保存菜品基本信息到菜品表
        this.save(dishDto);

        // 获取菜品ID并且给口位中的菜品ID赋值
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        // 保存菜品口位信息到菜品口味表
        dishFlavorService.saveBatch(flavors);

        return R.success("添加菜品成功");
    }

    /**
     * 分页查询菜品
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R<Page<DishDto>> pageList(int page, int pageSize, String name) {

        // 分页构造器
        Page<Dish> dishPage = new Page<>(page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
        // 添加过滤条件name不为空的时候进行like查询,并进行排序
        queryWrapper.like(!StringUtils.isEmpty(name), Dish::getName, name).orderByDesc(Dish::getUpdateTime);
        // 执行查询
        this.page(dishPage, queryWrapper);

        // 改造DishPage中的records数据，并封装为DishDto集合类型
        List<DishDto> records = dishPage.getRecords().stream().map((item) -> {
            // 根据分类ID查询分类对象
            Category category = categoryService.getById(item.getCategoryId());

            // 封装DishDto数据
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            if (category != null) {
                // 获取分类名称
                String categoryName = category.getName();
                // 设置分类名称
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        // 封装DishDtoPage数据
        Page<DishDto> dishDtoPage = new Page<>();
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");
        dishDtoPage.setRecords(records);

        // 返回数据
        return R.success(dishDtoPage);
    }

    /**
     * 根据ID查询菜品和口位
     *
     * @param id
     * @return
     */
    @Override
    public R<DishDto> getDish(Long id) {

        // 查询菜品基本信息
        Dish dish = this.getById(id);

        // 查询菜品口位信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        // 封装返回数据
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        dishDto.setFlavors(flavors);

        return R.success(dishDto);
    }

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    @Transactional
    @Override
    public R<String> updateDish(DishDto dishDto) {
        // 更新菜品基本信息到菜品表
        this.updateById(dishDto);

        // 更新菜品口位信息到菜品口味表
        Long dishId = dishDto.getId();
        // 删除
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishId);
        dishFlavorService.remove(queryWrapper);
        // 添加
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);

        return R.success("修改菜品成功");
    }

    @Value("${ylanTakeaway.path}")
    private String basePath;

    /**
     * 菜品删除
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public R<String> deleteDish(Long[] ids) {
        // 删除图片
        List<Dish> dishes = this.listByIds(Arrays.asList(ids));
        for (Dish dish : dishes) {
            File file = new File(basePath + File.separator + dish.getImage());
            if (file.exists()) file.delete();
        }

        // 批量删除菜品信息
        this.removeByIds(Arrays.asList(ids));

        // 批量删除菜品口位信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(queryWrapper);

        return R.success("菜品删除成功");
    }
}
