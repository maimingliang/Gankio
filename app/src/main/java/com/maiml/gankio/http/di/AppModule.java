package com.maiml.gankio.http.di;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maiml.gankio.http.DataManager;
import com.maiml.gankio.utils.FileUtils;
import com.maiml.gankio.utils.LogUtil;
import com.maiml.gankio.utils.NetworkUtil;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maimingliang
 */
@Module
public class AppModule {

    private final Context mContext;


    public AppModule(Context context) {
        this.mContext = context;
    }



    @Provides
    Interceptor providesIntercepter(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();


                HttpUrl url = request.url();

                LogUtil.i("请求头信息 start " );
                LogUtil.i("url = " + url.toString());
                LogUtil.i("request headers = " + request.headers().toString());
                LogUtil.i("请求头信息 end " );

                //更改请求头
                if (!NetworkUtil.isNetWorkActive(mContext)){
                    //如果没有网络，那么就强制使用缓存数据
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                //获得返回头，如果有网络，就缓存一分钟,没有网络缓存四周
                Response originalResponse = chain.proceed(request);


                LogUtil.i("响应头信息  start ");
                LogUtil.i("response headers = " + originalResponse.headers().toString());
                LogUtil.i("response body = "+originalResponse.body().toString());
                LogUtil.i("响应头信息  end ");

                 //更改响应头
                if (NetworkUtil.isNetWorkActive(mContext)){
                    String cacheControl = request.cacheControl().toString();
                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                             .build();
                }else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public,max-age=2419200")
                             .build();
                }
            }
        };
    }

    @Provides
    Cache providesCache(){
        File httpCacheFile = FileUtils.getDiskCacheDir("response");
        return new Cache(httpCacheFile, 1024 * 100 * 1024);
    }



    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Interceptor interceptor) {

        OkHttpClient okhttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) //设置出现错误进行重新连接。
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return chain.proceed(chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .build());
                    }
                })   //拦截器
                .addNetworkInterceptor(interceptor)
                .addInterceptor(interceptor)
                .cache(providesCache())
                .build();



         return okhttpClient;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okhttpClient) {

        Gson gson = new GsonBuilder().serializeNulls().create();


        Retrofit retrofit = new Retrofit.Builder()
                .client(okhttpClient)
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }


    @Provides
    public DataManager provideManager(ApiService service) {
        return new DataManager(service);
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }


}
