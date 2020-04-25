package com.learn.mymall.service;

import com.learn.mymall.common.CommonResult;
import com.learn.mymall.param.OrderParam;

/**
 * @author: likj
 * @create: 2020-04-25 16:40
 * @description: 前台订单管理
 * @program: mymall
 */
public interface OmsPortalOrderServiceI {
    /*
    * 根据提交信息生成订单
    * */
    CommonResult generateOrder(OrderParam orderParam);
    /*
    * 取消单个超时订单
    * */
    void cancelOrder(Long orderId);

}
