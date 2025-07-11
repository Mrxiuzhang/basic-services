package com.farben.springboot.xiaozhang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.farben.springboot.xiaozhang.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDao extends BaseMapper<ProductDto> {
    int update(@Param("quantity") Integer quantity,@Param("productId")Long productId
            ,@Param("version")Integer version);

    Long getProductId(@Param("productName")String productName);

    Integer  getStock(@Param("productId")Long productId);
}
