package com.learn.mymall.controller;

import com.learn.mymall.common.CommonResult;
import com.learn.mymall.entity.EmailEntity;
import com.learn.mymall.service.UmsMemberServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mymall
 * @description: 会员登录注册管理
 * @author: likj
 * @create: 2020-04-20 16:25
 */
@RestController
@Api(value="UmsMemberController",tags={"会员登录注册管理"})
@RequestMapping("/sso")
public class UmsMemberController {

    @Autowired
    private UmsMemberServiceI umsMemberServiceI;

    @ApiOperation("获取邮箱验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "receiveMailbox",value = "接收者邮箱",paramType = "path",dataType = "int")
    })
    @RequestMapping(value = "/getEmailAuthCode", method = RequestMethod.GET)
    public CommonResult getEmailAuthCode(@RequestParam String receiveMailbox) {
        return umsMemberServiceI.getEmailAuthCode(receiveMailbox);
    }

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    public CommonResult getAuthCode(@RequestParam String telephone) {
        return umsMemberServiceI.generateAuthCode(telephone);
    }

    @ApiOperation("判断验证码是否正确")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone",value = "邮箱or手机号码",paramType = "path",dataType = "string"),
            @ApiImplicitParam(name = "authCode",value = "验证码",paramType = "path",dataType = "string")
    })
    @RequestMapping(value = "/verifyAuthCode", method = RequestMethod.POST)
    public CommonResult updatePassword(@RequestParam String telephone, @RequestParam String authCode) {
        return umsMemberServiceI.verifyAuthCode(telephone,authCode);
    }




}
