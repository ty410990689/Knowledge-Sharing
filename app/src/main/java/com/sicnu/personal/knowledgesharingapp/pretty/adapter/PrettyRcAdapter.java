package com.sicnu.personal.knowledgesharingapp.pretty.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.utils.FrescoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/4 0004.
 */

public class PrettyRcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PrettyDataBean.ResultsBean> prettyDataBeen;
    private Context mContext;

    public PrettyRcAdapter(Context context, List<PrettyDataBean.ResultsBean> data) {
        this.mContext = context;
        this.prettyDataBeen = data;
    }

    public void onRefreshData(List<PrettyDataBean.ResultsBean> dataBean){
        if(dataBean!=null){
            prettyDataBeen.clear();
            prettyDataBeen.addAll(dataBean);
            notifyDataSetChanged();
        }
    }

    public void onLoadMoreData(List<PrettyDataBean.ResultsBean> dataBean){
        if(dataBean!=null){
            prettyDataBeen.addAll(dataBean);
            notifyDataSetChanged();
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PrettyPictureViewHolder view = new PrettyPictureViewHolder(LayoutInflater.from(mContext).inflate(R.layout.pretty_picture_recycler_item, parent, false));
        return view;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PrettyPictureViewHolder){
            DraweeController mDraweeController = FrescoUtils.getDefaultImageRequest(prettyDataBeen.get(position).getImageUrl());
            ((PrettyPictureViewHolder) holder).ivPrettyPicture.setController(mDraweeController);
        }
    }

    @Override
    public int getItemCount() {
        return prettyDataBeen==null? 0:prettyDataBeen.size();
    }

    static class PrettyPictureViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pretty_picture)
        SimpleDraweeView ivPrettyPicture;


        public PrettyPictureViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
