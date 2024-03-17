package com.kang.kangapibackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.kangapibackend.common.DeleteRequest;
import com.kang.kangapibackend.common.ErrorCode;
import com.kang.kangapibackend.exception.BusinessException;
import com.kang.kangapibackend.mapper.InterfaceinfoMapper;
import com.kang.kangapibackend.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.kang.kangapibackend.model.dto.interfaceInfo.InterfaceInfoQueryPageRequest;
import com.kang.kangapibackend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.kang.kangapibackend.service.InterfaceinfoService;
import com.kang.kangapibackend.service.UserService;
import com.kang.model.entity.Interfaceinfo;
import com.kang.model.user.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author 28356
 * @description 针对表【interfaceinfo(接口信息)】的数据库操作Service实现
 * @createDate 2024-03-12 17:16:39
 */
@Service
public class InterfaceinfoServiceImpl extends ServiceImpl<InterfaceinfoMapper, Interfaceinfo>
        implements InterfaceinfoService {

    @Resource
    private UserService userService;

    @Resource
    private InterfaceinfoMapper interfaceinfoMapper;

    @Override
    public boolean interfaceInfoDelete(DeleteRequest deleteRequest) {
        long id = deleteRequest.getId();

        // 查询接口是否存在
        Interfaceinfo interfaceinfo = this.getById(id);
        if (interfaceinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口不存在");
        }

        boolean result = this.removeById(id);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除失败，数据库错误");
        }
        return result;
    }

    @Override
    public Interfaceinfo interfaceInfoAdd(InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        String name = interfaceInfoAddRequest.getName();
        String description = interfaceInfoAddRequest.getDescription();
        String url = interfaceInfoAddRequest.getUrl();
        String method = interfaceInfoAddRequest.getMethod();
        String requestHeader = interfaceInfoAddRequest.getRequestHeader();
        String responseHeader = interfaceInfoAddRequest.getResponseHeader();
        String requestParams = interfaceInfoAddRequest.getRequestParams();

        if (StringUtils.isAnyBlank(name, url, method, requestHeader, requestParams, responseHeader)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 查询接口数量
        QueryWrapper<Interfaceinfo> interfaceQueryWrapper = new QueryWrapper<>();
        interfaceQueryWrapper.eq("name", name);
        long count = this.count(interfaceQueryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名称不能相同");
        }

        Interfaceinfo interfaceinfo = new Interfaceinfo();
        interfaceinfo.setName(name);
        interfaceinfo.setUrl(url);
        interfaceinfo.setMethod(method);
        interfaceinfo.setRequestHeader(requestHeader);
        interfaceinfo.setResponseHeader(responseHeader);
        interfaceinfo.setRequestParams(requestParams);

        if (StringUtils.isNotBlank(description)) {
            interfaceinfo.setDescription(description);
        }
        UserVO loginUser = userService.getLoginUser(request);
        interfaceinfo.setUserID(loginUser.getId());
        boolean result = this.save(interfaceinfo);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库错误");
        }
        return interfaceinfo;
    }

    @Override
    public Page<Interfaceinfo> interfaceInfoQueryPage(InterfaceInfoQueryPageRequest interfaceInfoQueryPageRequest) {
        int size = interfaceInfoQueryPageRequest.getSize();
        int current = interfaceInfoQueryPageRequest.getCurrent();

        QueryWrapper<Interfaceinfo> queryWrapper = getQueryWrapper(interfaceInfoQueryPageRequest);
        Page<Interfaceinfo> interfaceinfoPage = new Page<>(current, size);
        return interfaceinfoMapper.selectPage(interfaceinfoPage, queryWrapper);
    }

    @Override
    public Interfaceinfo interfaceInfoQuery(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        long id = interfaceInfoQueryRequest.getId();
        return this.getById(id);
    }

    /**
     * 获取 queryWrapper 封装对象
     *
     * @param interfaceInfoQueryPageRequest 接口分页查询请求
     * @return QueryWrapper<User>
     */
    private QueryWrapper<Interfaceinfo> getQueryWrapper(InterfaceInfoQueryPageRequest interfaceInfoQueryPageRequest) {
        String name = interfaceInfoQueryPageRequest.getName();
        String method = interfaceInfoQueryPageRequest.getMethod();
        Long userID = interfaceInfoQueryPageRequest.getUserID();
        Integer status = interfaceInfoQueryPageRequest.getStatus();

        QueryWrapper<Interfaceinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.eq(StringUtils.isNotBlank(method), "method", method);
        queryWrapper.eq(userID > 0, "userID", userID);
        queryWrapper.eq(status != null, "status", status);

        return queryWrapper;
    }
}




