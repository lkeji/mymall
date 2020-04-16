package com.learn.mymall.service.impl;

import com.learn.mymall.entity.PmsBrand;
import com.learn.mymall.mapper.PmsBrandMapper;
import com.learn.mymall.service.PmsBrandServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: mymall
 * @description: 品牌管理
 * @author: likj
 * @create: 2020-04-16 09:37
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandServiceI {

    @Autowired
    private PmsBrandMapper pmsBrandMapper;

    @Override
    public int createBrand(PmsBrand brand) {
        return pmsBrandMapper.insert(brand);
    }

    @Override
    public int updateBrand(PmsBrand brand) {
        return pmsBrandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public int deleteBrand(Long id) {
        return pmsBrandMapper.deleteByPrimaryKey(id
        );
    }

    @Override
    public List<PmsBrand> listBrand(int pageNum, int pageSize) {
        List<PmsBrand> result=new ArrayList<>();
        Map mpas=new HashMap();
        PmsBrand pmsBrand = pmsBrandMapper.selectByPrimaryKey(mpas);
        result.add(pmsBrand);
        return result;
    }

}
