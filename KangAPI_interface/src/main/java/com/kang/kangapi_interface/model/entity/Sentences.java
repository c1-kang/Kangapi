package com.kang.kangapi_interface.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 
 * @TableName sentences
 */
@TableName(value ="sentences")
@Data
public class Sentences implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 句子
     */
    private String hitokoto;

    /**
     * 类别
     */
    private String type;

    /**
     * 来源
     */
    private String source;

    /**
     * 来源于谁
     */
    private String from_who;

    /**
     * 长度
     */
    private Integer length;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}