package com.farben.springboot.xiaozhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.farben.springboot.xiaozhang.dao.AddressDao;
import com.farben.springboot.xiaozhang.dto.AddressDTO;
import com.farben.springboot.xiaozhang.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, AddressDTO> implements AddressService {
    @Resource
    private AddressDao addressDao;

    @Override
    public boolean saveAddress(AddressDTO user) {
        super.save(user);
        return true;
    }

    @Override
    public List<AddressDTO> listAddress() {

        return addressDao.selectByCondition(null,null);
    }


    public Page<AddressDTO> pageUsersByCondition(int page, int size, String username, Integer minAge) {
        Page<AddressDTO> pageParam = new Page<>(page, size);
        QueryWrapper<AddressDTO> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        if (minAge != null) {
            queryWrapper.ge("age", minAge);
        }
        queryWrapper.orderByDesc("create_time");

        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public Page<AddressDTO> pageUsers(Integer page, Integer size) {
        Page<AddressDTO> pageParam = Page.of(page, size);
        QueryWrapper<AddressDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(pageParam, queryWrapper);
    }
}
