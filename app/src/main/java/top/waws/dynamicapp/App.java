package top.waws.dynamicapp;

import android.app.Application;

import pw.androidthanatos.router.Router;

/**
 * Created on 2017/11/3.
 *
 * @author liuxiongfei.
 *         Desc
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.init(this.getApplicationContext());
        Router.debug(true);
    }
}
