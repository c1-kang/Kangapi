package com.kang.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户接口表
 * @TableName user_interfaceinfo
 */
@TableName(value ="user_interfaceinfo")
@Data
public class UserInterfaceinfo implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /**
     * 状态(0正常-1禁用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}