package com.maiml.gankio.http;


import com.maiml.gankio.App;
import com.maiml.gankio.CookieDbUtil;
import com.maiml.gankio.bean.GankIoBean;
import com.maiml.gankio.http.di.ApiService;
import com.maiml.gankio.http.intercepter.CookieResult;
import com.maiml.gankio.http.rx.BaseSubscriber;
import com.maiml.gankio.http.rx.HttpResultFunc;
import com.maiml.gankio.http.rx.RetryWhenNetworkException;
import com.maiml.gankio.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author maimingliang
 */
public class DataManager {

  private static final String TAG = "DataManager";

  private final ApiService mApiService;

    @Inject
    public DataManager(ApiService apiService) {
        this.mApiService = apiService;
    }


    public void findList(String category, int num, final BaseSubscriber<List<GankIoBean>> s){

        LogUtil.d("findList params");
        LogUtil.d(category);
        LogUtil.d(num);

           /*获取缓存数据*/
        final CookieResult cookieResulte = CookieDbUtil.getInstance().queryCookieBy("http://maimingliang.me/api");
        if (cookieResulte != null) {
            long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
            if (time < 60) {

                Observable.just(cookieResulte.getResulte()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        s.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        s.onError(e);
                        CookieDbUtil.getInstance().deleteCookie(cookieResulte);
                    }

                    @Override
                    public void onNext(String str) {

                        LogUtil.d("读取缓存 = " + str);
                        s.onCacheNext(str);
                    }
                });

                return;

            }


        }

        Observable<List<GankIoBean>> observable = mApiService.findList(category, num)
                 .map(new HttpResultFunc<List<GankIoBean>>());

        toSubscribe(observable,s);

    }

    private <T> void toSubscribe(Observable<T> o, BaseSubscriber<T> s) {

               o.retryWhen(new RetryWhenNetworkException())  /*失败后的retry配置*/
                 .compose(s.getRxAppCompatActivity().<T>bindToLifecycle())     /*生命周期管理*/
                .subscribeOn(Schedulers.io())  /*http请求线程*/
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}




