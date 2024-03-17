package com.kang.kangapibackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 单用户查询请求
 */
@Data
public class UserQueryRequest implements Serializable {
    private static final long SerialVersionUID = 1L;
    private long id;
}
