package com.kang.kangapibackend.service.impl.inner;

import com.kang.kangapibackend.service.UserInterfaceinfoService;
import com.kang.service.InnerUserInterfaceService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InnerUserInterfaceServiceImpl implements InnerUserInterfaceService {

    @Resource
    private UserInterfaceinfoService userInterfaceinfoService;

    @Override
    public boolean invokeCount(long userID, long interfaceID) {
        return userInterfaceinfoService.invokeCount(userID, interfaceID);
    }
}
