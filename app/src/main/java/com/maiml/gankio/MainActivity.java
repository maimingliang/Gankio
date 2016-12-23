package com.maiml.gankio;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maiml.gankio.fragment.MainFragment;
import com.maiml.gankio.utils.LogUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.user_img)
    ImageView userImg;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_autograph)
    TextView tvAutograph;
    @Bind(R.id.tv_change_theme)
    TextView tvChangeTheme;
    @Bind(R.id.tv_setting)
    TextView tvSetting;
    @Bind(R.id.nv_drawer_layout)
    LinearLayout nvDrawerLayout;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.mengban_view)
    ImageView mengbanView;
    @Bind(R.id.home_layout)
    FrameLayout homeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    private void initData() {

        Glide.with(this).load("http://fxblog.oss-cn-beijing.aliyuncs.com/avatar_img.png").placeholder(R.mipmap.ic_launcher).into(userImg);
        tvName.setText("潘金莲");
        tvSetting.setText("设置");
        tvChangeTheme.setText("切换主题");
        tvAutograph.setText("啦啦啦");
        setSupportActionBar(toolbar);
        setupToolbar();
        setFragment();
    }

    private void setupToolbar() {
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setTitle("首页");
        ab.setDisplayHomeAsUpEnabled(true);
    }



    private void setFragment() {

        Fragment fragment = MainFragment.newInstance();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content, fragment).commit();
            closeDrawer();
        } else {
            LogUtil.e(" fragment is null");
        }
    }
    protected void openDrawer() {
        if (drawerLayout == null)
            return;
        drawerLayout.openDrawer(GravityCompat.START);
    }

    protected void closeDrawer() {
        if (drawerLayout == null)
            return;
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
            case R.id.action_settings:
//                Bundle extras = new Bundle();
//                extras.putString(BaseWebActivity.BUNDLE_KEY_URL, "https://github.com/fangx");
//                extras.putString(BaseWebActivity.BUNDLE_KEY_TITLE, "关于我");
//                readyGo(BaseWebActivity.class, extras);
                break;
            case R.id.action_share:
//                BaseUtil.share(this, "分享项目地址", "https://github.com/fangx/ZhiHuMVP");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
