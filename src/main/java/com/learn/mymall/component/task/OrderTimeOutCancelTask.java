package com.learn.mymall.component.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: likj
 * @create: 2020-04-24 18:27
 * @description: 订单超时取消并解锁库存的定时器
 * @program: mymall
 */
@Component
public class OrderTimeOutCancelTask {
    private Logger LOGGER = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);

    @Scheduled(cron = "0 0/10 * ? * ?")
    private void cancelTimeOutOrder(){
        //todo 基础框架集成
        LOGGER.info("取消订单，并根据sku编号释放锁定库存");
    }

}
