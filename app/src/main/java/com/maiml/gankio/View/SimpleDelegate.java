package com.maiml.gankio.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maiml.gankio.R;
import com.maiml.gankio.base.AppDelegate;
import com.maiml.gankio.fragment.MainFragment;
import com.maiml.gankio.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.Bind;

public class SimpleDelegate extends AppDelegate {

    private Toolbar toolbar;
     FrameLayout content;
     ImageView userImg;
     TextView tvName;
     TextView tvAutograph;
     TextView tvChangeTheme;
     TextView tvSetting;
     LinearLayout nvDrawerLayout;
     DrawerLayout drawerLayout;
     ImageView mengbanView;
     FrameLayout homeLayout;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initWidget() {
        super.initWidget();
        toolbar = get(R.id.toolbar);
        content = get(R.id.content);
        userImg = get(R.id.user_img);
        tvChangeTheme = get(R.id.tv_change_theme);
        tvName = get(R.id.tv_name);
        tvAutograph = get(R.id.tv_autograph);
        tvSetting = get(R.id.tv_setting);
        nvDrawerLayout = get(R.id.nv_drawer_layout);
        drawerLayout = get(R.id.drawer_layout);
        mengbanView = get(R.id.mengban_view);
        homeLayout = get(R.id.home_layout);


        Glide.with(getActivity()).load("http://fxblog.oss-cn-beijing.aliyuncs.com/avatar_img.png").placeholder(R.mipmap.ic_launcher).into(userImg);
        tvName.setText("潘金莲");
        tvSetting.setText("设置");
        tvChangeTheme.setText("切换主题");
        tvAutograph.setText("啦啦啦");
        initToolbar();
        setupToolbar();
        setFragment();

    }
    protected void initToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            LogUtil.d("is not null");
            ((RxAppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }else{
            LogUtil.d("is  null");
        }
    }
    private void setFragment() {

        Fragment fragment = MainFragment.newInstance();
        if (fragment != null) {
            FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content, fragment).commit();
            closeDrawer();
        } else {
            LogUtil.e(" fragment is null");
        }
    }
    private void setupToolbar() {
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setTitle("首页");
        ab.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    public void openDrawer() {
        if (drawerLayout == null)
            return;
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        if (drawerLayout == null)
            return;
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    @Override
    public int getOptionsMenuId() {
        return R.menu.sample_actions;
    }


}
