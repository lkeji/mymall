package com.learn.mymall.service;

import com.learn.mymall.entity.PmsBrand;

import java.util.List;

/**
 * @program: mymall
 * @description: 品牌管理
 * @author: likj
 * @create: 2020-04-16 09:36
 */
public interface PmsBrandServiceI {

    int createBrand(PmsBrand brand);

    int updateBrand(PmsBrand brand);

    int deleteBrand(Long id);

    List<PmsBrand> listBrand(int pageNum, int pageSize);


}
