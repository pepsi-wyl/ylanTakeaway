package com.ylan.ylantakeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.dto.SetmealDto;
import com.ylan.ylantakeaway.entity.Category;
import com.ylan.ylantakeaway.entity.Setmeal;
import com.ylan.ylantakeaway.entity.SetmealDish;
import com.ylan.ylantakeaway.mapper.SetmealMapper;
import com.ylan.ylantakeaway.service.CategoryService;
import com.ylan.ylantakeaway.service.SetmealDishService;
import com.ylan.ylantakeaway.service.SetmealService;
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
 * @author by pepsi-wyl
 * @date 2022-12-19 20:24
 */

@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    /**
     * 注入SetmealDishService和CategoryService
     */
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加套餐
     *
     * @param setmealDto
     * @return
     */
    @Transactional
    @Override
    public R<String> addSetmeal(SetmealDto setmealDto) {
        // 保存套餐基本信息
        this.save(setmealDto);

        // 保存套餐和菜品关联信息
        Long id = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((v) -> {
            v.setSetmealId(id);
            return v;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);

        return R.success("添加套餐成功");
    }

    /**
     * 分页查询套餐
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R<Page<SetmealDto>> pageList(int page, int pageSize, String name) {

        // 分页构造器
        Page<Setmeal> setmealPage = new Page<Setmeal>(page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件name不为空的时候进行like查询,并进行排序
        queryWrapper.like(!StringUtils.isEmpty(name), Setmeal::getName, name).orderByDesc(Setmeal::getUpdateTime);
        // 执行查询
        this.page(setmealPage, queryWrapper);

        // 改造SetmealPage中的records数据，并封装为SetmealDto集合类型
        List<SetmealDto> records = setmealPage.getRecords().stream().map((item) -> {
            // 根据分类ID查询分类对象
            Category category = categoryService.getById(item.getCategoryId());

            // 封装SetmealDto数据
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());

        // 封装SetmealDtoPage数据
        Page<SetmealDto> setmealDtoPage = new Page<>();
        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        setmealDtoPage.setRecords(records);

        return R.success(setmealDtoPage);
    }

    /**
     * 根据ID查询套餐和菜品
     *
     * @param id
     * @return
     */
    @Override
    public R<SetmealDto> getSetmeal(Long id) {

        // 查询套餐的基本信息
        Setmeal setmeal = this.getById(id);

        // 查询套餐菜品基本信息
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmeal.getId());
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);

        // 封装返回数据
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        setmealDto.setSetmealDishes(setmealDishes);

        return R.success(setmealDto);
    }

    /**
     * 修改套餐
     *
     * @param setmealDto
     * @return
     */
    @Transactional
    @Override
    public R<String> updateSetmeal(SetmealDto setmealDto) {
        // 修改套餐基本信息
        this.updateById(setmealDto);

        // 跟新套餐的菜品信息
        Long setmealId = setmealDto.getId();
        // 删除
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, setmealId);
        setmealDishService.remove(queryWrapper);
        // 添加
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealId);
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);

        return R.success("修改套餐成功");
    }

    @Value("${ylanTakeaway.path}")
    private String basePath;

    /**
     * 套餐删除
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public R<String> deleteSetmeal(Long[] ids) {
        // 删除图片
        List<Setmeal> setmeals = this.listByIds(Arrays.asList(ids));
        for (Setmeal setmeal : setmeals) {
            File file = new File(basePath + File.separator + setmeal.getImage());
            if (file.exists()) file.delete();
        }

        // 批量删除套餐信息
        this.removeByIds(Arrays.asList(ids));

        // 批量删除套餐菜品信息
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(queryWrapper);

        return R.success("套餐删除成功");
    }
}
