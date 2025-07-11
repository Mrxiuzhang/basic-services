package com.farben.springboot.xiaozhang.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 商品实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("product")
public class ProductDto {
    private Long id;
    private String name;//商品名称
    private Double price;//商品价格
    private Integer stock;//库存数量
    private Integer version;//版本号（乐观锁）
}
