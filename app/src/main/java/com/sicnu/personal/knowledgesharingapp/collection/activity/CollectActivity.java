package com.sicnu.personal.knowledgesharingapp.collection.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentFrameLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.collection.adapter.CollectAdapter;
import com.sicnu.personal.knowledgesharingapp.collection.contact.CollectContact;
import com.sicnu.personal.knowledgesharingapp.collection.model.databean.CollectDataBean;
import com.sicnu.personal.knowledgesharingapp.collection.presenter.CollectHanldPresenter;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

import static com.sicnu.personal.knowledgesharingapp.utils.PxUtils.dp2px;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class CollectActivity extends AppCompatActivity implements CollectContact.CollectView {

    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_toolbar_save)
    ImageView ivToolbarSave;
    @BindView(R.id.fl_toolbar)
    PercentFrameLayout flToolbar;
    @BindView(R.id.lv_collect_swipe_menu)
    SwipeMenuListView lvCollectSwipeMenu;
    CollectHanldPresenter presenter;
    CollectAdapter adapter;
    List<CollectDataBean> dataBeen;
    @BindView(R.id.view_stub)
    ViewStub viewStub;
    TextView tvErrorContent;
    ImageView ivErrorIcon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        initToolbar();
        inits();
    }

    private void inits() {

        dataBeen = new ArrayList<>();
        adapter = new CollectAdapter(this, dataBeen);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openItem.setWidth(dp2px(90));
                openItem.setTitle("Open");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle("Dele");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        lvCollectSwipeMenu.setMenuCreator(creator);
        lvCollectSwipeMenu.setAdapter(adapter);
        lvCollectSwipeMenu.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        presenter = new CollectHanldPresenter(this, this);
        presenter.quertUserCollectDatasFromBmob(BmobUser.getCurrentUser(this).getUsername());
    }

    private void initToolbar() {
        ivToolbarSave.setVisibility(View.GONE);
        tvToolbarTitle.setText(getString(R.string.my_collect));
    }

    @Override
    public void showQueryCollectDatasPage(List<CollectDataBean> dataBeen) {
        adapter.addLoadData(dataBeen);
    }

    @Override
    public void showCollectDataIsExitedPage() {

    }

    @Override
    public void showCollectSuccessfulPage() {

    }

    @Override
    public void showCollectErrorPage(int code, String reason) {
        showViewStubPage(Constant.SHOW_ERROR_PAGE);
    }

    @Override
    public void showQueryErrorPage(int code, String reason) {
        showViewStubPage(Constant.QUERY_ERROR);
    }


    public void showViewStubPage(int type){
        View ivStubView = viewStub.inflate();
        tvErrorContent = ivStubView.findViewById(R.id.tv_warning_content);
        ivErrorIcon = ivStubView.findViewById(R.id.iv_error_icon);

        if(type== Constant.QUERY_ERROR){
            tvErrorContent.setText(getResources().getString(R.string.queryCollectDataIsNull));
            ivErrorIcon.setImageResource(R.mipmap.icon_error_blue);
        }else if(type==Constant.SHOW_ERROR_PAGE)
        {
            tvErrorContent.setText(getResources().getString(R.string.showPageError));
            ivErrorIcon.setImageResource(R.mipmap.icon_red_error);
        }
        viewStub.setVisibility(View.VISIBLE);
    }
}
