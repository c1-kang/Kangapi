package com.kang.service;

import com.kang.model.entity.User;

/**
 * 用户服务
 */
public interface InnerUserService {

    /**
     * 查询 accessKey 是否已经分配给用户
     *
     * @param accessKey 密钥
     * @return User
     */
    User getInvokeUser(String accessKey);
}
