package com.farben.springboot.xiaozhang.valid;

import com.farben.springboot.xiaozhang.annotation.ValidDateRange;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {
    private String startField;
    private String endField;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.startField = constraintAnnotation.startDate();
        this.endField = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            BeanWrapper wrapper = new BeanWrapperImpl(value);
            LocalDate start = (LocalDate) wrapper.getPropertyValue(startField);
            LocalDate end = (LocalDate) wrapper.getPropertyValue(endField);

            return start == null || end == null || !end.isBefore(start);
        } catch (Exception e) {
            return false;
        }
    }
}
