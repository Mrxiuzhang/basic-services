package com.farben.springboot.xiaozhang.valid;

import com.farben.springboot.xiaozhang.annotation.ValidEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// 枚举校验器
public class EnumValueValidator implements ConstraintValidator<ValidEnum, Object> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        for (Enum<?> enumValue : enumClass.getEnumConstants()) {
            if (enumValue.name().equals(value.toString())) {
                return true;
            }
        }
        return false;
    }
}
