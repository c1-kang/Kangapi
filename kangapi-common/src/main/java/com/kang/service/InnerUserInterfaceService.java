package com.kang.service;

/**
 * 用户接口服务
 */
public interface InnerUserInterfaceService {

    /**
     * 接口调用 + 1
     *
     * @param userID      用户ID
     * @param interfaceID 接口ID
     * @return boolean
     */
    boolean invokeCount(long userID, long interfaceID);
}
