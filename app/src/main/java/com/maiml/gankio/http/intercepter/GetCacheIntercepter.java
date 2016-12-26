package com.maiml.gankio.http.intercepter;

import com.maiml.gankio.App;
import com.maiml.gankio.utils.LogUtil;
import com.maiml.gankio.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by maimingliang on 2016/12/26.
 * <p>
 * get 请求缓存
 */

public class GetCacheIntercepter implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        HttpUrl url = request.url();

        LogUtil.i("请求头信息 start ");
        LogUtil.i("url = " + url.toString());
//                LogUtil.i("request headers = " + request.headers().toString());
        LogUtil.i("请求头信息 end ");

        //更改请求头
        if (!NetworkUtil.isNetWorkActive(App.getInstance())) {
            //如果没有网络，那么就强制使用缓存数据
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        //获得返回头，如果有网络，就缓存一分钟,没有网络缓存四周
        Response originalResponse = chain.proceed(request);


//                LogUtil.i("响应头信息  start ");
//                LogUtil.i("response headers = " + originalResponse.headers().toString());
//                LogUtil.i("response body = "+originalResponse.body().toString());
//                LogUtil.i("响应头信息  end ");

        //更改响应头
        if (NetworkUtil.isNetWorkActive(App.getInstance())) {

            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=60")//有网失效一分钟
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 6)// 没网失效6小时
                    .build();
        }
      }


}
