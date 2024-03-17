package com.kang.kangapi_interface.controller;

import com.kang.kangapi_interface.model.dto.Test;
import org.springframework.web.bind.annotation.*;

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
    public String getByPostName(@RequestBody Test test ) {
        String name = test.getName();
        return "hello by Test " + name;
    }
}
