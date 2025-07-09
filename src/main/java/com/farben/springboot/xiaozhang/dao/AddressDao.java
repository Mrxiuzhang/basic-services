package com.farben.springboot.xiaozhang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.farben.springboot.xiaozhang.dto.AddressDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface AddressDao extends BaseMapper<AddressDTO> { // 继承 MyBatis-Plus 的 BaseMapper
    // XML 方式查询
    List<AddressDTO> selectByCondition(@Param("province") String province,
                                       @Param("emailEncoding") Integer emailEncoding);
}
