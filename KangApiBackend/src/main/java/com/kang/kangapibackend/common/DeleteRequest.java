package com.kang.kangapibackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 */
@Data
public class DeleteRequest implements Serializable {
    private static final long SerialVersionUID = 1L;
    private long id;
}
