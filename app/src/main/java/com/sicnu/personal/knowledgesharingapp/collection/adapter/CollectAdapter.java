package com.sicnu.personal.knowledgesharingapp.collection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.collection.model.databean.CollectDataBean;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class CollectAdapter extends BaseAdapter {
    private Context context;
    private List<CollectDataBean> datas;

    public CollectAdapter(Context context, List<CollectDataBean> dataBeanList) {
        this.context = context;
        this.datas = dataBeanList;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return datas.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CollectViewHolder viewHolder;
        View mView;
        if (view == null) {
            mView = LayoutInflater.from(context).inflate(R.layout.item_collect_listview, viewGroup, false);
            viewHolder = new CollectViewHolder(mView);
            mView.setTag(viewHolder);
        }else{
            mView = view;
            viewHolder = (CollectViewHolder) mView.getTag();
        }
        viewHolder.tvCollectDesc.setText(datas.get(i).getArticleDesc());
        viewHolder.tvCollectType.setText(datas.get(i).getArticleType());
        viewHolder.tvCollectWho.setText(datas.get(i).getArticleAuthor());
        return mView;
    }

    public void addLoadData(List<CollectDataBean> data){
        if(data!=null && data.size()>0){
            this.datas.addAll(data);
            notifyDataSetChanged();
        }
    }

     class CollectViewHolder {
        @BindView(R.id.tv_collect_desc)
        TextView tvCollectDesc;
        @BindView(R.id.tv_collect_type)
        TextView tvCollectType;
        @BindView(R.id.tv_collect_who)
        TextView tvCollectWho;

        CollectViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
