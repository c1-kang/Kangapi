package com.kang.kangapi_interface.service;

import com.kang.kangapi_interface.model.entity.Sentences;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 28356
* @description 针对表【sentences】的数据库操作Service
* @createDate 2024-03-19 20:33:42
*/
public interface SentencesService extends IService<Sentences> {

    String getRandom(String queryString);
}
