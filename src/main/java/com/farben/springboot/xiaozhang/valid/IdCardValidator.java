package com.farben.springboot.xiaozhang.valid;

import com.farben.springboot.xiaozhang.annotation.ValidIdCard;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdCardValidator implements ConstraintValidator<ValidIdCard, String> {
    private static final String ID_CARD_PATTERN = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.matches(ID_CARD_PATTERN);
    }


}
