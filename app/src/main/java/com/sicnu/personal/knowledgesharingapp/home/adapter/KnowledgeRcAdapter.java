package com.sicnu.personal.knowledgesharingapp.home.adapter;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.home.model.databean.GankKnowledgeDataBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class KnowledgeRcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<GankKnowledgeDataBean.ResultsBean> mDatabean;
    private KnowledgeClickListenter mListener;
    public KnowledgeRcAdapter(Context context, ArrayList<GankKnowledgeDataBean.ResultsBean> dataBeen) {
        this.mContext = context;
        this.mDatabean = dataBeen;
    }

    public void loadMoreData(ArrayList<GankKnowledgeDataBean.ResultsBean> moreData) {
        if (this.mDatabean != null) {
            mDatabean.addAll(moreData);
            notifyDataSetChanged();
        }
    }

    public void refreshData(ArrayList<GankKnowledgeDataBean.ResultsBean> refreshData) {
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
                    Glide.with(mContext).load(mDatabean.get(position).getImages().get(0)).into(normalViewHolder.ivHomeRcItemBg);
                }else{
                    //无网络图片时，加载本地图片
                }
                normalViewHolder.tvKnowledgeDesc.setText(mDatabean.get(position).getDesc());
                normalViewHolder.tvKnowledgeTime.setText(mDatabean.get(position).getPublishedAt());
                normalViewHolder.tvKnowledgeWho.setText(mDatabean.get(position).getWho().toString());
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
        ImageView ivHomeRcItemBg;
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
