package com.maiml.gankio.presenter;

import android.content.Context;

import com.maiml.gankio.View.IMainView;
import com.maiml.gankio.bean.GankIoBean;
import com.maiml.gankio.common.SearchType;
import com.maiml.gankio.http.DataManager;
import com.maiml.gankio.http.rx.NormalSubscriber;
import com.maiml.gankio.http.rx.ProgressSubscriber;
import com.maiml.gankio.http.rx.SubscriberOnNextListener;
import com.maiml.gankio.utils.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by maimingliang on 2016/12/22.
 */

public class MainPresenter {

    private Context context;
    private IMainView iMainView;
    private DataManager dataManager;
    public static int PAGE_SIZE = 10;
    public int page = 0;

    public MainPresenter(Context context, IMainView iMainView, DataManager dataManager) {
        this.context = context;
        this.iMainView = iMainView;
        this.dataManager = dataManager;
    }


    public void findList(final SearchType searchType){


        if(SearchType.NEW == searchType){
            page = 0;
        }
        page++;
        SubscriberOnNextListener<List<GankIoBean>> s = new SubscriberOnNextListener<List<GankIoBean>>() {
            @Override
            public void onNext(List<GankIoBean> gankIoBeen) {
                iMainView.notifyDataChange(gankIoBeen,searchType);
            }
        };


        dataManager.findList(iMainView.getCategory(),page * PAGE_SIZE,new NormalSubscriber<List<GankIoBean>>(context,"random/data",s));

    }

}
