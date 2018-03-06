package com.sicnu.personal.knowledgesharingapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class NetConnectUtils {
    private   static NetConnectUtils netConnectUtils;
    ConnectivityManager connectivityManager;
    public static NetConnectUtils getInstance(Context context){
        if(netConnectUtils==null){
            synchronized (NetConnectUtils.class){
                netConnectUtils = new NetConnectUtils(context);
            }
        }
        return netConnectUtils;
    }
    private NetConnectUtils(Context context){
        if(connectivityManager==null && context!=null){
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

    public boolean isNetWorkConnected(){
        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(mNetworkInfo!=null){
            return true;
        }
        return false;
    }

    public boolean isWifiConnected(){
        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(mNetworkInfo!=null&&mNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return false;
    }
}
