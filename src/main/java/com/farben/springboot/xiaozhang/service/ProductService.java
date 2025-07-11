package com.farben.springboot.xiaozhang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farben.springboot.xiaozhang.dto.ProductDto;

public interface ProductService {
    Page<ProductDto> pageProduct(Integer page, Integer size);

    Long createOrder(Long userId, Long productId, int quantity);

     boolean reduceStockWithLock(Long productId, int quantity);

}
