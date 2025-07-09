package com.farben.springboot.xiaozhang.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface AsyncService {
    public void executeAsyncTask(Runnable task);
    public <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier);
}
