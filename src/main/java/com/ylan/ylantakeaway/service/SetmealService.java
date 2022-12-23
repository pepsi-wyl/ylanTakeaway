package com.ylan.ylantakeaway.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.dto.SetmealDto;
import com.ylan.ylantakeaway.entity.Setmeal;

/**
 * @author by ylan
 * @date 2022-12-19 20:19
 */

public interface SetmealService extends IService<Setmeal> {

    /**
     * 添加套餐
     *
     * @param setmealDto
     * @return
     */
    R<String> addSetmeal(SetmealDto setmealDto);

    /**
     * 分页查询套餐
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    R<Page<SetmealDto>> pageList(int page, int pageSize, String name);

    /**
     * 根据ID查询套餐和菜品
     *
     * @param id
     * @return
     */
    R<SetmealDto> getSetmeal(Long id);

    /**
     * 修改套餐
     *
     * @param setmealDto
     * @return
     */
    R<String> updateSetmeal(SetmealDto setmealDto);

    /**
     * 套餐删除
     *
     * @param ids
     * @return
     */
    R<String> deleteSetmeal(Long[] ids);

}
