package com.farben.springboot.xiaozhang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

@Configuration
@EnableAsync

public class ThreadPoolConfig {

    // 核心线程数 (CPU核心数)
    private final int corePoolSize = Runtime.getRuntime().availableProcessors();

    // 最大线程数 (核心线程数 * 2)
    private final int maxPoolSize = corePoolSize * 2;

    // 队列容量
    private final int queueCapacity = 100;

    // 线程空闲时间
    private final int keepAliveSeconds = 60;

    /**
     * 通用线程池 (用于普通异步任务)
     */
    @Bean(name = "taskExecutor")
    public ExecutorService taskExecutor() {
        return new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueCapacity),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );
    }

    /**
     * 定时任务线程池
     */
    @Bean
    public ScheduledExecutorService scheduledExecutor() {
        return new ScheduledThreadPoolExecutor(
                corePoolSize,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * 单线程池 (顺序执行任务)
     */
    @Bean
    public ExecutorService singleThreadExecutor() {
        return new ThreadPoolExecutor(
                1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueCapacity),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

}
