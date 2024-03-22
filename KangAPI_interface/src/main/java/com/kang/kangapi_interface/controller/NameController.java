package com.kang.kangapi_interface.controller;

import com.kang.kangapi_interface.model.test.Test;
import com.kang.kangapi_interface.model.test.Test02;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 测试接口
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get/{id}")
    public String getByGet(@PathVariable("id") String id) {
        return "hello " + id;
    }

    @GetMapping("/get")
    public String getByPost(@RequestParam("id") String id) {
        return "hello " + id;
    }

    @PostMapping("/post02")
    public String getByPostName(@RequestBody Test test, HttpServletRequest request) {
        String queryString = request.getQueryString();

        String name = test.getName();
        return "hello by Test " + name;
    }

    @PostMapping("/post03")
    public String post03(@RequestBody Test02 test02) {
        String name = test02.getName();
        int age = test02.getAge();
        String hobby = test02.getHobby();

        return "大家好，我的名字是" + name + "，我今年" + age + "岁，我的爱好是" + hobby;
    }

    @GetMapping("/image")
    public void getImage(HttpServletResponse response) throws IOException {
        // 图片的路径
        String path = "assets/pic.jpg";
        File file = new File(path);
        byte[] imageBytes = Files.readAllBytes(file.toPath());

        // 将图片数据写入到 response 中
        response.setContentType("image/png");
        response.getOutputStream().write(imageBytes);
        response.getOutputStream().flush();
    }
}
