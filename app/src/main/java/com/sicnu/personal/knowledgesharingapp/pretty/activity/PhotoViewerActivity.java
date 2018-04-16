package com.sicnu.personal.knowledgesharingapp.pretty.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentFrameLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.net.MDownLoadManager;
import com.sicnu.personal.knowledgesharingapp.pretty.adapter.PhotoPagerAdapter;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.utils.ColorUtil;
import com.sicnu.personal.knowledgesharingapp.utils.CommonUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;
import com.sicnu.personal.knowledgesharingapp.view.PhotoViewPager;
import com.tbruyelle.rxpermissions.Permission;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class PhotoViewerActivity extends AppCompatActivity {
    @BindView(R.id.vp_photoviewer)
    PhotoViewPager vpPhotoviewer;
    @BindView(R.id.tv_photo_size_desc)
    TextView tvPhotoSizeDesc;
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_toolbar_save)
    ImageView ivToolbarSave;
    @BindView(R.id.toolbar_photoviewer)
    Toolbar toolbarPhotoviewer;
    @BindView(R.id.root_layout)
    PercentRelativeLayout rootLayout;
    @BindView(R.id.fl_toolbar)
    PercentFrameLayout flToolbar;
    private List<PrettyDataBean.ResultsBean> data;
    private int position = 0;
    private PhotoPagerAdapter adapter;
    private int currentPosition = 0;
    public static final int SHOW_SIZE_TOAST = 1;
    public static final int HIDE_SIZE_TOAST = 2;
    private String currentUrl = "";
    private BroadcastReceiver broadcastReceiver;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SHOW_SIZE_TOAST) {

            } else if (msg.what == HIDE_SIZE_TOAST) {
                tvPhotoSizeDesc.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        ButterKnife.bind(this);
        initToolbar();

        registerDownLoadBoradCastReceiver();
        data = (List<PrettyDataBean.ResultsBean>) getIntent().getSerializableExtra("imageUrlList");
        position = getIntent().getIntExtra("position", 0);
        currentPosition = position + 1;

        tvPhotoSizeDesc.setText(formatSizeString(currentPosition, data.size()));
        if (data != null) {
            YLog.d("intent_data : position : " + position + "   size : " + data.size());
            adapter = new PhotoPagerAdapter(data, this);
            vpPhotoviewer.setAdapter(adapter);
            vpPhotoviewer.setCurrentItem(position);
            setColor(position);
            vpPhotoviewer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPosition = position;
                    mHandler.removeMessages(HIDE_SIZE_TOAST);
                    tvPhotoSizeDesc.setVisibility(View.VISIBLE);
                    tvPhotoSizeDesc.setText(formatSizeString(currentPosition + 1, data.size()));
                    mHandler.sendEmptyMessageDelayed(HIDE_SIZE_TOAST, 1500);
                    setColor(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private void setColor(int pos) {
        SimpleDraweeView imageView = (SimpleDraweeView) adapter.getPrimaryItem();
        String path = data.get(pos).getImageUrl();
        if (path != null) {
            FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(path));
            if(resource==null)
                return;
            File file = resource.getFile();
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            Palette.Builder builder = Palette.from(bitmap);
            builder.generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
//                Palette.Swatch vir = palette.getLightMutedSwatch();
                    Palette.Swatch vir = palette.getDarkVibrantSwatch();
                    if (vir == null) {
                        YLog.d("color return");
                        return;
                    }
                    YLog.d("color : " + vir.getRgb());
                    flToolbar.setBackgroundColor(vir.getRgb());
                    if (Build.VERSION.SDK_INT >= 21) {
                        Window window = getWindow();
                        window.setStatusBarColor(ColorUtil.colorBurn(vir.getRgb()));
                        window.setNavigationBarColor(ColorUtil.colorBurn(vir.getRgb()));
                    }
                }
            });
        }
    }

    @OnClick({R.id.iv_toolbar_back, R.id.iv_toolbar_save})
    public void setOnCliclListener(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                // this.finish();
                String path = (String) MDownLoadManager.getInstance(this).queryDownLaodMessage(id, MDownLoadManager.DOWNLOAD_FILE_SAVE_PATH);
                break;
            case R.id.iv_toolbar_save:
                currentUrl = data.get(vpPhotoviewer.getCurrentItem()).getImageUrl();
                Toast.makeText(this, "请稍后...", Toast.LENGTH_SHORT).show();
                checkPermissionDownLoad();
                break;
        }
    }

    private void checkPermissionDownLoad() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CommonUtils.requestSdPermissiton(PhotoViewerActivity.this, new Action1<Permission>() {
                @Override
                public void call(Permission permission) {
                    if (permission.granted) {
                        // 用户允许权限
                        id = MDownLoadManager.getInstance(PhotoViewerActivity.this).addDownLoadTask(currentUrl, CommonUtils.cutUrlGetImageName(currentUrl));
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了权限申请
                        Snackbar.make(rootLayout, "你拒绝了访问", Snackbar.LENGTH_SHORT).show();
                    } else {
                        // 用户拒绝，并且选择不再提示
                        // 可以引导用户进入权限设置界面开启权限
                        CommonUtils.showDialog(PhotoViewerActivity.this);
                    }
                }
            });
        } else {
            id = MDownLoadManager.getInstance(PhotoViewerActivity.this).addDownLoadTask(currentUrl, CommonUtils.cutUrlGetImageName(currentUrl));
        }
    }

    private void initToolbar() {
        tvToolbarTitle.setText(getString(R.string.photoviewer));
    }

    private long id = 0;

    private String formatSizeString(int position, int size) {
        return String.format("%d / %d", position, size);
    }


    private void registerDownLoadBoradCastReceiver() {
        // 注册广播监听系统的下载完成事件。
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                    Snackbar.make(rootLayout, "下载成功", Snackbar.LENGTH_LONG).show();
                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }
}
