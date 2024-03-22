package com.kang.kangapi_interface.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kang.kangapi_interface.model.entity.Sentences;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
* @author 28356
* @description 针对表【sentences】的数据库操作Mapper
* @createDate 2024-03-19 20:33:42
* @Entity generator.domain.Sentences
*/
public interface SentencesMapper extends BaseMapper<Sentences> {

    @Select("SELECT * FROM sentences ${ew.customSqlSegment} ORDER BY RAND() LIMIT 1")
    Sentences getRandomByType(@Param(Constants.WRAPPER) Wrapper wrapper);
}




