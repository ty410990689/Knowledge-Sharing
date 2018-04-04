package com.sicnu.personal.knowledgesharingapp.pretty.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.home.adapter.KnowledgeRcAdapter;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.utils.FrescoUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;
import com.sicnu.personal.knowledgesharingapp.viewholder.CommonRcFootVH;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/4 0004.
 */

public class PrettyRcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PrettyDataBean.ResultsBean> prettyDataBeen;
    private Context mContext;
    private static boolean isFade = false;
    private static boolean haveMore = false;
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
    public boolean isFade(){
        return isFade;
    }
    public void onLoadMoreData(List<PrettyDataBean.ResultsBean> dataBean){
        if(dataBean!=null){
            prettyDataBeen.addAll(dataBean);
            notifyDataSetChanged();
            haveMore=true;
        }else{
            haveMore=false;
        }
    }
    public void setIfHaveMoreData(boolean datamore){
        haveMore =datamore;
    }
    public int getItemViewType(int position) {
        if(position+1==getItemCount()){
            return Constant.RC_LOAD_TYPE;
        }else{
            return Constant.RC_NORMAL_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==Constant.RC_NORMAL_TYPE) {
            return new PrettyPictureViewHolder(LayoutInflater.from(mContext).inflate(R.layout.pretty_picture_recycler_item, parent, false));
        }else{
            return new CommonRcFootVH(LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_foot, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PrettyPictureViewHolder){
            DraweeController mDraweeController = FrescoUtils.getDefaultImageRequest(prettyDataBeen.get(position).getImageUrl());
            ((PrettyPictureViewHolder) holder).ivPrettyPicture.setController(mDraweeController);
        }else if(holder instanceof CommonRcFootVH){
            if(haveMore){
                isFade = false;
            }else{
                if(prettyDataBeen.size()>0){
                    isFade = true;
                    haveMore =false;
                }
            }
            if(haveMore==false && prettyDataBeen.size()>0){
                ((CommonRcFootVH) holder).tvRcFoot.setText("无新数据可加载...");
            }
        }
    }

    @Override
    public int getItemCount() {
        return prettyDataBeen==null? 0:prettyDataBeen.size()+1;
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
