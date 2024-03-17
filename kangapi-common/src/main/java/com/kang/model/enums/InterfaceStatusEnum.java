package com.kang.model.enums;

import lombok.Getter;

/**
 * 接口状态枚举
 */
@Getter
public enum InterfaceStatusEnum {
    ONLINE("上线", 1),
    OFFLINE("下线", 0);


    private final String text;

    private final int value;

    InterfaceStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }
}
