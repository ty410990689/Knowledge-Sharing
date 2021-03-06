package com.sicnu.personal.knowledgesharingapp.gank.knowledge.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentFrameLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.bmob.activity.LoginActivity;
import com.sicnu.personal.knowledgesharingapp.collection.activity.CollectActivity;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.adapter.HomeViewPagerAdapter;
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.fragment.AndroidFragment;
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.fragment.IosFragment;
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.fragment.WebFragment;
import com.sicnu.personal.knowledgesharingapp.gank.relaxmedia.activity.RelaxMediaActivity;
import com.sicnu.personal.knowledgesharingapp.pretty.activity.PrettyPictureActivity;
import com.sicnu.personal.knowledgesharingapp.utils.FrescoUtils;
import com.sicnu.personal.knowledgesharingapp.utils.SharedPreferencesUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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


    HomeViewPagerAdapter mAdapter;
    @BindView(R.id.navigation_view_home)
    NavigationView navigationViewHome;
    @BindView(R.id.reltv_main)
    PercentRelativeLayout reltvMain;
    @BindView(R.id.drawer_main_layout)
    DrawerLayout drawerMainLayout;
    @BindView(R.id.iv_title_menu)
    ImageView ivTitleMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inits();

    }

    private void inits() {
        ArrayList<Fragment> fragments = initFragments();
        mAdapter = new HomeViewPagerAdapter(this.getSupportFragmentManager(), this, fragments);
        viewpagerHome.setAdapter(mAdapter);
        viewpagerHome.addOnPageChangeListener(pageChangeListener);
        viewpagerHome.setOffscreenPageLimit(3);
        navigationViewHome.setItemIconTintList(null);
        navigationViewHome.setNavigationItemSelectedListener(this);
        View view = navigationViewHome.getHeaderView(0);
        String imageUrl = (String) SharedPreferencesUtils.getParam(this, SharedPreferencesUtils.USER_PHOTO_PATH, "null");
        if (!imageUrl.equals("null")) {
            SimpleDraweeView imageView = view.findViewById(R.id.iv_header_bg);
            DraweeController controller = FrescoUtils.getDefaultImageRequest(imageUrl);
            imageView.setController(controller);
        }

    }

    @OnClick({R.id.tv_title_android, R.id.tv_title_ios, R.id.tv_title_web, R.id.iv_title_menu})
    public void setOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_title_android:
                viewpagerHome.setCurrentItem(Constant.ANDROID_FRAGMENT, true);

                break;

            case R.id.tv_title_ios:

                viewpagerHome.setCurrentItem(Constant.IOS_FRAGMENT, true);
                break;

            case R.id.tv_title_web:
                viewpagerHome.setCurrentItem(Constant.WEB_FRAGMENT, true);
                break;

            case R.id.iv_title_menu:
                drawerMainLayout.openDrawer(Gravity.LEFT);
                break;
        }
    }

    private ArrayList<Fragment> initFragments() {
        ArrayList<Fragment> lists = new ArrayList<>();
        lists.add(new IosFragment());
        lists.add(new AndroidFragment());
        lists.add(new WebFragment());
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
            YLog.d("scro_position: " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private long exitTime = 0;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_photo:
                startActivity(new Intent(this, PrettyPictureActivity.class));
                break;

            case R.id.nav_relax_media:
                startActivity(new Intent(this, RelaxMediaActivity.class));
                break;
            case R.id.nav_loginout:
                if (System.currentTimeMillis() - exitTime > 2000) {
                    Snackbar.make(drawerMainLayout, getString(R.string.snack_exit_tip), Snackbar.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    SharedPreferencesUtils.setParam(this, Constant.USERNAME, "null");
                    SharedPreferencesUtils.getParam(this, Constant.PASSWORD, "null");
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                break;

            case R.id.nav_favorite:
                startActivity(new Intent(this, CollectActivity.class));
                break;

        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(navigationViewHome.getVisibility()==View.VISIBLE){
                drawerMainLayout.closeDrawers();
                return true;
            }else{
                this.finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
