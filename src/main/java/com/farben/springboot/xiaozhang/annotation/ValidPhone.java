package com.farben.springboot.xiaozhang.annotation;

import com.farben.springboot.xiaozhang.valid.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})//FIELD：标注在类的字段上（如实体类/DTO中的手机号字段）,PARAMETER：标注在方法参数上（如Controller接口的入参）
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class) // 指定校验器
@Documented
public @interface ValidPhone {

    String message() default "手机号格式不正确";  // 默认错误信息

    // 支持正则表达式配置
    String pattern() default "^1[3-9]\\d{9}$";

    // 是否允许为空
    boolean nullable() default false;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
