package com.kang.kangapibackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.kangapibackend.common.DeleteRequest;
import com.kang.kangapibackend.model.dto.user.*;

import com.baomidou.mybatisplus.extension.service.IService;

import com.kang.model.entity.User;
import com.kang.model.user.UserVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author 28356
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2024-03-12 17:16:39
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userRegisterRequest 注册请求
     * @return ID
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     *
     * @param userLoginRequest 登录请求
     * @param request          request
     * @return ID
     */
    UserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 单用户查询
     *
     * @param userQueryRequest 查询请求
     * @return UserVO
     */
    UserVO userQuery(UserQueryRequest userQueryRequest);


    /**
     * 用户分页查询
     *
     * @param userQueryPageRequest 用户分页查询请求
     * @return Page<UserVO>
     */
    Page<UserVO> userQueryPage(UserQueryPageRequest userQueryPageRequest);

    /**
     * 新建用户（仅管理员）
     *
     * @param userAddRequest 新建用户请求
     * @return UserVO
     */
    UserVO userAdd(UserAddRequest userAddRequest);

    /**
     * 删除用户（仅管理员）
     *
     * @param deleteRequest 删除请求
     * @return boolean
     */
    boolean userDelete(DeleteRequest deleteRequest);

    /**
     * 获取当前登录用户
     *
     * @param request request
     * @return UserVO
     */
    UserVO getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户(不脱敏）
     *
     * @param request request
     * @return UserVO
     */
    User getLoginUserAdmin(HttpServletRequest request);
}
