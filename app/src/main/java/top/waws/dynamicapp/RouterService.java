package top.waws.dynamicapp;

import android.os.Bundle;

import com.androidthanatos.dynamic.annotation.BundleOption;
import com.androidthanatos.dynamic.annotation.Path;
import com.androidthanatos.dynamic.annotation.RequestCode;
import com.androidthanatos.dynamic.annotation.ResultCall;
import com.androidthanatos.dynamic.annotation.SkipIntecepter;

import pw.androidthanatos.router.Call;
import pw.androidthanatos.router.Response;
import pw.androidthanatos.router.ResultCallBack;

/**
 * Created on 2017/11/3.
 *
 * @author liuxiongfei.
 *         Desc
 */

public interface RouterService {

    @Path("second")
    @RequestCode(100)
    @SkipIntecepter
    Call toSecond(@BundleOption Bundle option, @ResultCall ResultCallBack callBack);
}
