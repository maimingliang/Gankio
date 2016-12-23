package com.maiml.gankio.http.di;

import android.content.Context;
import android.widget.VideoView;

import com.maiml.gankio.fragment.AndroidFragment;
import com.maiml.gankio.fragment.ExsFragment;
import com.maiml.gankio.fragment.IOSFragment;
import com.maiml.gankio.fragment.MainFragment;
import com.maiml.gankio.fragment.MeiZhiFragment;
import com.maiml.gankio.fragment.QianduanFragment;
import com.maiml.gankio.fragment.VideoFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by maimingliang on 16/6/15.
 */
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    Context context();  // 提供Applicaiton的Context

    void inject(MeiZhiFragment fragment);
    void inject(AndroidFragment fragment);
    void inject(IOSFragment fragment);
    void inject(VideoFragment fragment);
    void inject(ExsFragment fragment);
    void inject(QianduanFragment fragment);
}
