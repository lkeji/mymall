package com.learn.mymall.service.impl;

import com.learn.mymall.common.CommonResult;
import com.learn.mymall.entity.EmailEntity;
import com.learn.mymall.service.RedisServiceI;
import com.learn.mymall.service.UmsMemberServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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


    @Autowired
    private JavaMailSender javaMailSender;


    //redis的key前缀
    @Value("${redis.key.prefix.authCode}")
    private  String REDIS_KEY_PREFIX_AUTH_CODE;
    //验证码过期时间
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    //发送方邮箱
    @Value("${spring.mail.username}")
    private  String SENDER_MAIL_BOX;


    /**
     * 将验证码发送到QQ邮箱
     */
    @Override
    public CommonResult getEmailAuthCode(String receiveMailbox) {
        return privateEmailAuthCode(receiveMailbox);
    }

    /*
        发送邮箱封装
     * @param 接收方邮箱
     * @return
     * @author likj
     * @date 2020/4/20
     */
    private CommonResult privateEmailAuthCode(String receiveMailbox) {
        if ( StringUtils.isEmpty(receiveMailbox)) {
            return CommonResult.failed("请输入接收方邮箱");
        }
        //拼接字符串
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderresule = new StringBuilder();
        Random random = new Random();
        //随机生成一串数字
        for (int i=0;i< 6;i++){
            stringBuilder.append(random.nextInt(10));
        }
        //将验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE+receiveMailbox,stringBuilder.toString());
        //设置过期时间
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE+receiveMailbox,AUTH_CODE_EXPIRE_SECONDS);
        stringBuilderresule.append("收到一个验证码（有效期为"+AUTH_CODE_EXPIRE_SECONDS+"秒）哟~~~~    "+stringBuilder);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            // 发送方邮箱
            helper.setFrom(SENDER_MAIL_BOX);
            // 接收方邮箱
            helper.setTo(receiveMailbox);
            // 主题
            helper.setSubject("主题：获取验证码邮件");
            // 内容
            helper.setText(stringBuilderresule.toString());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return CommonResult.success(null,"验证码发送成功");
    }

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
