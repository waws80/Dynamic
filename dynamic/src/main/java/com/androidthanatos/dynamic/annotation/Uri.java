package com.androidthanatos.dynamic.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 路由uri跳转注解
 * Created by liuxiongfei on 2017/11/1.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Uri {
    String value();
    boolean outside() default false;
}
