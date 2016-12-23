package com.maiml.gankio.http;


import com.maiml.gankio.bean.GankIoBean;
import com.maiml.gankio.http.di.ApiService;
import com.maiml.gankio.http.rx.HttpResultFunc;
import com.maiml.gankio.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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


    public void findList(String category, int num, Subscriber<List<GankIoBean>> s){

        LogUtil.d("findList params");
        LogUtil.d(category);
        LogUtil.d(num);

        Observable<List<GankIoBean>> observable = mApiService.findList(category, num)
                .map(new HttpResultFunc<List<GankIoBean>>());

        toSubscribe(observable,s);

    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}




