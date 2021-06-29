package com.example.demo.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title: AccessLimit
 * Description: 接口访问限制注解
 * 访问限制：@AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    //时间段内
    int seconds();

    //最大请求次数
    int maxCount();

    //是否需要登录
    boolean needLogin() default true;
}
