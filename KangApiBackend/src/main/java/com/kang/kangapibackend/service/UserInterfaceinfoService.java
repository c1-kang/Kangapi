package com.kang.kangapibackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.kangapibackend.common.IDRequest;
import com.kang.kangapibackend.model.dto.userInterface.UserInterfaceInfoAddRequest;
import com.kang.kangapibackend.model.dto.userInterface.UserInterfaceInfoQueryPageRequest;
import com.kang.kangapibackend.model.dto.userInterface.UserInterfaceUpdateRequest;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.model.entity.UserInterfaceinfo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author 28356
 * @description 针对表【user_interfaceinfo(用户接口表)】的数据库操作Service
 * @createDate 2024-03-12 17:16:39
 */
public interface UserInterfaceinfoService extends IService<UserInterfaceinfo> {

    UserInterfaceinfo userInterfaceInfoQuery(IDRequest idRequest);

    Page<UserInterfaceinfo> userInterfaceInfoQueryPage(UserInterfaceInfoQueryPageRequest userInterfaceInfoQueryPageRequest);

    Boolean userInterfaceInfoAdd(UserInterfaceInfoAddRequest request);

    boolean userInterfaceInfoDelete(IDRequest deleteRequest);

    boolean userInterfaceUpdate(UserInterfaceUpdateRequest userInterfaceUpdateRequest);

    /**
     * 调用次数 + 1， 剩余次数 - 1
     *
     * @param userID      用户ID
     * @param interfaceID 接口ID
     * @return boolean
     */
    boolean invokeCount(long userID, long interfaceID);
}
