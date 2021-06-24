package com.example.demo.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验
 */
public class ValidatorUtil {

    private static Pattern MOBILE_PATTERN = Pattern.compile("[1]([3-9])[0-9]{9}$");

    /**
     * 手机号码校验
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        if (StringUtils.isEmpty(mobile)){
            return false;
        }
        Matcher matcher = MOBILE_PATTERN.matcher(mobile);
        return matcher.matches();
    }
}
