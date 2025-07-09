package com.farben.springboot.xiaozhang.service.impl;

import com.farben.springboot.xiaozhang.annotation.ExecutionTimer;
import com.farben.springboot.xiaozhang.service.DataProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DataProcessorServiceImpl implements DataProcessorService {
    @ExecutionTimer(taskName = "我是服务端测试",unit = TimeUnit.MILLISECONDS)
    public void exPlanServicePrint() throws Exception{
        Thread.sleep(2000);
        log.info("eeeeeeeeeeee");
    }



}
