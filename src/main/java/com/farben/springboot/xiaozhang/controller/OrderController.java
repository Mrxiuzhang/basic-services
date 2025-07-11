package com.farben.springboot.xiaozhang.controller;

import com.farben.springboot.xiaozhang.dao.ProductDao;
import com.farben.springboot.xiaozhang.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@Slf4j
@RestController
@RequestMapping("/api/order")
@Api(value = "订单模块-基于 Redis 分布式锁的商品防超卖", description = "订单模块")
public class OrderController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDao productDao;

    /**
     * 创建订单接口
     */
    @PostMapping("/create")
    @ApiOperation(value = "创建订单接口", notes = "查询")
    public ResponseEntity<?> createOrder(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity) {

        if (quantity <= 0) {
            return ResponseEntity.badRequest().body("购买数量必须大于0");
        }

        Long orderId = productService.createOrder(userId, productId, quantity);

        if (orderId != null) {
            log.info("订单创建成功, orderId: {}, userId: {}, productId: {}, quantity: {}",
                    orderId, userId, productId, quantity);
            return ResponseEntity.ok(orderId);
        } else {
            log.warn("订单创建失败, userId: {}, productId: {}, quantity: {}",
                    userId, productId, quantity);
            return ResponseEntity.badRequest().body("创建订单失败，可能库存不足");
        }
    }

    /**
     * 模拟并发扣减库存
     */
    @PostMapping("/testReduceStockWithLock")
    @ApiOperation(value = "模拟并发扣减库存", notes = "test")
    public ResponseEntity<?> testReduceStockWithLock() throws Exception{

        Long productId  = productDao.getProductId("测试商品");
        // 模拟并发扣减库存
        int threadCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                try {
                    boolean result = productService.reduceStockWithLock(productId, 3);
                    System.out.println("扣减结果: " + result);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // 验证最终库存
        Integer finalStock = productDao.getStock(productId);

        // 最多只能成功扣减3次（10/3≈3.33）
        //assertTrue(finalStock >= 1 && finalStock <= 10);

        return null;
    }
}
