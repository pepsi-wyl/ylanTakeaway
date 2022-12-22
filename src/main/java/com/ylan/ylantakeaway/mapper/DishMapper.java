package com.ylan.ylantakeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylan.ylantakeaway.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by ylan
 * @date 2022-12-19 20:14
 */

@Mapper
@Transactional
public interface DishMapper extends BaseMapper<Dish> {

}
