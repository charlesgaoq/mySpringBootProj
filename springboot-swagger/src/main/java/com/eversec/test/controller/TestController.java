package com.eversec.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "test",description ="测试")
public class TestController {
    
    @RequestMapping(value = "test")
    @ApiOperation(value = "test")
    public String test() {
        System.out.println("ets");
        return "hello";
    }
    
}
