package com.sicnu.personal.knowledgesharingapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sicnu.personal.knowledgesharingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/4 0004.
 */

public class CommonRcFootVH extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_rc_foot)
    public  TextView tvRcFoot;
    public CommonRcFootVH(View itemView) {
        super(itemView);
            ButterKnife.bind(this, itemView);
    }

}
