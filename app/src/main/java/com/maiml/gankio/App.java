package com.maiml.gankio;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.antfortune.freeline.FreelineCore;
import com.maiml.gankio.http.di.AppComponent;
import com.maiml.gankio.http.di.AppModule;
import com.maiml.gankio.http.di.ComponentHolder;
import com.maiml.gankio.http.di.DaggerAppComponent;
import com.maiml.gankio.utils.CrashHandler;
import com.maiml.gankio.utils.LogUtil;

/**
 * Created by maimingliang on 2016/12/21.
 */

public class App extends Application {


    private static Context instance;

    public final static boolean IS_CACHE = false;
    public final static String  SERVER_ADDRESS = "http://gank.io/api/";
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtil.init();
        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        ComponentHolder.setAppComponent(appComponent);
        //捕获全局异常
        CrashHandler.getInstance().init(this);
        FreelineCore.init(this);
    }


    public static Context getInstance(){
        return instance;
    }


}
