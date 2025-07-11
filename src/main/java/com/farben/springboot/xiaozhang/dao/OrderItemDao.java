package com.farben.springboot.xiaozhang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.farben.springboot.xiaozhang.dto.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderItemDao extends BaseMapper<OrderItem> {
    int insertOrderItem(@Param("orderId") Long orderId, @Param("productId") Long productId, @Param("productName") String productName
            , @Param("productPrice") Double productPrice, @Param("quantity") Integer quantity, @Param("subtotal") Double subtotal);
}
