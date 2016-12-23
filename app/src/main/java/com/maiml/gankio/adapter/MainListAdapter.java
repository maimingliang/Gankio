package com.maiml.gankio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.maiml.gankio.R;
import com.maiml.gankio.bean.GankIoBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by fangxiao on 16/1/28.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    public List<GankIoBean> datas = null;

    Context context;

    public MainListAdapter(Context context, List<GankIoBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_list_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        GankIoBean gankIoBean = datas.get(position);

        viewHolder.tvTitle.setText(gankIoBean.getDesc());
        viewHolder.tvTime.setText(gankIoBean.getPublishedAt());

        if(gankIoBean.getImages() != null){
            Glide.with(context).load(gankIoBean.getImages().get(0)).placeholder(R.mipmap.ic_launcher).into(viewHolder.imgIcon);
        }else{
            viewHolder.imgIcon.setImageResource(R.mipmap.ic_launcher);
        }


        viewHolder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvTime;
        public RelativeLayout rlMain;
        public ImageView imgIcon;
//
        public ViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            imgIcon = (ImageView) view.findViewById(R.id.img_icon);
            rlMain = (RelativeLayout) view.findViewById(R.id.rl_main);

        }
    }




}