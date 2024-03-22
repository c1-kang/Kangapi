package com.kang.kangapi_interface.model.test;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class temp implements Serializable {
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
    private String from;

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