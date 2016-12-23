package com.maiml.gankio.adapter;

import android.content.Context;
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

import java.util.List;


/**
 * Created by fangxiao on 16/1/28.
 */
public class MeiZhiListAdapter extends RecyclerView.Adapter<MeiZhiListAdapter.ViewHolder> {
    public List<GankIoBean> datas = null;

    Context context;

    public MeiZhiListAdapter(Context context, List<GankIoBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meizhi_list_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        GankIoBean gankIoBean = datas.get(position);

        Glide.with(context).load(gankIoBean.getUrl()).into(viewHolder.imgIcon);




    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgIcon;
//
        public ViewHolder(View view) {
            super(view);

            imgIcon = (ImageView) view.findViewById(R.id.img_icon);


        }
    }




}