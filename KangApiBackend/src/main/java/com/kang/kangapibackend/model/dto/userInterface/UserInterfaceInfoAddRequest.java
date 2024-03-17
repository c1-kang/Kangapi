package com.kang.kangapibackend.model.dto.userInterface;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户接口新建请求
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long userID;

    /**
     * 接口ID
     */
    private Long interfaceID;

    /**
     * 总次数
     */
    private Integer totalNum;

    /**
     * 剩余次数
     */
    private Integer remainNum;
}
