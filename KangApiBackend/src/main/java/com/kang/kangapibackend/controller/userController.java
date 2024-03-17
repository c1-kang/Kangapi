package com.kang.kangapibackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.kangapibackend.annotation.AuthCheck;
import com.kang.kangapibackend.common.BaseResponse;
import com.kang.kangapibackend.common.DeleteRequest;
import com.kang.kangapibackend.common.ErrorCode;
import com.kang.kangapibackend.common.ResultUtils;
import com.kang.kangapibackend.exception.BusinessException;
import com.kang.kangapibackend.model.dto.user.*;
import com.kang.kangapibackend.service.UserService;
import com.kang.model.user.UserVO;
import com.kang.model.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import static com.kang.kangapibackend.common.UserConstant.USER_LOGIN_STATE;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
@Tag(name="用户管理")
public class userController {

    @Resource
    private UserService userService;

    @Operation(summary = "注册")
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(result);
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO result = userService.userLogin(userLoginRequest, request);
        return ResultUtils.success(result);
    }

    @Operation(summary = "获取当前登录用户")
    @GetMapping("/getLogin")
    public BaseResponse<UserVO>  getLoginUserVO(HttpServletRequest request) {
        UserVO loginUserVO = userService.getLoginUser(request);
        return ResultUtils.success(loginUserVO);
    }

    @Operation(summary = "登出")
    @GetMapping("/signout")
    public BaseResponse<Boolean> userSignOut(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未登录");
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return ResultUtils.success(true);
    }

    @Operation(summary = "单用户查询")
    @PostMapping("/userQuery")
    public BaseResponse<UserVO> userQuery(@RequestBody UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = userService.userQuery(userQueryRequest);
        return ResultUtils.success(userVO);
    }

    @Operation(summary = "用户分页查询")
    @PostMapping("/userQueryPage")
    public BaseResponse<Page<UserVO>> userQueryPage(@RequestBody UserQueryPageRequest userQueryPageRequest) {
        if (userQueryPageRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<UserVO> page = userService.userQueryPage(userQueryPageRequest);
        return ResultUtils.success(page);
    }

    @Operation(summary = "新建用户（仅管理员）")
    @PostMapping("/userAdd")
    @AuthCheck
    public BaseResponse<UserVO> userAdd(@RequestBody UserAddRequest userAddRequest) {
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = userService.userAdd(userAddRequest);
        return ResultUtils.success(userVO);
    }

    @Operation(summary = "删除用户（仅管理员）")
    @PostMapping("/userDelete")
    @AuthCheck
    public BaseResponse<Boolean> userAdd(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userDelete(deleteRequest);
        return ResultUtils.success(result);
    }
}
