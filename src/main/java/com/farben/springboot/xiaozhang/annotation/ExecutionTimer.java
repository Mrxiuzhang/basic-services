package com.farben.springboot.xiaozhang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @Retention(RetentionPolicy.RUNTIME)作用：
 * 声明注解保留到运行时，使JVM能在运行时通过反射读取注解信息。
 * 使用场景：
 * 需要运行时动态处理校验逻辑时（如Spring Validation框架在方法调用时触发校验）。
 * 关键点：
 * 若设置为CLASS或SOURCE，则校验框架无法在运行时获取注解，导致校验失效。
 *
 * @Target 作用目标	代码位置	常见用途
 * METHOD	方法声明上方	AOP、自定义逻辑、框架扩展
 * FIELD	字段声明上方	数据校验、依赖注入
 * PARAMETER	方法参数前	参数校验、参数预处理
 * TYPE	类/接口/枚举声明上方	组件声明、配置类标记
 */
@Retention(RetentionPolicy.RUNTIME)//声明注解保留到运行时，使JVM能在运行时通过反射读取注解信息。
@Target(ElementType.METHOD)//标注在方法参数上（如Controller接口的入参）
public @interface ExecutionTimer {
    String taskName() default "Unnamed Task";  // 任务名称
    TimeUnit unit() default TimeUnit.MILLISECONDS; // 时间单位
}
