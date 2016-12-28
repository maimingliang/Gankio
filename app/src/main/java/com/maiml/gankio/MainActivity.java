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
import com.maiml.gankio.View.SimpleDelegate;
import com.maiml.gankio.base.ActivityPresenter;
import com.maiml.gankio.fragment.BaseLazyFragment;
import com.maiml.gankio.fragment.MainFragment;
import com.maiml.gankio.netstatus.NetUtils;
import com.maiml.gankio.utils.LogUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ActivityPresenter<SimpleDelegate> {



    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }


    @Override
    protected Class getDelegateClass() {
        return SimpleDelegate.class;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                 viewDelegate.openDrawer();
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
