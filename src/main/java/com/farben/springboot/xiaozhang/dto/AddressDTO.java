package com.farben.springboot.xiaozhang.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@TableName("address") // MyBatis-Plus 注解
public class AddressDTO {
    @TableId(type = IdType.AUTO) // 主键自增
    private String id;

    @NotBlank(message = "省份不能为空")
    private String province;

    @NotBlank(message = "城市不能为空")
    private String city;

    @Size(max = 200, message = "详细地址不能超过200字符")
    private String detail;

    /**
     * 邮件编码
     */
    private int emailEncoding;

    @TableField(fill = FieldFill.INSERT) // 自动填充
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
