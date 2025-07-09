package com.farben.springboot.xiaozhang.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farben.springboot.xiaozhang.common.ApiResult;
import com.farben.springboot.xiaozhang.common.PageResult;
import com.farben.springboot.xiaozhang.dao.AddressDao;
import com.farben.springboot.xiaozhang.dto.AddressDTO;
import com.farben.springboot.xiaozhang.dto.UserRegisterDTO;
import com.farben.springboot.xiaozhang.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/address")
@Validated // 启用参数校验
@Api(value = "地址API", description = "这是一个地址API的描述")
public class AddressController {


    @Autowired
    private AddressService addressService;

    // 请求体校验
    @PostMapping("/createAddress")
    public ResponseEntity<Void> createAddress(@RequestBody AddressDTO user) {
            addressService.saveAddress(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/listUsers")
    @ApiOperation(value = "条件查询", notes = "返回结果")
    public ResponseEntity<List<AddressDTO>> listUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(addressService.listAddress());
    }

    @GetMapping("/pageUsers")
    @ApiOperation(value = "分页查询", notes = "返回结果")
    public ResponseEntity<ApiResult<PageResult<AddressDTO>>> pageUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<AddressDTO> pageData = addressService.pageUsers(page, size);

        PageResult<AddressDTO> pageResult = new PageResult<>(
                pageData.getRecords(),
                pageData.getTotal(),
                pageData.getCurrent(),
                pageData.getSize()
        );
        return ResponseEntity.ok(ApiResult.pageSuccess(pageResult));
    }

}

