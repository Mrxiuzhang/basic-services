package com.farben.springboot.xiaozhang.service.impl;

import com.farben.springboot.xiaozhang.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
public class AsyncServiceImpl implements AsyncService {
    /**
     * 使用 @Async 注解执行异步方法
     * 默认使用名为 "taskExecutor" 的线程池
     */
    @Async("taskExecutor")
    public void executeAsyncTask(Runnable task) {
        task.run();
    }

    /**
     * 异步方法带返回值
     */
    @Async("taskExecutor")
    public <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {

        return CompletableFuture.supplyAsync(supplier);
    }
}
