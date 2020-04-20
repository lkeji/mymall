package com.learn.mymall.service;

import com.learn.mymall.common.CommonResult;
import com.learn.mymall.entity.EmailEntity;

/**
 * @program: mymall
 * @description: 会员登录注册管理
 * @author: likj
 * @create: 2020-04-20 16:28
 */
public interface UmsMemberServiceI {
    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);
    /**
     * 发送邮箱验证码
     */
    CommonResult getEmailAuthCode(String receiveMailbox);

    /**
     * 判断验证码和手机号码是否匹配
     */
    CommonResult verifyAuthCode(String telephone, String authCode);
}
