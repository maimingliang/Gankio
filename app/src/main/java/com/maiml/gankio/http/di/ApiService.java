package com.maiml.gankio.http.di;

import com.maiml.gankio.bean.GankIoBean;
import com.maiml.gankio.http.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author maimingliang
 */
public interface ApiService {

    @GET("random/data/{category}/{num}")
    Observable<HttpResult<List<GankIoBean>>> findList(@Path("category") String category,@Path("num") int num);

}
