package com.kang.kangapibackend.aop;

import com.kang.kangapibackend.annotation.AuthCheck;
import com.kang.kangapibackend.common.ErrorCode;
import com.kang.kangapibackend.exception.BusinessException;
import com.kang.kangapibackend.service.UserService;
import com.kang.model.enums.UserRoleEnum;
import com.kang.model.user.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 自定义切面校验逻辑
 */
@Aspect
@Component
public class PermissionAdvice {

    @Resource
    private UserService userService;

    @Pointcut("@annotation(com.kang.kangapibackend.annotation.AuthCheck)")
    private void permissionCheck(){}

    @Around("@annotation(authCheck)")
    public Object check(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 获得需要的权限
        String needRole = authCheck.needRole();

        // 得到登录态，并获取当前登录用户的权限
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        UserVO loginUser = userService.getLoginUser(request);
        String userRole = loginUser.getUserRole();

        if (StringUtils.isNotBlank(needRole)) {
            if (!userRole.equals(UserRoleEnum.ADMIN.getValue())) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
            }
        }
        return joinPoint.proceed();
    }

}
