package com.kang.kangapibackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 只有一个 ID 字段
 */
@Data
public class IDRequest implements Serializable {
    private long id;
    private static final Long SerialVersionUID = 1L;
}
