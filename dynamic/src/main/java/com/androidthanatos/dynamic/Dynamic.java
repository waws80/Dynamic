package com.androidthanatos.dynamic;

import android.content.Context;
import android.support.annotation.NonNull;

import com.androidthanatos.dynamic.core.ServiceMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import pw.androidthanatos.router.Call;
import pw.androidthanatos.router.Response;
import pw.androidthanatos.router.Router;
import pw.androidthanatos.router.RouterLog;

/**
 * Dynamic 这是一个封装类 可以像使用Retrofit一样使用
 * Created on 2017/10/31.
 * @author liuxiongfei
 */

public final class Dynamic {

    private String baseUrl;
    private Router router;

    private Dynamic(Builder builder){
        this.baseUrl = builder.baseUrl;
        this.router = builder.router;
    }

    public String baseUrl(){
        return this.baseUrl;
    }

    public Router router(){
        return this.router;
    }

    public static final class Builder{

        private String baseUrl;
        private Router router;

        public Builder baseUrl(@NonNull String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder router(@NonNull Router router){
            this.router = router;
            return this;
        }

        public Dynamic build(){
            return new Dynamic(this);
        }

    }

    /**
     * 服务构建
     * @param service 服务类
     * @param context 上下文 使用 context 将会添加flag：Intent.FLAG_ACTIVITY_NEW_TASK
     * @param <T> 服务类泛型
     * @return 返回服务类
     */
    @SuppressWarnings("All")
    public <T> T create(@NonNull Class<T> service, final Context context){
        Util.checkInterface(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RouterLog.d(method.getReturnType().getName());
                        Class<?> returnType = method.getReturnType();
                        if (returnType == Call.class){
                            return new ServiceMethod.Builder(method, args,context).build().buildCall();
                        }else if (returnType == Response.class){
                            return new ServiceMethod.Builder(method, args,context).build().buildResponse();
                        }else if (Constants.VOID.equals(returnType.getName())){
                            new ServiceMethod.Builder(method,args,context).build().noReturn();
                            return null;
                        }else {
                            throw new IllegalArgumentException("service: "+method.getDeclaringClass()+
                                    " method: "+method.getName()+"的返回值不被支持，目前仅支持 Call Response");
                        }

                    }
                });
    }
}
