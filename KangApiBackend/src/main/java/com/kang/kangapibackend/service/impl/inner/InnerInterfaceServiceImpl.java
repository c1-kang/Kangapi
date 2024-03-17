package com.kang.kangapibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.kangapibackend.common.ErrorCode;
import com.kang.kangapibackend.exception.BusinessException;
import com.kang.kangapibackend.mapper.InterfaceinfoMapper;
import com.kang.model.entity.Interfaceinfo;
import com.kang.service.InnerInterfaceService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InnerInterfaceServiceImpl implements InnerInterfaceService {

    @Resource
    private InterfaceinfoMapper interfaceinfoMapper;

    @Override
    public Interfaceinfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Interfaceinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);

        return interfaceinfoMapper.selectOne(queryWrapper);
    }
}
