package com.learn.mymall.service.impl;

import com.github.pagehelper.Page;
import com.learn.mymall.elasticsearch.document.EsProduct;
import com.learn.mymall.elasticsearch.repository.EsProductRepository;
import com.learn.mymall.mapper.EsProductMapper;
import com.learn.mymall.service.EsProductServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: likj
 * @create: 2020-04-24 20:05
 * @description:
 * @program: mymall
 */
@Service
public class EsProductServiceImpl implements EsProductServiceI {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);

    @Autowired
    private EsProductMapper esProductMapper;
    @Autowired
    private EsProductRepository esProductRepository;

    /*
    *从数据库中查询数据塞到ES
    * */
    @Override
    public int importAll() {
        List<EsProduct> esProductList = esProductMapper.getAllEsProductList(null);
        Iterable<EsProduct> esProductIterable = esProductRepository.saveAll(esProductList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result=0;
        while (iterator.hasNext()){
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        esProductRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        EsProduct result=null;
        //根据id去mysql查出数据
        List<EsProduct> allEsProductList = esProductMapper.getAllEsProductList(id);
        if (allEsProductList.size()>0){
            EsProduct esProduct =  allEsProductList.get(0);
            //将数据作为参数新增到ES
            result = esProductRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)){
            List<EsProduct> esProducts=new ArrayList<>();
            for (Long id : ids) {
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProducts.add(esProduct);
            }
            esProductRepository.deleteAll(esProducts);

        }

    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
       Pageable pageable=PageRequest.of(pageNum,pageSize);
        return  esProductRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
