package com.maiml.gankio.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.maiml.gankio.R;
import com.melnykov.fab.FloatingActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainFragment extends Fragment {


    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.fab_button)
    FloatingActionButton fabButton;
    private Activity activity;

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        viewPager.setAdapter(new MainFragmentAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class MainFragmentAdapter extends FragmentPagerAdapter {
        private String[] mTab = new String[]{"福利", "Android","iOS","休息视频","拓展资源","前端"};

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return MeiZhiFragment.newInstance();
            } else if(position == 1){
                return AndroidFragment.newInstance();
            }else if(position == 2){
                return IOSFragment.newInstance();
            }else if(position == 3){
                return VideoFragment.newInstance();
            }else if(position == 4){
                return ExsFragment.newInstance();
            }else {
                return QianduanFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return mTab.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTab[position];
        }
    }
}