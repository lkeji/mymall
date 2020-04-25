package com.learn.mymall.service;

import com.github.pagehelper.Page;
import com.learn.mymall.elasticsearch.document.EsProduct;

import java.util.List;

/**
 * @author: likj
 * @create: 2020-04-24 19:59
 * @description:    商品搜索管理Service
 * @program: mymall
 */
public interface EsProductServiceI {

    /*
    * 从数据库中导入所有商品到ES
    * */
    int importAll();
    /*
    * 根据id删除商品
    * */
    void delete(Long id);
    /*
    * 根据id创建商品
    * */
    EsProduct create(Long id);

    /*
     * 批量删除商品
     * */
    void delete(List<Long> ids);
    /*
    * 根据关键字搜索名称或者标题
    * */
    Page<EsProduct> search(String keyword,Integer pageNum, Integer pageSize);









}
