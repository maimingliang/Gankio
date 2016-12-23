package com.maiml.gankio.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maiml.gankio.R;
import com.maiml.gankio.View.IMainView;
import com.maiml.gankio.adapter.MainListAdapter;
import com.maiml.gankio.bean.GankIoBean;
import com.maiml.gankio.common.SearchType;
import com.maiml.gankio.http.DataManager;
import com.maiml.gankio.http.di.ComponentHolder;
import com.maiml.gankio.presenter.MainPresenter;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.fangx.haorefresh.HaoRecyclerView;
import me.fangx.haorefresh.LoadMoreListener;


public class VideoFragment extends Fragment implements IMainView{


    @Bind(R.id.recycleview)
    HaoRecyclerView recycleview;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    private FloatingActionButton fabButton;
    private List<GankIoBean> datas = new ArrayList<>();
    private Activity activity;
    private MainListAdapter mainListAdapter;

    @Inject
    DataManager dataManager;
    private MainPresenter mainPresenter;

    public FloatingActionButton getFabButton() {
        return fabButton;
    }

    public void setFabButton(FloatingActionButton fabButton) {
        this.fabButton = fabButton;
    }

    public VideoFragment() {
        // Required empty public constructor
    }


    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mei_zhi, container, false);
        ButterKnife.bind(this, view);
        ComponentHolder.getAppComponent().inject(this);
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.findList(SearchType.NEW);
            }
        });

        recycleview.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mainPresenter.findList(SearchType.OLD);
            }
        });
    }

    private void initData() {

        swiperefresh.setColorSchemeResources(R.color.textBlueDark, R.color.textBlueDark, R.color.textBlueDark,
                R.color.textBlueDark);

        mainPresenter = new MainPresenter(activity,this,dataManager);
        mainListAdapter = new MainListAdapter(activity,datas);

        recycleview.setAdapter(mainListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleview.setLayoutManager(layoutManager);
        if(fabButton != null){
            fabButton.attachToRecyclerView(recycleview);
        }

        //设置自定义加载中和到底了效果
//        ProgressBar progressBar = new ProgressBar(activity);
//        progressBar.setIndeterminate(false);
//        // 给progressbar准备一个FrameLayout的LayoutParams
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        // 设置对其方式为：屏幕居中对其
//        lp.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
//        progressBar.setLayoutParams(lp);
//        recycleview.setFootLoadingView(progressBar);

         TextView textView = new TextView(activity);
        textView.setText("已经到底啦~");
        recycleview.setFootEndView(textView);

        mainPresenter.findList(SearchType.NEW);

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void notifyDataChange(List<GankIoBean> list, SearchType searchType) {
        if(searchType == SearchType.NEW){
            recycleview.refreshComplete();
            recycleview.loadMoreComplete();
            swiperefresh.setRefreshing(false);
            datas.clear();
            datas.addAll(list);
            mainListAdapter.notifyDataSetChanged();
        }else{

            if (list == null || list.size() <= 0) {
                recycleview.loadMoreEnd();
            } else {
                datas.clear();
                datas.addAll(list);
                mainListAdapter.notifyDataSetChanged();
                recycleview.loadMoreComplete();
            }
        }
    }

    @Override
    public String getCategory() {
        return "休息视频";
    }


}
