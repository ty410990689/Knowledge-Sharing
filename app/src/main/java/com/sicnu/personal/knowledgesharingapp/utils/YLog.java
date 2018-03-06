package com.sicnu.personal.knowledgesharingapp.utils;

import android.util.Log;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class YLog {
    public static final boolean isDebug = true;
    public static void d(String mes){
        if(isDebug){
            Log.d("Rain.tang",mes);
        }
    }
}
