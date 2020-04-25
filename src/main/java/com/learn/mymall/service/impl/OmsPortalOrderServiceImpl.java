package com.learn.mymall.service.impl;

import com.learn.mymall.common.CommonResult;
import com.learn.mymall.component.mq.CancelOrderSender;
import com.learn.mymall.param.OrderParam;
import com.learn.mymall.service.OmsPortalOrderServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: likj
 * @create: 2020-04-25 16:43
 * @description: 前台订单管理实现类
 * @program: mymall
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderServiceI {
    private static Logger LOGGER = LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);
    //数据生产者
    @Autowired
    private CancelOrderSender cancelOrderSender;
    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        LOGGER.info("process generateOrder");
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderID应该在下单后生成）
        sendDelayMessageCancelOrder(11L);
        return CommonResult.success(null,"下单成功");
    }

    @Override
    public void cancelOrder(Long orderId) {
        LOGGER.info("process cancelOrder orderId:{}",orderId);
    }

    private void sendDelayMessageCancelOrder(Long orderId){
        //获取订单超时时间，假设为60分钟(测试用的30秒)
        long delayTimes = 30 * 1000;
        cancelOrderSender.sendMessage(orderId,delayTimes);
    }


}
