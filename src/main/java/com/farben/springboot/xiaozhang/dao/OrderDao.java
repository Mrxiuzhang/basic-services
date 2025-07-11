package com.farben.springboot.xiaozhang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.farben.springboot.xiaozhang.dto.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao extends BaseMapper<Order> {
    int insertOrder(@Param("orderNo")String orderNo,@Param("userId")Long userId,
                    @Param("totalAmount") Double totalAmount);

    Long getOrderId(@Param("orderNo")String orderNo);
}
