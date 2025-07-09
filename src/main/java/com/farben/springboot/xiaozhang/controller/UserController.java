package com.farben.springboot.xiaozhang.controller;

import com.farben.springboot.xiaozhang.dto.UserRegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/users")
@Validated // 启用参数校验
public class UserController {

    // 请求体校验
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterDTO dto) {
        // 业务处理
        return ResponseEntity.ok("注册成功");
    }

    // 路径变量校验
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(
            @PathVariable @Min(1) Long id) {
        // 业务处理
        return ResponseEntity.ok(null);
    }

}

