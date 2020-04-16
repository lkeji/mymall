package com.learn.mymall.service.impl;

import com.learn.mymall.entity.PmsBrand;
import com.learn.mymall.service.PmsBrandServiceI;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: mymall
 * @description: 品牌管理
 * @author: likj
 * @create: 2020-04-16 09:37
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandServiceI {
    @Override
    public List<PmsBrand> listAllBrand() {
        return null;
    }

    @Override
    public int createBrand(PmsBrand brand) {
        return 0;
    }

    @Override
    public int updateBrand(Long id, PmsBrand brand) {
        return 0;
    }

    @Override
    public int deleteBrand(Long id) {
        return 0;
    }

    @Override
    public List<PmsBrand> listBrand(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public PmsBrand getBrand(Long id) {
        return null;
    }
}
