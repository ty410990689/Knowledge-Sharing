package com.sicnu.personal.knowledgesharingapp.utils;

import android.content.Context;
import android.view.WindowManager;

import com.sicnu.personal.knowledgesharingapp.app.KnowledgeApplication;

/**
 * Created by Administrator on 2018/3/10 0010.
 */

public class PxUtils {
    public static final WindowManager wm = (WindowManager) KnowledgeApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE); ;
    public static final Context mContext = KnowledgeApplication.getAppContext();
    public static int getScreentWidth(){
        int width = wm.getDefaultDisplay().getWidth();
       return width;
    }
    public static int getScreentHeight(){
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
    public static int px2dp( float pxValue) {
        final float scale =  mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * convert dp to its equivalent px
     *
     * 将dp转换为与之相等的px
     */
    public static int dp2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * convert px to its equivalent sp
     *
     * 将px转换为sp
     */
    public static int px2sp(float pxValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
