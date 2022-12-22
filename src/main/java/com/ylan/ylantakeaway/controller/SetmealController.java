package com.ylan.ylantakeaway.controller;

import com.ylan.ylantakeaway.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐控制器
 *
 * @author by ylan
 * @date 2022-12-19 20:26
 */

@Slf4j
@RestController
public class SetmealController {

    /**
     * 注入服服务层类
     */
    @Autowired
    private SetmealService setmealService;

}
