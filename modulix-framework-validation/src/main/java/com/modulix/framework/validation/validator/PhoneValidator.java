package com.modulix.framework.validation.validator;

import com.modulix.framework.validation.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return pattern.matcher(s).matches();
    }
}
