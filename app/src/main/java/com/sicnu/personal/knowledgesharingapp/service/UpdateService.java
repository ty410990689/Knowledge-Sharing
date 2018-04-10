package com.sicnu.personal.knowledgesharingapp.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.sicnu.personal.knowledgesharingapp.flash.databean.FlashPicDataBean;
import com.sicnu.personal.knowledgesharingapp.net.MDownLoadManager;
import com.sicnu.personal.knowledgesharingapp.net.RetrofitClient;
import com.sicnu.personal.knowledgesharingapp.net.ServerApi;
import com.sicnu.personal.knowledgesharingapp.utils.CommonUtils;
import com.sicnu.personal.knowledgesharingapp.utils.SharedPreferencesUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class UpdateService extends Service {

    private BroadcastReceiver broadcastReceiver;
    private long downLoadId = -1;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getFlashInfo();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    public void getFlashInfo() {
        YLog.d("flash_log : getFlashInfo");
        RetrofitClient.getInstance().createReq(ServerApi.class).getFlashPicInfo("flashInfo.cfg")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FlashPicDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        YLog.d("flash_log :err :"+e.getMessage());
                    }

                    @Override
                    public void onNext(FlashPicDataBean flashPicDataBean) {
                        if(!flashPicDataBean.isError()){
                            String imageUrl = flashPicDataBean.getResults().getImageUrl();
                            String fileName = flashPicDataBean.getResults().getImageName();
                            String imageVersion = flashPicDataBean.getResults().getImageVersion();
                            String oldVersion = (String) SharedPreferencesUtils.getParam(UpdateService.this,SharedPreferencesUtils.FLASH_VERSION,"v0.0.0");
                            YLog.d("flash_log :onNext "+CommonUtils.getDownLoadLocalPath(UpdateService.this,fileName));

                            if(imageUrl!=null && imageUrl.length()>0 && imageVersion.compareTo(oldVersion)>0) {
                                File flashFile = new File(CommonUtils.getDownLoadLocalPath(UpdateService.this,fileName));
                                if(flashFile.exists()){
                                    flashFile.delete();
                                }
                                downLoadId = MDownLoadManager.getInstance(UpdateService.this).addDownLoadTask(
                                        imageUrl, fileName);
                                registerDownLoadBoradCastReceiver(flashPicDataBean.getResults().getImageMd5(), fileName,
                                        flashPicDataBean.getResults().getShowMode(),
                                        flashPicDataBean.getResults().getImageVersion());
                            }
                        }
                    }
                });
    }

    private void registerDownLoadBoradCastReceiver(final String md5, final String fileName, final String showMode, final String flashVersion ) {
        // 注册广播监听系统的下载完成事件。
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction()==DownloadManager.ACTION_DOWNLOAD_COMPLETE && downLoadId==intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -2)) {
                    checkImageMd5(md5,fileName,showMode,flashVersion);
                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }
    private void unRegisterDownLoadBoardReceiver(){
        if(broadcastReceiver!=null){
            this.unregisterReceiver(broadcastReceiver);
            broadcastReceiver=null;
        }
    }
    private void checkImageMd5(String md5,String fileName,String showMode,String flashVersion) {
        String savePath = CommonUtils.getDownLoadLocalPath(this,fileName);
        YLog.d("flash_log : savePath : "+savePath);
        String calculateMd5 = CommonUtils.getFileMD5(savePath);
        if(calculateMd5!=null) {
            YLog.d("flash_log : calculateMd5 : "+calculateMd5);
            if (calculateMd5.equals(md5)) {
                SharedPreferencesUtils.setParam(this, SharedPreferencesUtils.FLASH_MODE, showMode);
                SharedPreferencesUtils.setParam(this, SharedPreferencesUtils.FLASH_IMAGE_NAME, fileName);
                SharedPreferencesUtils.setParam(this,SharedPreferencesUtils.FLASH_VERSION,flashVersion);
            } else {
                File file = new File(savePath);
                if (file.exists()) {
                    file.delete();
                }
                SharedPreferencesUtils.setParam(this,SharedPreferencesUtils.FLASH_MODE,"local");
            }
        }
        unRegisterDownLoadBoardReceiver();
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterDownLoadBoardReceiver();
    }
}
