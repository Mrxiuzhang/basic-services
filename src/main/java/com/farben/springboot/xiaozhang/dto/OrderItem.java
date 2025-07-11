package com.farben.springboot.xiaozhang.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单商品明细表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_item")
public class OrderItem {

    private Long id;
    private Long orderId;//订单id
    private Long productId;//商品id
    private String productName;//商品名称
    private BigDecimal productPrice;//商品名称
    private int quantity;//购买数据
    private BigDecimal subtotal;//小计金额
}
