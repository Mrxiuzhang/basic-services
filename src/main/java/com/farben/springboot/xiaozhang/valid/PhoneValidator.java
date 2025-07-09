package com.farben.springboot.xiaozhang.valid;

import com.farben.springboot.xiaozhang.annotation.ValidPhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator  implements ConstraintValidator<ValidPhone, String> {

    private String pattern;
    private boolean nullable;

    @Override
    public void initialize(ValidPhone constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 允许空值
        if (nullable && (value == null || value.isEmpty())) {
            return true;
        }

        // 非空校验
        if (value == null || value.isEmpty()) {
            return false;
        }

        if (!Pattern.matches(pattern, value)) {
            // 动态设置错误信息
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "手机号格式错误，当前值: " + value + "，要求格式: " + pattern
            ).addConstraintViolation();
            return false;
        }
        return true;
    }

}
