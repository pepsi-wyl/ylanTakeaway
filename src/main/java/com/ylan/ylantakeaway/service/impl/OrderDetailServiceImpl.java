package com.ylan.ylantakeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ylan.ylantakeaway.entity.OrderDetail;
import com.ylan.ylantakeaway.mapper.OrderDetailMapper;
import com.ylan.ylantakeaway.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author by ylan
 * @date 2022-12-24 21:48
 */

@Slf4j
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
