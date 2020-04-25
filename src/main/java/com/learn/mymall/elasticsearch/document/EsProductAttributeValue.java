package com.learn.mymall.elasticsearch.document;

import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author: likj
 * @create: 2020-04-24 19:55
 * @description: 搜索中的商品属性信息
 * @program: mymall
 */
@Data
public class EsProductAttributeValue implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long productAttributeId;
    //属性值
    @Field(type = FieldType.Keyword)
    private String value;
    //属性参数：0-》规格；1-》参数
    private Integer type;
    //属性名次
    @Field(type = FieldType.Keyword)
    private String name;



}
