package com.kang.model.user;

import lombok.Data;

import java.util.Date;

/**
 * 登录用户返回结果去敏封装
 */
@Data
public class UserVO {
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 权限
     */
    private String userRole;

    /**
     * 状态 0 正常 1 封号
     */
    private Integer status;

    /**
     * 头像
     */
    private String userAvatar;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
