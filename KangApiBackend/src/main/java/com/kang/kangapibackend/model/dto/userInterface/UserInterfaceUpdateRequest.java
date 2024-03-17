package com.kang.kangapibackend.model.dto.userInterface;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户接口更新请求
 */
@Data
public class UserInterfaceUpdateRequest implements Serializable {
    private static final long SerialVersionUID = 1L;
    private Long id;
    /**
     * 总次数
     */
    private Integer totalNum;

    /**
     * 剩余次数
     */
    private Integer remainNum;
}
