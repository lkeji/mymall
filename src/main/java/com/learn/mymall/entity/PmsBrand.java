package com.learn.mymall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "PmsBrand", description = "品牌管理实体类")
@Data
public class PmsBrand implements Serializable {
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 名称
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 首字母
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "首字母")
    private String firstLetter;

    /**
     * 排序
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 是否为品牌制造商：0->不是；1->是
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "是否为品牌制造商：0->不是；1->是")
    private Integer factoryStatus;

    /**
     * 是否显示
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "是否显示")
    private Integer showStatus;

    /**
     * 产品数量
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "产品数量")
    private Integer productCount;

    /**
     * 产品评论数量
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "产品评论数量")
    private Integer productCommentCount;

    /**
     * 品牌logo
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "品牌logo")
    private String logo;

    /**
     * 专区大图
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "专区大图")
    private String bigPic;

    /**
     * 品牌故事
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "品牌故事")
    private String brandStory;

    private static final long serialVersionUID = 1L;




}