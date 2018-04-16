package com.sicnu.personal.knowledgesharingapp.gank.relaxmedia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.percent.PercentFrameLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.gank.model.databean.GankDataBean;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;
import com.sicnu.personal.knowledgesharingapp.viewholder.CommonRcFootVH;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/15 0015.
 */

public class RelaxMediaRcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<GankDataBean.ResultsBean> mDatabean;
    private KnowledgeClickListenter mListener;
    private static boolean isFade = false;
    private static boolean haveMore = false;
    private int[] positionList = {};
    public RelaxMediaRcAdapter(Context context, List<GankDataBean.ResultsBean> dataBeen) {
        this.mContext = context;
        this.mDatabean = dataBeen;

    }



    public void loadMoreData(List<GankDataBean.ResultsBean> moreData) {
        if (this.mDatabean != null) {
            haveMore = true;
            this.mDatabean.addAll(moreData);
            notifyDataSetChanged();
        } else {
            haveMore = false;
        }
    }

    public boolean isFade() {
        return isFade;
    }

    public void refreshData(List<GankDataBean.ResultsBean> refreshData) {
        if (mDatabean != null) {
            mDatabean.clear();
            mDatabean.addAll(refreshData);
            notifyDataSetChanged();
        }
    }

    public void setItemClickListener(KnowledgeClickListenter listener) {
        if (mListener == null) {
            this.mListener = listener;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constant.RC_NORMAL_TYPE) {
            return new KnowledgeNormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.knowledge_home_recycler_item, parent, false));
        } else {
            return new CommonRcFootVH(LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_foot, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onKnowledgeClickListener(view, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mListener.onKnowledgeLongClickListener(view, position);
                    return true;
                }
            });
        }
        if (holder instanceof KnowledgeNormalViewHolder) {
            if (mDatabean.size() > 0) {
                KnowledgeNormalViewHolder normalViewHolder = ((KnowledgeNormalViewHolder) holder);
                initItemBgAndTvTextColor(normalViewHolder,position);
                normalViewHolder.tvKnowledgeDesc.setText(mDatabean.get(position).getDesc());
                normalViewHolder.tvKnowledgeTime.setText(mDatabean.get(position).getPublishedAt());
                normalViewHolder.viewHomeRcItemCover.setBackgroundColor(Color.TRANSPARENT);
                if (mDatabean.get(position).getWho() != null) {
                    normalViewHolder.tvKnowledgeWho.setText(mDatabean.get(position).getWho().toString());
                } else {
                    normalViewHolder.tvKnowledgeWho.setText("UnKnown");
                }
            }
        } else if (holder instanceof CommonRcFootVH) {
            if (haveMore) {
                isFade = false;
            } else {
                if (mDatabean.size() > 0) {
                    isFade = true;
                    haveMore = false;
                }
            }
        }
    }

    private void initItemBgAndTvTextColor(KnowledgeNormalViewHolder holder,int position) {
        Random rand = new Random();
        holder.ivHomeRcItemBg.setImageURI(Uri.parse("res:///"+Constant.RELAX_MEDIA_BG[position%16]));
        if(position%2==0) {
            YLog.d("Radom : I0");
            holder.tvKnowledgeDesc.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.tvKnowledgeTime.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.tvKnowledgeWho.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }else if(position%2==1){
            YLog.d("Radom : I1");
            holder.tvKnowledgeDesc.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.tvKnowledgeTime.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.tvKnowledgeWho.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        }else{
            YLog.d("Radom : I3");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return Constant.RC_LOAD_TYPE;
        } else {
            return Constant.RC_NORMAL_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mDatabean == null ? 0 : mDatabean.size() + 1;
    }

    public interface KnowledgeClickListenter {
        void onKnowledgeClickListener(View view, int pos);

        void onKnowledgeLongClickListener(View view, int pos);
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
        @BindView(R.id.layout_main_rc_item)
        PercentFrameLayout rcItemMainLayout;

        public KnowledgeNormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

