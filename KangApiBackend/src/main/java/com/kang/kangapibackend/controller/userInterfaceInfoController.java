package com.kang.kangapibackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.kangapibackend.annotation.AuthCheck;
import com.kang.kangapibackend.common.BaseResponse;
import com.kang.kangapibackend.common.ErrorCode;
import com.kang.kangapibackend.common.IDRequest;
import com.kang.kangapibackend.common.ResultUtils;
import com.kang.kangapibackend.exception.BusinessException;
import com.kang.kangapibackend.model.dto.userInterface.UserInterfaceInfoAddRequest;
import com.kang.kangapibackend.model.dto.userInterface.UserInterfaceInfoQueryPageRequest;
import com.kang.kangapibackend.model.dto.userInterface.UserInterfaceUpdateRequest;
import com.kang.kangapibackend.service.UserInterfaceinfoService;
import com.kang.model.entity.UserInterfaceinfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口管理
 */
@RestController
@RequestMapping("/userInterface")
@Tag(name = "用户接口管理")
public class userInterfaceInfoController {

    @Resource
    private UserInterfaceinfoService userInterfaceInfoService;

    @Operation(summary = "单用户接口查询")
    @PostMapping("/userInterfaceInfoQuery")
    public BaseResponse<UserInterfaceinfo> userInterfaceInfoQuery(@RequestBody IDRequest idRequest) {
        if (idRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceinfo userInterfaceinfo = userInterfaceInfoService.userInterfaceInfoQuery(idRequest);
        return ResultUtils.success(userInterfaceinfo);
    }

    @Operation(summary = "用户接口分页查询")
    @PostMapping("/userInterfaceInfoQueryPage")
    public BaseResponse<Page<UserInterfaceinfo>> userInterfaceInfoQueryPage(@RequestBody UserInterfaceInfoQueryPageRequest userInterfaceInfoQueryPageRequest) {
        if (userInterfaceInfoQueryPageRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<UserInterfaceinfo> page = userInterfaceInfoService.userInterfaceInfoQueryPage(userInterfaceInfoQueryPageRequest);
        return ResultUtils.success(page);
    }

    @Operation(summary = "新建用户接口（仅管理员）")
    @PostMapping("/userInterfaceAdd")
    @AuthCheck
    public BaseResponse<Boolean> userAdd(@RequestBody UserInterfaceInfoAddRequest userInterfaceInfoAddRequest, HttpServletRequest request) {
        if (userInterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userInterfaceInfoService.userInterfaceInfoAdd(userInterfaceInfoAddRequest);
        return ResultUtils.success(result);
    }

    @Operation(summary = "删除用户接口（仅管理员）")
    @PostMapping("/userInterfaceDelete")
    @AuthCheck
    public BaseResponse<Boolean> userAdd(@RequestBody IDRequest deleteRequest) {
        if (deleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userInterfaceInfoService.userInterfaceInfoDelete(deleteRequest);
        return ResultUtils.success(result);
    }

    @Operation(summary = "用户接口更新（仅管理员）")
    @PostMapping("update")
    @AuthCheck
    public BaseResponse<Boolean> update(@RequestBody UserInterfaceUpdateRequest userInterfaceUpdateRequest) {
        if (userInterfaceUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userInterfaceInfoService.userInterfaceUpdate(userInterfaceUpdateRequest);
        return ResultUtils.success(result);
    }
}
