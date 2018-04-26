package com.sicnu.personal.knowledgesharingapp.pretty.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.utils.FrescoUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class PhotoPagerAdapter extends PagerAdapter {
    private List<PrettyDataBean.ResultsBean> data;
    private AppCompatActivity mContext;

    private View mCurrentView;
    public PhotoPagerAdapter(List<PrettyDataBean.ResultsBean >databean, AppCompatActivity context){
        this.mContext = context;
        this.data=databean;

    }
    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }
    public Object instantiateItem(ViewGroup container, int position) {
        String url= data.get(position).getImageUrl();
        SimpleDraweeView photoView = new SimpleDraweeView(mContext);
        DraweeController controller = FrescoUtils.getDefaultImageRequest(url);
        photoView.setController(controller);
        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.finish();
            }
        });
        return photoView;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    public View getPrimaryItem() {
        return mCurrentView;
    }
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    public View getmCurrentView() {
        return mCurrentView;
    }
}
