package com.farben.springboot.xiaozhang.annotation;

import com.farben.springboot.xiaozhang.valid.EnumValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 枚举值校验
@Target(ElementType.FIELD)//FIELD 标注在类的字段上（如实体类/DTO中的手机号字段）
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
public @interface ValidEnum {

    String message() default "无效的枚举值";
    Class<? extends Enum<?>> enumClass();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
