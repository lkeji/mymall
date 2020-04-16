package com.learn.mymall.mapper;

import com.learn.mymall.entity.PmsBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface PmsBrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsBrand record);

    PmsBrand selectByPrimaryKey(Map mpas);

    int updateByPrimaryKey(PmsBrand record);
}