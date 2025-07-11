package com.farben.springboot.xiaozhang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages="com.farben.springboot.xiaozhang")
@MapperScan("com.farben.springboot.xiaozhang.dao") // 扫描 Mapper 接口
@EnableCaching//开启redis缓存
public class XiaozhangApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaozhangApplication.class, args);
    }

}
