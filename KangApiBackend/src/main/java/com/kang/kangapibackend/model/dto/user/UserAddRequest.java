package com.kang.kangapibackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户新建请求
 */
@Data
public class UserAddRequest implements Serializable {
    private String userAccount;
    private String userPassword;
    private String userRole;
    private static final long SerialVersionUID = 1L;
}
