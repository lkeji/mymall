package com.learn.mymall.service.impl;

import com.learn.mymall.common.CommonResult;
import com.learn.mymall.service.RedisServiceI;
import com.learn.mymall.service.UmsMemberServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * @program: mymall
 * @description: 会员登录注册管理
 * @author: likj
 * @create: 2020-04-20 16:29
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberServiceI {

    @Autowired
    private RedisServiceI redisService;

    //redis的key前缀
    @Value("${redis.key.prefix.authCode}")
    private  String REDIS_KEY_PREFIX_AUTH_CODE;
    //验证码过期时间
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    /**
     * 生成验证码
     */
    @Override
    public CommonResult generateAuthCode(String telephone) {
        //拼接字符串
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        //随机生成一串数字
        for (int i=0;i< 6;i++){
            stringBuilder.append(random.nextInt(10));
        }
        //将验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE+telephone,stringBuilder.toString());
        //设置过期时间
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE+telephone,AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(stringBuilder.toString(),"获取验证码成功");
    }
    /**
     * 判断验证码和手机号码是否匹配
     */
    @Override
    public CommonResult verifyAuthCode(String telephone, String authCode) {
        if (StringUtils.isEmpty(authCode)){
            return CommonResult.failed("请输入验证码");
        }
        String redisAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        if (StringUtils.isEmpty(redisAuthCode)){
            return CommonResult.failed("未找到该手机号码或验证码已失效");
        }
        boolean result = authCode.equals(redisAuthCode);
        if (result){
           return CommonResult.success(null,"验证码校验成功");
        }else{
            return CommonResult.failed("验证码不正确");
        }

    }
}
