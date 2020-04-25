package com.learn.mymall.controller;

import com.learn.mymall.common.CommonResult;
import com.learn.mymall.param.OrderParam;
import com.learn.mymall.service.OmsPortalOrderServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: likj
 * @create: 2020-04-25 16:49
 * @description: 订单管理
 * @program: mymall
 */
@RestController
@Api(tags ={"订单管理"})
@RequestMapping("/order")
public class OmsPortalOrderController {
    @Autowired
    private OmsPortalOrderServiceI portalOrderService;
    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    public CommonResult generateOrder(@RequestBody OrderParam orderParam) {
        return portalOrderService.generateOrder(orderParam);
    }
}