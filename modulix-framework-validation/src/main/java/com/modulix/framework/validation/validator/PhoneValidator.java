package com.modulix.framework.validation.validator;

import ch.qos.logback.core.util.StringUtil;
import com.modulix.framework.validation.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * 手机号码校验器
 *
 * <br>
 * {@code date} 2025/3/27 10:08
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private Pattern pattern;

    @Override
    public void initialize(Phone constraintAnnotation) {
        pattern = constraintAnnotation.local().getPattern();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtil.isNullOrEmpty(s)) {
            return true;
        }
        return pattern.matcher(s).matches();
    }
}
