package com.sicnu.personal.knowledgesharingapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.bmob.activity.LoginActivity;
import com.sicnu.personal.knowledgesharingapp.collection.activity.CollectActivity;
import com.sicnu.personal.knowledgesharingapp.collection.contact.CollectContact;
import com.sicnu.personal.knowledgesharingapp.collection.model.databean.CollectDataBean;
import com.sicnu.personal.knowledgesharingapp.collection.model.datasource.CollectResponse;
import com.sicnu.personal.knowledgesharingapp.collection.presenter.CollectHanldPresenter;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.activity.HomeActivity;
import com.sicnu.personal.knowledgesharingapp.view.ActionSheetDialog;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;


import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import rx.functions.Action1;


/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class CommonUtils {
    public static String cutUrlGetImageName(String imageUrl) {
        if (imageUrl.equals("flashpicture")) {
            return "flash_picture" + imageUrl.substring(imageUrl.lastIndexOf("."));
        } else {
            return imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.length());
        }

    }

    public static String getFileMD5(String filePath) {
        File file = new File(filePath);
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    public static String getDownLoadSavePath(Context mContext) {
        return (String) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.DOWNLOAD_PATH, Constant.DOWNLOAD_DEFALUT_PATH);
    }

    public static final String getDownLoadLocalPath(Context context, String fileName) {
        return Environment.getExternalStorageDirectory() + getDownLoadSavePath(context) + fileName;
    }


    public static void requestSdPermissiton(final Activity activity,Action1<Permission> action1) {
        new RxPermissions(activity).requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(action1);

    }

    public static void showPermissionDialog(final Context context) {
        //TODO 首先检查是否有权限， 有权限直接读写， 没有权限 拉起权限
        //有权限  跳过拉起设置权限步骤
        //没有权限 走以下步骤

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("权限提示");
        builder.setMessage("您目前还没有给多识App 开启访问存储权限， 可能会导致无法下载文件， 建议开启该权限");
        builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                simpleSetting(context,"SdCardPermisiion");

            }
        }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });

        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
    }
    public static void showNetDialog(final Activity context, final String type){
        //TODO 首先检查网络状态， 4G提示， 未连接网络直接退出
        //有权限  跳过拉起设置权限步骤
        //没有权限 走以下步骤
        String tipContent = "";
        String positiveText = "";
        String negativeText ="";
        final int open_mode ;
        if(type.equals(Constant.NET_IS_DISCONNECT)){
            open_mode=1;
            tipContent = "您当前处于无网状态，请先连接网络";
            positiveText = "去开启";
            negativeText = "退出";
        }else if(type.equals(Constant.NET_IS_CONNECT)){
            open_mode=2;
            tipContent = "多识App多图预警，您目前正处于使用流量状态。建议你三思而后行";
            positiveText = "我是土豪";
            negativeText = "退出";
        }else{
            open_mode = 0;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("温馨提示");
        builder.setMessage(tipContent);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(open_mode==1){
                    simpleSetting(context,"NetPermisiion");
                }else if(open_mode==2){
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        }).
                setNegativeButton(negativeText, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        context.finish();
                    }
                });

        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
    }
    private static void simpleSetting(Context context,String type) {
        Intent intent = new Intent();
        try{
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(type.equals("SdCardPermisiion")) {
                // 将用户引导到系统设置页面
                if (Build.VERSION.SDK_INT >= 9) {
                    Log.e("HLQ_Struggle", "APPLICATION_DETAILS_SETTINGS");
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
            }else if(type.equals("NetPermisiion")){
                intent.setAction(Settings.ACTION_DATA_ROAMING_SETTINGS);
            }
            context.startActivity(intent);
        } catch (Exception e) {//抛出异常就直接打开设置页面
            Log.e("HLQ_Struggle", e.getLocalizedMessage());
            intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        }
    }

    public static void showFunctionDialog(final Activity activity, final CollectHanldPresenter presenter, final CollectContact.CollectDataView dataView, final CollectDataBean dataBean){
        new ActionSheetDialog(activity).builder()
                .setTitle(activity.getString(R.string.function_menu_title))
                .setCancelable(true)
                .setCanceledOnTouchOutside(false)
                .addSheetItem(activity.getString(R.string.collect_text), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        presenter.addCollectToBmob(dataBean,dataView);
                    }
                })
                .addSheetItem(activity.getString(R.string.open_collect_page), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        activity.startActivity(new Intent(activity, CollectActivity.class));
                    }
                }).show();
    }

}
