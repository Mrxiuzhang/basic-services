package com.farben.springboot.xiaozhang.controller;

import com.farben.springboot.xiaozhang.dto.RedisDto;
import com.farben.springboot.xiaozhang.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController("/redis")
@Api(value = "RedisAPI", description = "Redis控制器")
@Slf4j
@CacheConfig(cacheNames = "userCache")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    //查询并缓存结果
    @ApiOperation(value = "查询并缓存结果", notes = "查询")
    @Cacheable(key = "#id")
    @GetMapping("/getUserById")
    public RedisDto getUserById(@RequestParam("id") String id) {
        // 模拟数据库查询
        return new RedisDto(id, "User" + id);
    }

    // 更新并刷新缓存
    @ApiOperation(value = "更新并刷新缓存", notes = "更新")
    @CachePut(key = "#user.key")
    @PostMapping("/updateUser")
    public RedisDto updateUser(@RequestBody RedisDto user) {
        // 模拟数据库更新
        return user;
    }

    // 删除时清除缓存
    @ApiOperation(value = "删除时清除缓存", notes = "删除")
    @CacheEvict(key = "#id")
    @GetMapping("/deleteUser")
    public void deleteUser(@RequestParam("id") String id) {
        // 模拟数据库删除
    }

    // 测试基本缓存操作
    @ApiOperation(value = "测试基本缓存操作", notes = "新增")
    @PostMapping("/set")
    public String set(@RequestParam String key, @RequestParam String value) {
        redisUtil.set(key, value);
        return "设置成功";
    }

    @ApiOperation(value = "测试基本缓存操作", notes = "查询")
    @GetMapping("/get")
    public Object get(@RequestParam String key) {
        return redisUtil.get(key);
    }

}
