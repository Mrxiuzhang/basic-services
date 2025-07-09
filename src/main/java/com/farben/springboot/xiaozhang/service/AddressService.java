package com.farben.springboot.xiaozhang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farben.springboot.xiaozhang.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> listAddress();

    boolean saveAddress(AddressDTO user);

    Page<AddressDTO> pageUsers(Integer page, Integer size);
}
