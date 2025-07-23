package com.modulix.framework.validation.common;

import java.util.regex.Pattern;

/**
 * 正则表达式常量
 *
 * <br>
 * {@code date} 2025/3/27 10:24
 */
public class ConstantRegexp {

    /**
     * 中国大陆手机号码正则表达式
     */
    private static final String ZH_CN_PHONE_REGEX = "^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$";
    /**
     * 中国大陆手机号码正则表达式
     */
    public static final Pattern ZH_CN_PHONE_PATTERN = getPattern(ZH_CN_PHONE_REGEX);



    /**
     * 通过正则表达式字符串创建Pattern对象
     *
     * @param regex 正则表达式字符串
     * @return 正则表达式Pattern对象
     */
    private static Pattern getPattern(String regex) {
        return Pattern.compile(regex);
    }

}
