package com.learn.mymall;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
/**
 * 
 * @author lei.chen
 * @Email 1431472560@qq.com
 *
 */
public class SendemailApplicationTests {
	
	//使用MailSender依赖注解
    @Autowired
    private JavaMailSender javaMailSender;
    
    /**
     * testEmail
     * @throws Exception
     */
    @Test
    public void testSendEmail() throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        // 发送方邮箱
        helper.setFrom("1545431995@qq.com");
        // 接收方邮箱
        helper.setTo("297206795@qq.com");
        // 主题
        helper.setSubject("主题：测试邮件");
        // 内容
        helper.setText("一封来自你对象的邮件，么么哒,李科纪测试2");
        javaMailSender.send(mimeMessage);

    }

}
