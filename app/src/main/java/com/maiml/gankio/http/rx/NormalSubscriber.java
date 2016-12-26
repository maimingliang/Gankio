package com.maiml.gankio.http.rx;

import android.content.Context;
import android.widget.Toast;

import com.maiml.gankio.App;
import com.maiml.gankio.CookieDbUtil;
import com.maiml.gankio.http.intercepter.CookieResult;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by maimingliang on 2016/12/26.
 */

public class NormalSubscriber<T> extends BaseSubscriber<T> {


    public NormalSubscriber(Context context, String method,SubscriberOnNextListener mSubscriberOnNextListener) {
        super(context,method, mSubscriberOnNextListener);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    /**
     * 对错误进行统一处理
     * @param e
     */
    @Override
    public void onError(Throwable e) {

          /*需要緩存并且本地有缓存才返回*/
        if (App.IS_CACHE) {
            Observable.just(App.SERVER_ADDRESS+method).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    doError(e);
                }

                @Override
                public void onNext(String s) {
                    /*获取缓存数据*/
                    CookieResult cookieResulte = CookieDbUtil.getInstance().queryCookieBy(s);
                    if (cookieResulte == null) {
                        throw new ApiException("网络错误");
                    }
                    long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                    if (time < 60) {
                        if (mSubscriberOnNextListener.get() != null) {
                            mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte());
                        }
                    } else {
                        CookieDbUtil.getInstance().deleteCookie(cookieResulte);
                        throw new ApiException("网络错误");
                    }
                }
            });
        } else {
            doError(e);
        }

    }

    private void doError(Throwable e) {
        Context context = mActivity.get();

        if(context == null){
            return;
        }

        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if(e instanceof UnknownHostException){
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if(e instanceof SocketException){
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(mSubscriberOnNextListener.get() != null){
            mSubscriberOnNextListener.get().onError(e);
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }
}
