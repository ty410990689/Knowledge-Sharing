package com.sicnu.personal.knowledgesharingapp.home.activity;


import android.os.Bundle;
import android.support.percent.PercentFrameLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.home.adapter.HomeViewPagerAdapter;
import com.sicnu.personal.knowledgesharingapp.home.fragment.AndroidFragment;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.tv_title_web)
    TextView tvTitleWeb;
    @BindView(R.id.tv_title_ios)
    TextView tvTitleIos;
    @BindView(R.id.tv_title_android)
    TextView tvTitleAndroid;
    @BindView(R.id.fragmelayout_home_title)
    PercentFrameLayout fragmelayoutHomeTitle;
    @BindView(R.id.view_interlaced)
    View viewInterlaced;
    @BindView(R.id.viewpager_home)
    ViewPager viewpagerHome;
    @BindView(R.id.activity_main)
    PercentRelativeLayout activityMain;

    HomeViewPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ArrayList<Fragment> fragments = initFragments();
        mAdapter = new HomeViewPagerAdapter(this.getSupportFragmentManager(),this,fragments);
        viewpagerHome.setAdapter(mAdapter);
        viewpagerHome.addOnPageChangeListener(pageChangeListener);
        viewpagerHome.setOffscreenPageLimit(3);
    }
    @OnClick({R.id.tv_title_android,R.id.tv_title_ios,R.id.tv_title_web})
    public void setOnClickListener(View view) {
        switch (view.getId()){
            case R.id.tv_title_android:
                viewpagerHome.setCurrentItem(Constant.ANDROID_FRAGMENT,true);
                Toast.makeText(this, "Android", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_title_ios:
                Toast.makeText(this, "IOS", Toast.LENGTH_SHORT).show();
                viewpagerHome.setCurrentItem(Constant.IOS_FRAGMENT,true);
                break;

            case R.id.tv_title_web:
                Toast.makeText(this, "Web", Toast.LENGTH_SHORT).show();
                viewpagerHome.setCurrentItem(Constant.WEB_FRAGMENT,true);
                break;
        }
    }

    private ArrayList<Fragment> initFragments() {
        ArrayList<Fragment> lists = new ArrayList<>();
        lists.add(new AndroidFragment());
        lists.add(new AndroidFragment());
        lists.add(new AndroidFragment());
        return lists;
    }
    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int wid = tvTitleIos.getWidth();
            viewInterlaced.setTranslationX((wid * (positionOffset + position)));

        }

        @Override
        public void onPageSelected(int position) {
            YLog.d("scro_position: "+position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
