package com.kang.service;

import com.kang.model.entity.Interfaceinfo;

/**
 * 接口服务
 */
public interface InnerInterfaceService {

    /**
     * 查询接口是否存在
     *
     * @param path   路径
     * @param method 请求方法
     * @return Interfaceinfo
     */
    Interfaceinfo getInterfaceInfo(String path, String method);
}
