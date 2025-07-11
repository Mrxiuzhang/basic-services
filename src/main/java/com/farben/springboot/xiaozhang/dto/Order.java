package com.farben.springboot.xiaozhang.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("order")
public class Order {

    private Long id;
    private String orderNo;//订单编号
    private Long userId;//用户id
    private BigDecimal totalAmount ;//订单总金额
    private int status;//订单状态(0:待支付,1:已支付,2:已取消)
}
