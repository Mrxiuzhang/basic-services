package com.farben.springboot.xiaozhang.dto;

import com.farben.springboot.xiaozhang.annotation.ValidEnum;
import com.farben.springboot.xiaozhang.annotation.ValidIdCard;
import com.farben.springboot.xiaozhang.annotation.ValidPhone;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class UserRegisterDTO {
    @ValidEnum(enumClass = UserType.class, message = "无效的用户类型")
    private String userType;
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度2-20字符")
    private String username;

    @ValidPhone(nullable = false, message = "手机号不能为空")
    private String phone;

    @ValidIdCard
    private String idCard;

    // 嵌套对象校验
    @Valid
    private AddressDTO address;


}
