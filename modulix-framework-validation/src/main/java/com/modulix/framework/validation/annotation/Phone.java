package com.modulix.framework.validation.annotation;

import com.modulix.framework.validation.common.ConstantRegexp;
import com.modulix.framework.validation.validator.PhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.*;
import java.util.regex.Pattern;

/**
 * 校验电话号码
 *
 * <br>
 * {@code date} 2025/3/26 11:22
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return 号码所属区
     */
    Local local() default Local.ZH_CN;

    @Getter
    @AllArgsConstructor
    enum Local {

        /**
         * 中国大陆手机号
         */
        ZH_CN(ConstantRegexp.ZH_CN_PHONE_PATTERN),

        ;

        private final Pattern pattern;
    }
}
