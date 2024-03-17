package com.kang.kangapibackend.annotation;

import java.lang.annotation.*;
import static com.kang.kangapibackend.common.UserConstant.ADMIN;

/**
 * 自定义权限校验注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthCheck {

    String needRole() default ADMIN;
}
