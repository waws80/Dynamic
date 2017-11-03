package com.androidthanatos.dynamic;

/**
 * Created on 2017/11/1.
 * @author liuxiongfei
 */

public class Util {
    /**
     * 检测api接口是否合法
     * @param service 接口
     * @param <T> 类类型
     */
    static <T> void checkInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }
}
