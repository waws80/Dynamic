package com.androidthanatos.dynamic.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.androidthanatos.dynamic.Constants;
import com.androidthanatos.dynamic.annotation.Action;
import com.androidthanatos.dynamic.annotation.Anims;
import com.androidthanatos.dynamic.annotation.BundleOption;
import com.androidthanatos.dynamic.annotation.Flag;
import com.androidthanatos.dynamic.annotation.Path;
import com.androidthanatos.dynamic.annotation.QueryBundle;
import com.androidthanatos.dynamic.annotation.RequestCode;
import com.androidthanatos.dynamic.annotation.ResultCall;
import com.androidthanatos.dynamic.annotation.SkipIntecepter;
import com.androidthanatos.dynamic.annotation.Uri;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import pw.androidthanatos.router.Call;
import pw.androidthanatos.router.Request;
import pw.androidthanatos.router.Response;
import pw.androidthanatos.router.ResultCallBack;
import pw.androidthanatos.router.Router;
import pw.androidthanatos.router.RouterLog;

/**
 * 构建Service
 * Created on 2017/11/1.
 * @author liuxiongfei
 */

public class ServiceMethod {

    private Method method;
    private Object[] args;
    private Context context;

    private ServiceMethod(Builder builder){
        this.method = builder.method;
        this.args = builder.args;
        this.context = builder.context;
    }

    public Call buildCall(){
        SkipIntecepter intecepter = method.getAnnotation(SkipIntecepter.class);
        if (intecepter != null){
            if (intecepter.value()){
                return Router.getInstance().skipIntecepter().newCall(buildRequest());
            }
        }
        return Router.getInstance().newCall(buildRequest());
    }

    public Response buildResponse(){
        SkipIntecepter intecepter = method.getAnnotation(SkipIntecepter.class);
        if (intecepter != null){
            if (intecepter.value()){
                return Router.getInstance().skipIntecepter().newCall(buildRequest()).execute();
            }
        }
        return Router.getInstance().newCall(buildRequest()).execute();
    }

    private Request buildRequest(){
        Request.Builder builder = new Request.Builder(context);
        Path path = method.getAnnotation(Path.class);
        Action action = method.getAnnotation(Action.class);
        Uri uri = method.getAnnotation(Uri.class);
        RequestCode requestCode = method.getAnnotation(RequestCode.class);
        Anims anims = method.getAnnotation(Anims.class);
        Flag flag = method.getAnnotation(Flag.class);
        if (path != null){
            builder.path(path.value());
        }
        if (action != null){
            builder.action(action.value());
        }
        if (requestCode != null){
            builder.responseCode(requestCode.value());
            ResultCallBack callBack = parseParams(Constants.RESULTCALL);
            if (callBack != null){
                builder.resultCallBack(callBack);
            }
        }
        if (anims != null){
            builder.addAnimate(anims.enter(),anims.exit());
        }
        if (flag != null){
            builder.flag(flag.value());
        }
        Bundle query = parseParams(Constants.QUERYBUNDLE);
        if (query != null){
            builder.addAll(query);
        }
        Bundle option = parseParams(Constants.OPTIONBUNDLE);
        if (option != null){
            builder.addOption(option);
        }
        if (uri != null ){
            if (uri.outside()){
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(android.net.Uri.parse(uri.value()));
                context.startActivity(intent);
                return null;
            }
            if (path == null){
                android.net.Uri url = android.net.Uri.parse(uri.value());
                builder.path(url.getPath().replace("/",""));
                for (String name:url.getQueryParameterNames()) {
                    String value = url.getQueryParameter(name);
                    RouterLog.d("key: "+name+"    value: "+value);
                    builder.addString(name,value);
                }
            }else {
                throw new IllegalArgumentException("service method has path! ");
            }
        }
        return builder.build();
    }

    @SuppressWarnings("All")
    private <T> T parseParams(String type) {
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            if (method.getParameterTypes()[i].getName().equals(Constants.BUNDLE)){
                if (method.getParameterAnnotations()[i].length >0){
                    for (Annotation annotation : method.getParameterAnnotations()[i]) {
                        if (type.equals(Constants.QUERYBUNDLE) && annotation instanceof QueryBundle){
                            return (T) args[i];
                        }
                        if (type.equals(Constants.OPTIONBUNDLE) && annotation instanceof BundleOption){
                            return (T) args[i];
                        }
                    }

                }
            }else if (method.getParameterTypes()[i].getName().equals(Constants.RESULTCALLBACK)){
                if (method.getParameterAnnotations()[i].length >0){
                    for (Annotation annotation : method.getParameterAnnotations()[i]) {
                        if (type.equals(Constants.RESULTCALL) && annotation instanceof ResultCall){
                            return (T) args[i];
                        }
                    }

                }
            }
        }
        return null;
    }

    public void noReturn() {
        Uri uri= method.getAnnotation(Uri.class);
        if (uri != null){
            if (uri.outside()){
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(android.net.Uri.parse(uri.value()));
                context.startActivity(intent);
            }else {
                Router.getInstance().newCall(buildRequest()).execute();
            }
        }else {
            Router.getInstance().newCall(buildRequest()).execute();
        }
    }


    public static final class Builder{
        private Method method;
        private Object[] args;
        private Context context;

        public Builder(Method method, Object[] args, Context context){
            this.method = method;
            this.args = args;
            this.context = context;
        }

        public ServiceMethod build(){
            return new ServiceMethod(this);
        }
    }
}
