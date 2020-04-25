package com.learn.mymall.mongodb.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author: likj
 * @create: 2020-04-25 13:24
 * @description: 用户商品浏览历史记录
 * @program: mymall
 */
@Data
//@Document标示映射到MongoDB文档上的领域对象(mongobd表名)
@Document
public class MemberReadHistory {
    //@Id标示某个域为ID域
    @Id
    private String id;
    //@Indexed标示某个字段为MongoDB的索引字段
    @Indexed
    private Long memberId;
    private String memberNickname;
    private String memberIcon;
    @Indexed
    private Long productId;
    private String productName;
    private String productPic;
    private String productSubTitle;
    private String productPrice;
    private Date createTime;
}
