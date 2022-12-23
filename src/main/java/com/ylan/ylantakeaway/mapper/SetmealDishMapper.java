package com.ylan.ylantakeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylan.ylantakeaway.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by ylan
 * @date 2022-12-22 20:29
 */

@Mapper
@Transactional
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

}
