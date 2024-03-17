package com.kang.kangapibackend.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.kangapibackend.common.DeleteRequest;
import com.kang.kangapibackend.common.ErrorCode;
import com.kang.kangapibackend.exception.BusinessException;
import com.kang.kangapibackend.mapper.UserMapper;
import com.kang.kangapibackend.model.dto.user.*;
import com.kang.kangapibackend.service.UserService;
import com.kang.model.entity.User;
import com.kang.model.user.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kang.kangapibackend.common.UserConstant.USER_LOGIN_STATE;

/**
 * @author 28356
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-03-12 17:16:39
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private static final String SALT = "kanglanlan";

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        if (userAccount.length() > 20 || userPassword.length() < 6 || userPassword.length() > 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数长度不合法");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不相等");
        }

        User user = saveUser(userAccount, userPassword, null);
        return user.getId();
    }

    @Override
    public UserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        if (userAccount.length() > 20 || userPassword.length() < 6 || userPassword.length() > 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数长度不合法");
        }

        // 将密码 MD5 加密
        String newPassword = DigestUtil.md5Hex(SALT + userPassword);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        userQueryWrapper.eq("userPassword", newPassword);
        User user = this.getOne(userQueryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该用户未注册");
        }

        // 保存登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);

        return getUserVO(user);
    }

    @Override
    public UserVO userQuery(UserQueryRequest userQueryRequest) {
        long id = userQueryRequest.getId();
        User user = this.getById(id);
        return getUserVO(user);
    }

    @Override
    public Page<UserVO> userQueryPage(UserQueryPageRequest userQueryPageRequest) {
        QueryWrapper<User> userQueryWrapper = getQueryWrapper(userQueryPageRequest);

        int current = userQueryPageRequest.getCurrent();
        int size = userQueryPageRequest.getSize();
        Page<User> userPage = new Page<>(current, size);
        Page<User> queryResult = userMapper.selectPage(userPage, userQueryWrapper);

        // 脱敏
        List<UserVO> newListVO = queryResult.getRecords().stream().map(this::getUserVO).toList();
        Page<UserVO> page = new Page<>(current, size, queryResult.getTotal());
        page.setRecords(newListVO);

        return page;
    }

    @Override
    public UserVO userAdd(UserAddRequest userAddRequest) {
        String userAccount = userAddRequest.getUserAccount();
        String userPassword = userAddRequest.getUserPassword();
        String userRole = userAddRequest.getUserRole();

        if (StringUtils.isAnyBlank(userAccount, userPassword, userRole)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() > 20 || userPassword.length() < 6 || userPassword.length() > 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数长度不合法");
        }
        User user = saveUser(userAccount, userPassword, userRole);
        return getUserVO(user);
    }

    @Override
    public boolean userDelete(DeleteRequest deleteRequest) {
        long id = deleteRequest.getId();

        // 查询用户是否存在
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }

        boolean result = this.removeById(id);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除失败，数据库错误");
        }
        return result;
    }

    @Override
    public UserVO getLoginUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未登录");
        }
        return getUserVO(user);
    }

    /**
     * 获取当前用户（不脱敏）
     *
     * @param request request
     * @return User
     */
    @Override
    public User getLoginUserAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未登录");
        }
        return user;
    }

    /**
     * 获取 queryWrapper 封装对象
     *
     * @param userQueryPageRequest 用户分页查询请求
     * @return QueryWrapper<User>
     */
    private QueryWrapper<User> getQueryWrapper(UserQueryPageRequest userQueryPageRequest) {
        String userAccount = userQueryPageRequest.getUserAccount();
        String userRole = userQueryPageRequest.getUserRole();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(StringUtils.isNotBlank(userAccount), "userAccount", userAccount);
        userQueryWrapper.eq(StringUtils.isNotBlank(userRole), "userRole", userRole);

        return userQueryWrapper;
    }

    /**
     * 获取脱敏用户信息
     *
     * @param user user
     * @return UserVO
     */
    public UserVO getUserVO(User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 保存用户
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @param userRole     权限
     * @return user
     */
    private User saveUser(String userAccount, String userPassword, String userRole) {
        // 查询账户数量
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        long count = this.count(userQueryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该用户已注册");
        }

        // 将密码 MD5 加密
        String newPassword = DigestUtil.md5Hex(SALT + userPassword);

        // accessKey: SALT + userAccount + 随机6位数字加密
        String accessKey = DigestUtil.md5Hex(SALT + userAccount + RandomUtil.randomNumbers(6));

        // secretKey: SALT + userPassword + 随机8位数字加密
        String secretKey = DigestUtil.md5Hex(SALT + userPassword + RandomUtil.randomNumbers(8));

        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        user.setAccessKey(accessKey);
        user.setSecretKey(secretKey);
        if (StringUtils.isNotBlank(userRole)) {
            user.setUserRole(userRole);
        }
        boolean result = this.save(user);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库错误");
        }
        return user;
    }
}




