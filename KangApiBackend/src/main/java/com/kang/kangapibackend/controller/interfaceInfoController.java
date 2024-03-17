package com.kang.kangapibackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.kang.kangapibackend.annotation.AuthCheck;
import com.kang.kangapibackend.common.*;
import com.kang.kangapibackend.exception.BusinessException;
import com.kang.kangapibackend.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.kang.kangapibackend.model.dto.interfaceInfo.InterfaceInfoQueryPageRequest;
import com.kang.kangapibackend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.kang.kangapibackend.service.InterfaceinfoService;
import com.kang.kangapibackend.service.UserService;
import com.kang.kangclientsdk.client.KangApiClient;
import com.kang.kangclientsdk.model.Test;
import com.kang.model.entity.Interfaceinfo;
import com.kang.model.entity.User;
import com.kang.model.enums.InterfaceStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 接口管理
 */
@RestController
@RequestMapping("/interface")
@Tag(name = "接口管理")
public class interfaceInfoController {

    @Resource
    private InterfaceinfoService interfaceInfoService;

    @Resource
    private UserService userService;


    @Operation(summary = "单接口查询")
    @PostMapping("/interfaceInfoQuery")
    public BaseResponse<Interfaceinfo> interfaceInfoQuery(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceinfo = interfaceInfoService.interfaceInfoQuery(interfaceInfoQueryRequest);
        return ResultUtils.success(interfaceinfo);
    }

    @Operation(summary = "接口分页查询")
    @PostMapping("/interfaceInfoQueryPage")
    public BaseResponse<Page<Interfaceinfo>> interfaceInfoQueryPage(@RequestBody InterfaceInfoQueryPageRequest interfaceInfoQueryPageRequest) {
        if (interfaceInfoQueryPageRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Interfaceinfo> page = interfaceInfoService.interfaceInfoQueryPage(interfaceInfoQueryPageRequest);
        return ResultUtils.success(page);
    }

    @Operation(summary = "新建接口（仅管理员）")
    @PostMapping("/interfaceAdd")
    @AuthCheck
    public BaseResponse<Interfaceinfo> userAdd(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceinfo = interfaceInfoService.interfaceInfoAdd(interfaceInfoAddRequest, request);
        return ResultUtils.success(interfaceinfo);
    }

    @Operation(summary = "删除接口（仅管理员）")
    @PostMapping("/interfaceDelete")
    @AuthCheck
    public BaseResponse<Boolean> userAdd(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = interfaceInfoService.interfaceInfoDelete(deleteRequest);
        return ResultUtils.success(result);
    }

    @Operation(summary = "上线")
    @PostMapping("/online")
    public BaseResponse<Boolean> online(@RequestBody IDRequest idRequest) {
        if (idRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = idRequest.getId();
        Interfaceinfo interfaceinfo = interfaceInfoService.getById(id);

        // TODO 验证接口是否可以调用

        interfaceinfo.setStatus(InterfaceStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceinfo);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库更新错误");
        }
        return ResultUtils.success(result);
    }

    @Operation(summary = "下线")
    @PostMapping("/offline")
    public BaseResponse<Boolean> offline(@RequestBody IDRequest idRequest) {
        if (idRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = idRequest.getId();
        Interfaceinfo interfaceinfo = interfaceInfoService.getById(id);
        interfaceinfo.setStatus(InterfaceStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceinfo);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库更新错误");
        }
        return ResultUtils.success(result);
    }

    @Operation(summary = "调用接口")
    @GetMapping("/get/{id}")
    public BaseResponse<Object> getName(@PathVariable("id") long interfaceID, HttpServletRequest request) {
        // 得到接口信息
        Interfaceinfo interfaceinfo = interfaceInfoService.getById(interfaceID);
        String url = interfaceinfo.getUrl();
        String method = interfaceinfo.getMethod();
        String requestParams = interfaceinfo.getRequestParams();

        // 调用 Kang API Client
        User user = userService.getLoginUserAdmin(request);
        String accessKey = user.getAccessKey();
        String secretKey = user.getSecretKey();
        KangApiClient client = new KangApiClient(accessKey, secretKey);

        // 不同请求方式，不同方法
        String result = "";
        if (method.equals("POST")) {
            result = client.byPost(url, requestParams);
        } else if (method.equals("GET")) {
            // TODO get 请求有问题
            result = client.byGet(url);
        }

        return ResultUtils.success(result);
    }
}
