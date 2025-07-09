package com.farben.springboot.xiaozhang.annotation;

import com.farben.springboot.xiaozhang.valid.IdCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *@Constraint(validatedBy = PhoneValidator.class)
 * 作用：
 * 声明该注解为校验约束注解，并绑定具体的校验实现类。
 * 参数规则：
 * validatedBy：必须指定实现了ConstraintValidator接口的类（此处为PhoneValidator）。
 *
 */
// 身份证校验
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCardValidator.class)
public @interface ValidIdCard {
    String message() default "身份证格式不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
