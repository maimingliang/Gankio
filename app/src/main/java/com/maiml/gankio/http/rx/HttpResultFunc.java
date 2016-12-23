package com.maiml.gankio.http.rx;

/**
 * Created by maimingliang on 16/6/22.
 */



import com.maiml.gankio.http.HttpResult;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
 *
 * @author maimingliang
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    private static final String TAG = "HttpResultFunc";

    @Override
    public T call(HttpResult<T> httpResult) {

        if (!ErrorHandler.isRequestSuccussful(httpResult.getStatus())) {
            throw new ApiException(httpResult.getStatus(),httpResult.getError()==null?"":httpResult.getError());
        }
        return httpResult.getResults();
    }
}

