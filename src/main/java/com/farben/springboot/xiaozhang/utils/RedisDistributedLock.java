package com.farben.springboot.xiaozhang.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实现
 */
@Slf4j
@Component
public class RedisDistributedLock {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 锁前缀
    private static final String LOCK_PREFIX = "lock:product:";

    private static final long DEFAULT_EXPIRE_TIME = 30; // 默认锁过期时间

    // 获取锁等待时间(毫秒)
    private static final long WAIT_TIME = 3000;

    // 获取锁重试间隔(毫秒)
    private static final long RETRY_INTERVAL = 100;

    /**
     * 获取分布式锁
     * @param productId 商品ID
     * @return 锁标识（用于解锁）
     */
    public String lock(Long productId) {
        return lock(productId, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
    }


    /**
     * 获取分布式锁
     * @param productId 商品ID
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 锁标识（用于解锁）
     */
    public String lock(Long productId, long timeout, TimeUnit unit) {

        String lockKey = LOCK_PREFIX + productId;
        String lockValue = String.valueOf(System.currentTimeMillis() + unit.toMillis(timeout));

        long end = System.currentTimeMillis() + WAIT_TIME;



        while (System.currentTimeMillis() < end) {
            // 尝试获取锁
            if (redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, timeout, unit)) {
                return lockValue;
            }

            try {
                Thread.sleep(RETRY_INTERVAL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        log.warn("获取分布式锁超时, productId: {}", productId);
        return null;
    }


    /**
     * 释放分布式锁
     * @param productId 商品ID
     * @param lockValue 锁标识
     */
    public void unlock(Long productId, String lockValue) {
        String lockKey = LOCK_PREFIX + productId;
        String currentValue = (String) redisTemplate.opsForValue().get(lockKey);

        // 只有锁的持有者才能释放锁
        if (lockValue != null && lockValue.equals(currentValue)) {
            redisTemplate.delete(lockKey);
        }
    }

    /**
     * 尝试获取分布式锁（非阻塞）
     * @param productId 商品ID
     * @return 锁标识（用于解锁）
     */
    public String tryLock(Long productId) {
        String lockKey = LOCK_PREFIX + productId;
        String lockValue = String.valueOf(System.currentTimeMillis() + DEFAULT_EXPIRE_TIME * 1000);

        if (redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS)) {
            return lockValue;
        }

        return null;
    }

    public boolean lock(String key, String value) {
        return lock(key, value, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    public boolean lock(String key, String value, long timeout, TimeUnit unit) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        return Boolean.TRUE.equals(result);
    }

    public boolean unlock(String key, String value) {
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        if (currentValue != null && currentValue.equals(value)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }
}
