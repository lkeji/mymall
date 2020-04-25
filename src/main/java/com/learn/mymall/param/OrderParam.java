package com.learn.mymall.param;

import lombok.Data;

/**
 * @author: likj
 * @create: 2020-04-25 16:41
 * @description: 生成订单时传入的参数
 * @program: mymall
 */
@Data
public class OrderParam {
    //收货地址id
    private Long memberReceiveAddressId;
    //优惠券id
    private Long couponId;
    //使用的积分数
    private Integer useIntegration;
    //支付方式
    private Integer payType;
}
