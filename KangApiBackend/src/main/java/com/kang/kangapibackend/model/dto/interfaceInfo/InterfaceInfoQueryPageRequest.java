package com.kang.kangapibackend.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 多接口查询请求
 */
@Data
public class InterfaceInfoQueryPageRequest implements Serializable {
    private static final long SerialVersionUID = 1L;

    private int current;
    private int size;
    /**
     * 名称
     */
    private String name;
    /**
     * 请求类型
     */
    private String method;
    /**
     * 创建人ID
     */
    private Long userID;

    /**
     * 状态-(0关闭,1开启)
     */
    private Integer status;

}
