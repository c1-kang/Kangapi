package com.kang.kangapibackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.kangapibackend.common.ErrorCode;
import com.kang.kangapibackend.common.IDRequest;
import com.kang.kangapibackend.exception.BusinessException;
import com.kang.kangapibackend.mapper.UserInterfaceinfoMapper;
import com.kang.kangapibackend.model.dto.userInterface.UserInterfaceInfoAddRequest;
import com.kang.kangapibackend.model.dto.userInterface.UserInterfaceInfoQueryPageRequest;
import com.kang.kangapibackend.model.dto.userInterface.UserInterfaceUpdateRequest;
import com.kang.kangapibackend.service.InterfaceinfoService;
import com.kang.kangapibackend.service.UserInterfaceinfoService;
import com.kang.kangapibackend.service.UserService;
import com.kang.model.entity.Interfaceinfo;
import com.kang.model.entity.User;
import com.kang.model.entity.UserInterfaceinfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author 28356
 * @description 针对表【user_interfaceinfo(用户接口表)】的数据库操作Service实现
 * @createDate 2024-03-12 17:16:39
 */
@Service
public class UserInterfaceinfoServiceImpl extends ServiceImpl<UserInterfaceinfoMapper, UserInterfaceinfo>
        implements UserInterfaceinfoService {

    @Resource
    private UserInterfaceinfoMapper userInterfaceinfoMapper;

    @Resource
    private UserService userService;

    @Resource
    private InterfaceinfoService interfaceinfoService;

    @Override
    public UserInterfaceinfo userInterfaceInfoQuery(IDRequest idRequest) {
        long id = idRequest.getId();
        return this.getById(id);
    }

    @Override
    public Page<UserInterfaceinfo> userInterfaceInfoQueryPage(UserInterfaceInfoQueryPageRequest userInterfaceInfoQueryPageRequest) {
        UserInterfaceinfo userInterfaceinfo = new UserInterfaceinfo();
        BeanUtils.copyProperties(userInterfaceInfoQueryPageRequest, userInterfaceinfo);
        QueryWrapper<UserInterfaceinfo> queryWrapper = getQueryWrapper(userInterfaceinfo);

        int current = userInterfaceInfoQueryPageRequest.getCurrent();
        int size = userInterfaceInfoQueryPageRequest.getSize();
        Page<UserInterfaceinfo> page = new Page<>(current, size);
        return userInterfaceinfoMapper.selectPage(page, queryWrapper);
    }

    private QueryWrapper<UserInterfaceinfo> getQueryWrapper(UserInterfaceinfo request) {
        Long userID = request.getUserID();
        Long interfaceID = request.getInterfaceID();
        Integer status = request.getStatus();

        QueryWrapper<UserInterfaceinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(userID != null, "userID", userID);
        queryWrapper.eq(interfaceID != null, "interfaceID", interfaceID);
        queryWrapper.eq(status != null, "status", status);

        return queryWrapper;
    }

    @Override
    public Boolean userInterfaceInfoAdd(UserInterfaceInfoAddRequest request) {
        Long userID = request.getUserID();
        Long interfaceID = request.getInterfaceID();
        Integer totalNum = request.getTotalNum();
        Integer remainNum = request.getRemainNum();

        if (userID == null || interfaceID == null || totalNum == null || remainNum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        if (totalNum < 0 || remainNum < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "调用次数非法");
        }

        // 验证用户是否存在
        User user = userService.getById(userID);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }

        // 验证接口是否存在
        Interfaceinfo interfaceinfo = interfaceinfoService.getById(interfaceID);
        if (interfaceinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口不存在");
        }

        // 验证用户-接口是否已存在
        UserInterfaceinfo userInterfaceinfo = new UserInterfaceinfo();
        userInterfaceinfo.setUserID(userID);
        userInterfaceinfo.setInterfaceID(interfaceID);
        userInterfaceinfo.setTotalNum(totalNum);
        userInterfaceinfo.setRemainNum(remainNum);
        QueryWrapper<UserInterfaceinfo> queryWrapper = getQueryWrapper(userInterfaceinfo);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该用户已拥有该接口");
        }

        // 保存
        boolean save = this.save(userInterfaceinfo);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库保存错误");
        }
        return true;
    }

    @Override
    public boolean userInterfaceInfoDelete(IDRequest deleteRequest) {
        long id = deleteRequest.getId();
        UserInterfaceinfo userInterfaceinfo = this.getById(id);
        if (userInterfaceinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户接口不存在");
        }
        boolean result = this.removeById(id);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库删除错误");
        }
        return true;
    }

    @Override
    public boolean userInterfaceUpdate(UserInterfaceUpdateRequest userInterfaceUpdateRequest) {
        Long id = userInterfaceUpdateRequest.getId();
        Integer totalNum = userInterfaceUpdateRequest.getTotalNum();
        Integer remainNum = userInterfaceUpdateRequest.getRemainNum();

        if (totalNum == null && remainNum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数非法");
        }

        UserInterfaceinfo userInterfaceinfo = this.getById(id);
        if (userInterfaceinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户接口不存在");
        }

        UserInterfaceinfo updateInfo = new UserInterfaceinfo();
        updateInfo.setId(id);
        if (totalNum != null ) {
            if (totalNum <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "调用次数非法");
            }
            updateInfo.setTotalNum(totalNum);
        }
        if (remainNum != null ) {
            if (remainNum < 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "调用次数非法");
            }
            updateInfo.setRemainNum(remainNum);
        }

        boolean result = this.updateById(updateInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库更新错误");
        }

        return true;
    }

    @Override
    public boolean invokeCount(long userID, long interfaceID) {
        if (userID <= 0 || interfaceID <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceinfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userID", userID);
        updateWrapper.eq("interfaceID", interfaceID);
        updateWrapper.setSql("remainNum = remainNum - 1, totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }
}




