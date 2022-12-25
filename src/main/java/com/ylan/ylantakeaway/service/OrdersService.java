package com.ylan.ylantakeaway.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylan.ylantakeaway.common.R;
import com.ylan.ylantakeaway.dto.OrdersDto;
import com.ylan.ylantakeaway.entity.Orders;

import java.util.Map;

/**
 * @author by ylan
 * @date 2022-12-24 21:44
 */

public interface OrdersService extends IService<Orders> {

    /**
     * 用户下单
     *
     * @param orders
     */
    void submit(Orders orders);

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
    R<Page<Orders>> pageList(int page, int pageSize, String number, String beginTime, String endTime);

    /**
     * 查看自己的订单
     *
     * @param page
     * @param pageSize
     * @return
     */
    R<Page<OrdersDto>> userPageList(int page, int pageSize);

    /**
     * 再来一单
     *
     * @param map
     * @return
     */
    R<String> againSubmit(Map<String, String> map);
}
