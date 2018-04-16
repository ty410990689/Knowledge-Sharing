package com.sicnu.personal.knowledgesharingapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.sicnu.personal.knowledgesharingapp.utils.YLog;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class PhotoViewPager extends ViewPager {
    public PhotoViewPager(Context context) {
        super(context);
    }

    public PhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        }catch (IllegalArgumentException e){
            YLog.d("PhotoViewEx : "+e.getMessage());
            return false;
        }

    }
}
