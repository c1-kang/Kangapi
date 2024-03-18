package com.kang.kangapibackend.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 调用接口请求
 */
@Data
public class InvokeInterfaceRequest implements Serializable {
    private static final long SerialVersionUID = 1L;
    private long id;
    private String queryParams;
}
