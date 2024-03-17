package com.kang.kangapibackend.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 单接口查询请求
 */
@Data
public class InterfaceInfoQueryRequest implements Serializable {
    private static final long SerialVersionUID = 1L;
    private long id;
}
