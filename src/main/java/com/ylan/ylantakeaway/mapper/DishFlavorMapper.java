package com.ylan.ylantakeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylan.ylantakeaway.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by ylan
 * @date 2022-12-20 21:17
 */

@Mapper
@Transactional
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}
