package com.kang.kangapibackend.model.dto.userInterface;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户接口分页查询请求
 */
@Data
public class UserInterfaceInfoQueryPageRequest implements Serializable {
    private static final long SerialVersionUID = 1L;

    private int current;
    private int size;

    /**
     * 用户ID
     */
    private Long userID;

    /**
     * 接口ID
     */
    private Long interfaceID;

    /**
     * 状态(0正常-1禁用)
     */
    private Integer status;
}
