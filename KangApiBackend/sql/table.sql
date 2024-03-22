-- 创建 USER 表
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT "ID",
    userAccount  VARCHAR(256) NOT NULL COMMENT "账号",
    userPassword VARCHAR(256) NOT NULL COMMENT "密码",
    userRole     VARCHAR(16)  NOT NULL DEFAULT "USER" COMMENT "权限",
    userAvatar   VARCHAR(512) COMMENT "头像",
    status       TINYINT      NOT NULL DEFAULT 0 COMMENT "状态 0 正常 1 封号",
    accessKey    VARCHAR(256) COMMENT "访问密钥",
    secretKey    VARCHAR(256) COMMENT "密钥",
    createTime   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "创建时间",
    updateTime   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "更新时间",
    isDelete     TINYINT      NOT NULL DEFAULT 0 COMMENT "是否删除",
    PRIMARY KEY (`id`) USING BTREE
);

-- 创建接口表
DROP TABLE IF EXISTS interfaceInfo;
CREATE TABLE interfaceInfo
(
    id             BIGINT       NOT NULL AUTO_INCREMENT COMMENT "ID" PRIMARY KEY,
    name           VARCHAR(256) NOT NULL COMMENT "名称",
    description    VARCHAR(512) COMMENT "描述",
    url            VARCHAR(256) NOT NULL COMMENT "请求地址",
    method         VARCHAR(128) NOT NULL COMMENT "请求类型",
    requestHeader  VARCHAR(512) NOT NULL COMMENT "请求头",
    responseHeader VARCHAR(512) NOT NULL COMMENT "响应头",
    requestParams  VARCHAR(512) NOT NULL COMMENT "请求参数",
    userID         BIGINT       NOT NULL COMMENT "创建人ID",
    status         TINYINT      NOT NULL DEFAULT 0 COMMENT "状态-(0关闭,1开启)",
    createTime     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "创建时间",
    updateTime     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "更新时间",
    isDelete       TINYINT      NOT NULL DEFAULT 0 COMMENT "是否删除"
) COMMENT "接口信息";

-- 创建 user_interfaceInfo 表
DROP TABLE IF EXISTS user_interfaceInfo;
CREATE TABLE user_interfaceInfo
(
    id          BIGINT   NOT NULL AUTO_INCREMENT COMMENT "ID" PRIMARY KEY,
    userID      BIGINT   NOT NULL COMMENT "用户ID",
    interfaceID BIGINT   NOT NULL COMMENT "接口ID",
    totalNum    INT      NOT NULL DEFAULT 0 COMMENT "总次数",
    remainNum   INT      NOT NULL DEFAULT 0 COMMENT "剩余次数",
    status      TINYINT  NOT NULL DEFAULT 0 COMMENT "状态(0正常-1禁用)",
    createTime  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "创建时间",
    updateTime  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "更新时间",
    isDelete    TINYINT  NOT NULL DEFAULT 0 COMMENT "是否删除"
) COMMENT "用户接口表";

CREATE TABLE user
(
    id       BIGINT       NOT NULL AUTO_INCREMENT COMMENT "ID",
    uuid     VARCHAR(56)  NOT NULL COMMENT "uuid",
    hitokoto VARCHAR(512) NOT NULL COMMENT "句子",
    type     VARCHAR(4)   NOT NULL COMMENT "类别",
    source   VARCHAR(256) COMMENT "来源",
    from_who VARCHAR(64) COMMENT "来源于谁",
    length   int COMMENT "长度",
    PRIMARY KEY (`id`) USING BTREE
);