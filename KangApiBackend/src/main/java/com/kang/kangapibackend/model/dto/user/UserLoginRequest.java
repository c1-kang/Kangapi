package com.kang.kangapibackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求
 */
@Data
public class UserLoginRequest implements Serializable {
    private String userAccount;
    private String userPassword;
    private static final long SerialVersionUID = 1L;
}
