package com.ylan.ylantakeaway.dto;

import com.ylan.ylantakeaway.entity.OrderDetail;
import com.ylan.ylantakeaway.entity.Orders;
import lombok.*;

import java.util.List;

/**
 * @author ylan
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;

}
