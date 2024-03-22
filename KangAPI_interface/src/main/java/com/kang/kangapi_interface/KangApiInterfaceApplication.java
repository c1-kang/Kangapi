package com.kang.kangapi_interface;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.kang.kangapi_interface.mapper")
public class KangApiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KangApiInterfaceApplication.class, args);
    }

}
