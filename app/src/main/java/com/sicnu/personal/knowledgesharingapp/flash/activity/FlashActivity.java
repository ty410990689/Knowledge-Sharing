package com.sicnu.personal.knowledgesharingapp.flash.activity;


import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;



import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;

import com.sicnu.personal.knowledgesharingapp.gank.knowledge.activity.HomeActivity;
import com.sicnu.personal.knowledgesharingapp.utils.CommonUtils;
import com.sicnu.personal.knowledgesharingapp.utils.NetConnectUtils;
import com.sicnu.personal.knowledgesharingapp.utils.SharedPreferencesUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class FlashActivity extends Activity {


    @BindView(R.id.iv_flash_main)
    ImageView ivFlashMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_main);
        ButterKnife.bind(this);
        String flashMode = (String) SharedPreferencesUtils.getParam(this, SharedPreferencesUtils.FLASH_MODE, "local");
        String fileName = (String) SharedPreferencesUtils.getParam(this,SharedPreferencesUtils.FLASH_IMAGE_NAME,"null");
        YLog.d("flash_log mode : "+flashMode+"  fileName : "+fileName);
        File flashImage = new File(CommonUtils.getDownLoadLocalPath(this,fileName));
        if (flashMode.equals("remote")&& !fileName.equals("null")&& flashImage.exists()) {
            //显示后台推送的图片
            ivFlashMain.setImageURI(Uri.fromFile(flashImage));
        } else {
            //显示默认图片
            ivFlashMain.setBackground(getResources().getDrawable(R.mipmap.bg_default_flash));
        }
        startAnimation();
    }
    private void startAnimation(){
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f,1.1f,1f,1.1f,
                    Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if( NetConnectUtils.getInstance(FlashActivity.this).isWifiConnected()) {
                    YLog.d("Can Use");
                    startHomeActivity(Constant.WIFI_IS_CONNECT);
                }else if(!NetConnectUtils.getInstance(FlashActivity.this).isNetWorkConnected()){
                    //无网络连接
                    YLog.d("NO NET");
                    startHomeActivity(Constant.NET_IS_DISCONNECT);
                }else{
                    //有网切不是WIFI
                    YLog.d("HAVE NET isnot WIFI");
                    startHomeActivity(Constant.NET_IS_CONNECT);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivFlashMain.startAnimation(scaleAnimation);
    }

    private void startHomeActivity(String action){
        Intent startHomeIntent = new Intent(FlashActivity.this, HomeActivity.class);
        startHomeIntent.putExtra("netState",action);
        startActivity(startHomeIntent);
    }
}
