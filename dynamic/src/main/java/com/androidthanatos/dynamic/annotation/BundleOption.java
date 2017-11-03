package com.androidthanatos.dynamic.annotation;

import android.os.Bundle;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 界面跳转动画5.0以上转场动画
 * Created by liuxiongfei on 2017/11/1.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface BundleOption {
}
