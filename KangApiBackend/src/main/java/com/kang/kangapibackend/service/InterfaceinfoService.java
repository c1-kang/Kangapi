package com.kang.kangapibackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.kangapibackend.common.DeleteRequest;
import com.kang.kangapibackend.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.kang.kangapibackend.model.dto.interfaceInfo.InterfaceInfoQueryPageRequest;
import com.kang.kangapibackend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;

import com.baomidou.mybatisplus.extension.service.IService;

import com.kang.model.entity.Interfaceinfo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author 28356
 * @description 针对表【interfaceinfo(接口信息)】的数据库操作Service
 * @createDate 2024-03-12 17:16:39
 */
public interface InterfaceinfoService extends IService<Interfaceinfo> {

    /**
     * 删除接口（仅管理员）
     *
     * @param deleteRequest 删除请求
     * @return boolean
     */
    boolean interfaceInfoDelete(DeleteRequest deleteRequest);

    /**
     * 新建接口（仅管理员）
     *
     * @param interfaceInfoAddRequest 新建接口请求
     * @return Interfaceinfo
     */
    Interfaceinfo interfaceInfoAdd(InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request);

    /**
     * 接口分页查询
     *
     * @param interfaceInfoQueryPageRequest 分页查询请求
     * @return Page<Interfaceinfo>
     */
    Page<Interfaceinfo> interfaceInfoQueryPage(InterfaceInfoQueryPageRequest interfaceInfoQueryPageRequest);

    /**
     * 单接口查询
     *
     * @param interfaceInfoQueryRequest 查询请求
     * @return Interfaceinfo
     */
    Interfaceinfo interfaceInfoQuery(InterfaceInfoQueryRequest interfaceInfoQueryRequest);
}
