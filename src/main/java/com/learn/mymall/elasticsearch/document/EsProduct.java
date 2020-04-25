package com.learn.mymall.elasticsearch.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 搜索中的商品信息
 * Created by macro on 2018/6/19.
 */

/*
* Document:
*   indexName 索引库名，mysql中数据库的概念
*   type      文档类型，mysql中标的概念
*   shards    默认分片数
*   replicas  默认副本数量
* */
@Data
@Document(indexName = "pms", type = "product",shards = 5,replicas = 1)
public class EsProduct implements Serializable {
    private static final long serialVersionUID = -1L;
    //@Id 文档可以认为是mysql中表行的概念
    @Id
    private Long id;
    /*
    * @Field
    *   type    文档中字段的类型
    *   index   是否建立倒排索引
    *   store   是否进行存储
    *   analyzer    分词器名次
    * */
    @Field(type = FieldType.Keyword)
    private String productSn;
    private Long brandId;
    /*
    * FieldType：
    *   Text    会进行分词并建立了索引的字符类型
    *   Auto    自动判断字段类型
    *   Nested  嵌套对象类型
    *   Keyword 不会进行分词建立索引的类型
    *
    * */
    @Field(type = FieldType.Keyword)
    private String brandName;
    private Long productCategoryId;
    @Field(type = FieldType.Keyword)
    private String productCategoryName;
    private String pic;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String name;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String subTitle;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String keywords;
    private BigDecimal price;
    private Integer sale;
    private Integer newStatus;
    private Integer recommandStatus;
    private Integer stock;
    private Integer promotionType;
    private Integer sort;
    @Field(type =FieldType.Nested)
    private List<EsProductAttributeValue> attrValueList;


}
