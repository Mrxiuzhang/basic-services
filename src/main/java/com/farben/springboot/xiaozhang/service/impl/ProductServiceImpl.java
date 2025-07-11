package com.farben.springboot.xiaozhang.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.farben.springboot.xiaozhang.dao.OrderDao;
import com.farben.springboot.xiaozhang.dao.OrderItemDao;
import com.farben.springboot.xiaozhang.dao.ProductDao;
import com.farben.springboot.xiaozhang.dto.ProductDto;
import com.farben.springboot.xiaozhang.service.ProductService;
import com.farben.springboot.xiaozhang.utils.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductDto> implements ProductService {
    @Autowired
    private RedisDistributedLock redisLock;
    @Resource
    private ProductDao productDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderItemDao orderItemDao;

    @Override
    public Page<ProductDto> pageProduct(Integer page, Integer size) {
        return null;
    }

    /**
     * 扣减库存（使用Redis分布式锁）
     * @param productId 商品ID
     * @param quantity 扣减数量
     * @return 是否扣减成功
     */
    @Transactional
    public boolean reduceStockWithLock(Long productId, int quantity) {
        // 获取分布式锁
        String lockValue = redisLock.lock(productId);
        if (lockValue == null) {
            log.error("获取分布式锁失败, productId: {}", productId);
            return false;
        }

        try {
            // 查询商品库存
            ProductDto product = super.getById(productId);
            if (product == null) {
                log.error("商品不存在, productId: {}", productId);
                return false;
            }

            // 检查库存是否充足
            if (product.getStock() < quantity) {
                log.warn("库存不足, productId: {}, stock: {}, quantity: {}",
                        productId, product.getStock(), quantity);
                return false;
            }

            // 扣减库存（使用乐观锁）
            int updated = productDao.update(quantity,productId,product.getVersion());
            return updated > 0;
        } finally {
            // 释放分布式锁
            redisLock.unlock(productId, lockValue);
        }
    }


    /**
     * 创建订单（包含防超卖逻辑）
     * @param userId 用户ID
     * @param productId 商品ID
     * @param quantity 购买数量
     * @return 订单ID（创建失败返回null）
     */
    @Transactional
    public Long createOrder(Long userId, Long productId, int quantity) {
        // 1. 获取分布式锁
        String lockValue = redisLock.lock(productId);
        if (lockValue == null) {
            log.error("获取分布式锁失败, productId: {}", productId);
            return null;
        }

        try {
            // 2. 查询商品信息
            ProductDto product = super.getById(productId);
            if (product == null) {
                log.error("商品不存在, productId: {}", productId);
                return null;
            }

            // 3. 检查库存
            if (product.getStock() < quantity) {
                log.warn("库存不足, productId: {}, stock: {}, quantity: {}",
                        productId, product.getStock(), quantity);
                return null;
            }

            // 4. 扣减库存（使用乐观锁）
            int updated = productDao.update(quantity, productId, product.getVersion());


            if (updated <= 0) {
                log.warn("库存扣减失败, 可能已被其他请求修改, productId: {}", productId);
                return null;
            }

            // 5. 创建订单
            String orderNo = generateOrderNo();
            double totalAmount = product.getPrice() * quantity;
            orderDao.insertOrder(orderNo,userId,totalAmount);

            // 6. 获取订单ID
            Long orderId = orderDao.getOrderId(orderNo);

            // 7. 创建订单明细
            orderItemDao.insertOrderItem( orderId, productId, product.getName(),
                    product.getPrice(), quantity, totalAmount);
            return orderId;
        } finally {
            // 8. 释放分布式锁
            redisLock.unlock(productId, lockValue);
        }
    }


    /**
     * 生成订单号（简单实现）
     */
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }


}
