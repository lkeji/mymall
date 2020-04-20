package com.learn.mymall.entity;

import lombok.Data;

/**
 * @program: mymall
 * @description: 发送QQ邮箱实体类
 * @author: likj
 * @create: 2020-04-20 18:43
 */
@Data
public class EmailEntity {
   private String senderMailbox;
   private String receiveMailbox;
   private String theme;
   private String content;

}
