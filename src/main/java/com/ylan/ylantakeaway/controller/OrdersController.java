package com.ylan.ylantakeaway.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.dto.OrdersDto;
import com.ylan.ylantakeaway.entity.Orders;
import com.ylan.ylantakeaway.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author by ylan
 * @date 2022-12-24 21:51
 */

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("用户下单，订单数据{}", orders.toString());
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 后端查看订单明细
     *
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page<Orders>> page(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "number", required = false) String number, @RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime) {
        log.info("查询订单明细page={},pageSize={},number={},beginTime={},endTime={}", page, pageSize, number, beginTime, endTime);
        return ordersService.pageList(page, pageSize, number, beginTime, endTime);
    }

    /**
     * 派送外卖
     *
     * @param orders
     * @return
     */
    @PutMapping
    public R<String> updateOrder(@RequestBody Orders orders) {
        log.info("{}派送", orders.getId());
        ordersService.updateById(orders);
        return R.success("派送成功");
    }

    /**
     * 查看自己的订单
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> userPage(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "pageSize", required = true) int pageSize) {
        log.info("查询订单明细page={},pageSize={}", page, pageSize);
        return ordersService.userPageList(page, pageSize);
    }

    /**
     * 再来一单
     *
     * @param map
     * @return
     */
    @PostMapping("/again")
    public R<String> againSubmit(@RequestBody Map<String, String> map) {
        log.info("再来一单", map);
        return ordersService.againSubmit(map);
    }


}
