package com.ylan.ylantakeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylan.ylantakeaway.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by ylan
 * @date 2022-12-24 21:42
 */

@Mapper
@Transactional
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}
