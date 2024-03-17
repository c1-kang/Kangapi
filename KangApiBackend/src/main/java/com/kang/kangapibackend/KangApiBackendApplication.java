package com.kang.kangapibackend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kang.kangapibackend.mapper")
@EnableDubbo
public class KangApiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KangApiBackendApplication.class, args);
    }

}
