package com.learn.mymall.elasticsearch.repository;

import com.github.pagehelper.Page;
import com.learn.mymall.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;


/**
 * @author: likj
 * @create: 2020-04-24 20:12
 * @description: ES操作类(用于操作ES)
 * @program: mymall
 */
public interface EsProductRepository extends ElasticsearchCrudRepository<EsProduct,Long> {

    /**
     * 搜索查询
     *
     * @param name              商品名称
     * @param subTitle          商品标题
     * @param keywords          商品关键字
     * @param page              分页信息
     * @return
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);
}
