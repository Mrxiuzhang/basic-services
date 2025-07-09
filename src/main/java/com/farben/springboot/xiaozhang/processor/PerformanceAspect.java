package com.farben.springboot.xiaozhang.processor;

import com.farben.springboot.xiaozhang.annotation.ExecutionTimer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    // 拦截所有使用 @ExecutionTimer 注解的方法
    @Around("@annotation(monitor)")
    public Object measurePerformance(ProceedingJoinPoint joinPoint,
                                     ExecutionTimer monitor) throws Throwable {

        // 1. 获取方法签名和注解参数
        String methodName = joinPoint.getSignature().toShortString();
        // 获取方法参数
        Object[] args = joinPoint.getArgs();

        // 记录参数信息
        if (args.length > 0) {
            log.debug("方法参数: {}", args.length);
           return joinPoint.proceed();
        }


        String taskName = monitor.taskName();
        TimeUnit unit = monitor.unit();

        // 2. 记录开始时间
        long startTime = System.nanoTime();

        log.info("开始执行{}任务，方法名:{}",taskName,methodName);
        // 3. 执行目标方法
        Object result = joinPoint.proceed();
        log.info("{}任务执行完成,方法名:{}",taskName,methodName);
        // 4. 计算执行时间
        long duration = System.nanoTime() - startTime;

        // 5. 根据单位转换时间
        double convertedTime = convertTime(duration, unit);

        // 6. 记录性能数据（实际项目中可存入数据库或日志系统）
        System.out.printf("[%s] %s 执行耗时: %.3f %s%n",
                methodName, taskName, convertedTime, unit.name().toLowerCase());

        return result;
    }

    private double convertTime(long duration, TimeUnit unit) {

        // 根据注解配置转换时间单位
        double result;
        switch (unit) {
            case NANOSECONDS:
                result = duration;
                break;
            case MILLISECONDS:
                result = duration / 1_000_000.0;
                break;
            case SECONDS:
                result = duration / 1_000_000_000.0;
                break;
            default:
                // 必须处理未覆盖的情况（根据实际需求处理）
                throw new IllegalArgumentException("Unsupported unit: " + unit);
        }
        return duration;
    }
}
