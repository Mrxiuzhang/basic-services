package com.farben.springboot.xiaozhang.controller;

import com.farben.springboot.xiaozhang.annotation.ExecutionTimer;
import com.farben.springboot.xiaozhang.service.DataProcessorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.TimeUnit;
@Controller
@RequestMapping("/pro")
@Api(value = "示例自定义注解", description = "这是一个自定义注解demo")
public class DataProcessorController {

    @Autowired
    private DataProcessorService dataProcessorService;

    @ExecutionTimer(taskName = "张政", unit = TimeUnit.MILLISECONDS)
    @GetMapping("/processData")
    @ApiOperation(value = "打印方法耗时", notes = "没有返回结果")
    public void processData() {
        // 模拟耗时操作
        try {
            Thread.sleep(1500);
            dataProcessorService.exPlanServicePrint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
