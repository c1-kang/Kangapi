package com.kang.kangapibackend.service.impl;

import com.kang.kangapibackend.service.UserInterfaceinfoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserInterfaceinfoServiceImplTest {
    @Resource
    private UserInterfaceinfoService userInterfaceinfoService;

    @Test
    public void invokeCount() {
        boolean b = userInterfaceinfoService.invokeCount(2L, 2L);
        Assertions.assertTrue(b);
    }
}