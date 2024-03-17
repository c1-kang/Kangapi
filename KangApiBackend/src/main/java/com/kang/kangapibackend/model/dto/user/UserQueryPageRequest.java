package com.kang.kangapibackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 多用户查询请求
 */
@Data
public class UserQueryPageRequest implements Serializable {
    private static final long SerialVersionUID = 1L;

    private int current;
    private int size;
    private String userAccount;
    private String userRole;
}
