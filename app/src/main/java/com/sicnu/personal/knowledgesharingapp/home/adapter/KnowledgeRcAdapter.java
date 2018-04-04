package com.sicnu.personal.knowledgesharingapp.home.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Px;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.app.KnowledgeApplication;
import com.sicnu.personal.knowledgesharingapp.home.model.databean.GankKnowledgeDataBean;
import com.sicnu.personal.knowledgesharingapp.utils.FrescoUtils;
import com.sicnu.personal.knowledgesharingapp.utils.PxUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class KnowledgeRcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<GankKnowledgeDataBean.ResultsBean> mDatabean;
    private KnowledgeClickListenter mListener;
    public KnowledgeRcAdapter(Context context, List<GankKnowledgeDataBean.ResultsBean> dataBeen) {
        this.mContext = context;
        this.mDatabean = dataBeen;
    }

    public void loadMoreData(List<GankKnowledgeDataBean.ResultsBean> moreData) {
        if (this.mDatabean != null) {
            YLog.d("Adapter LoadMore");

            this.mDatabean.addAll(moreData);
            notifyDataSetChanged();
        }
    }

    public void refreshData(List<GankKnowledgeDataBean.ResultsBean> refreshData) {
        if (mDatabean != null) {
            mDatabean.clear();
            mDatabean.addAll(refreshData);
            notifyDataSetChanged();
        }
    }
    public void setItemClickListener(KnowledgeClickListenter listener){
        if(mListener==null){
            this.mListener = listener;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new KnowledgeNormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.knowledge_home_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(mListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onKnowledgeClickListener(view,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mListener.onKnowledgeLongClickListener(view,position);
                    return true;
                }
            });
        }
        if(holder instanceof KnowledgeNormalViewHolder){
            if(mDatabean.size()>0) {
                KnowledgeNormalViewHolder normalViewHolder = ((KnowledgeNormalViewHolder) holder);
                if(mDatabean.get(position).getImages()!=null && mDatabean.get(position).getImages().size()>0) {
                    //加载Json数据中的网络图片
                    DraweeController mDraweeController = FrescoUtils.getDefaultImageRequest(mDatabean.get(position).getImages().get(0));
                    normalViewHolder.ivHomeRcItemBg.setController(mDraweeController);

                }else{
                    //无网络图片时，加载本地图片
                    Uri uri = Uri.parse("res:///"+R.mipmap.ic_launcher);
                    normalViewHolder.ivHomeRcItemBg.setImageURI(uri);
                }
                normalViewHolder.tvKnowledgeDesc.setText(mDatabean.get(position).getDesc());
                normalViewHolder.tvKnowledgeTime.setText(mDatabean.get(position).getPublishedAt());
                if(mDatabean.get(position).getWho()!=null){
                    normalViewHolder.tvKnowledgeWho.setText(mDatabean.get(position).getWho().toString());
                }else{
                    normalViewHolder.tvKnowledgeWho.setText("UnKnown");
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDatabean.size();
    }
    public  interface KnowledgeClickListenter{
        void onKnowledgeClickListener(View view,int pos);
        void onKnowledgeLongClickListener(View view,int pos);
    }
    static class KnowledgeNormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_home_rc_item_bg)
        SimpleDraweeView ivHomeRcItemBg;
        @BindView(R.id.view_home_rc_item_cover)
        View viewHomeRcItemCover;
        @BindView(R.id.tv_knowledge_desc)
        TextView tvKnowledgeDesc;
        @BindView(R.id.tv_knowledge_who)
        TextView tvKnowledgeWho;
        @BindView(R.id.tv_knowledge_time)
        TextView tvKnowledgeTime;
        @BindView(R.id.rl_item_home_bg)
        PercentRelativeLayout rlItemHomeBg;
        public KnowledgeNormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
