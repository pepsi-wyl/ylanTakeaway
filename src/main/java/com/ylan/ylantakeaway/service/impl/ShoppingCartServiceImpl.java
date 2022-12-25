package com.ylan.ylantakeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ylan.ylantakeaway.entity.ShoppingCart;
import com.ylan.ylantakeaway.mapper.ShoppingCartMapper;
import com.ylan.ylantakeaway.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author by ylan
 * @date 2022-12-24 18:54
 */

@Slf4j
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
