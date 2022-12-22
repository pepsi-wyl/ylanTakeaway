package com.ylan.ylantakeaway.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.dto.DishDto;
import com.ylan.ylantakeaway.entity.Dish;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by ylan
 * @date 2022-12-19 20:19
 */

public interface DishService extends IService<Dish> {

    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    R<String> addDish(DishDto dishDto);

    /**
     * 分页查询菜品
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    R<Page<DishDto>> pageList(int page, int pageSize, String name);

    /**
     * 根据ID查询菜品和口位
     *
     * @param id
     * @return
     */
    R<DishDto> getDish(Long id);

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    R<String> updateDish(DishDto dishDto);

    /**
     * 菜品删除
     *
     * @param ids
     * @return
     */
    R<String> deleteDish(Long[] ids);

}
