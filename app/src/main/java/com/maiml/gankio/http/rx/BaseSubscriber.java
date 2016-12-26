package com.maiml.gankio.http.rx;

import android.content.Context;

import com.maiml.gankio.App;
import com.maiml.gankio.CookieDbUtil;
import com.maiml.gankio.http.intercepter.CookieResult;
import com.maiml.gankio.utils.NetworkUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import rx.Subscriber;

/**
 * Created by maimingliang on 2016/12/26.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    /* 软引用回调接口*/
    protected SoftReference<SubscriberOnNextListener> mSubscriberOnNextListener;


    /*软引用反正内存泄露*/
    protected SoftReference<RxAppCompatActivity> mActivity;

    protected String method;

    public BaseSubscriber(Context context, String method,SubscriberOnNextListener mSubscriberOnNextListener) {

        this.mSubscriberOnNextListener = new SoftReference<SubscriberOnNextListener>(mSubscriberOnNextListener);
        this.mActivity = new SoftReference<RxAppCompatActivity>((RxAppCompatActivity) context);

        this.method = method;
    }


    public RxAppCompatActivity getRxAppCompatActivity() {
        return mActivity.get();
    }


    @Override
    public void onStart() {
        super.onStart();
        /*缓存并且有网*/
        if (App.IS_CACHE && NetworkUtil.isNetWorkActive(App.getInstance())) {
             /*获取缓存数据*/
            CookieResult cookieResulte = CookieDbUtil.getInstance().queryCookieBy(App.SERVER_ADDRESS+method);
            if (cookieResulte != null) {
                long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                if (time < 60) {
                    if (mSubscriberOnNextListener.get() != null) {
                        mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte());
                    }
                    onCompleted();
                    unsubscribe();
                }
            }
        }
    }
}
