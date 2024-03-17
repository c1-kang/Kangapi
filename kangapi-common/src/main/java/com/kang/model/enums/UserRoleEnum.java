package com.kang.model.enums;

import lombok.Getter;

/**
 * 用户权限枚举类
 */
@Getter
public enum UserRoleEnum {
    ADMIN("管理员", "admin"),
    USER("普通用户", "user");

    private final String text;

    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }
}
