package com.sicnu.personal.knowledgesharingapp.collection.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentFrameLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.activity.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

import static com.sicnu.personal.knowledgesharingapp.utils.PxUtils.dp2px;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class CollectActivity extends AppCompatActivity implements SwipeMenuListView.OnMenuItemClickListener, CollectContact.QueryDataView, CollectContact.DeleteDataView {

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
    @BindView(R.id.rl_rootview)
    RelativeLayout rlRootview;
    private int currentClickPosition;
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
                openItem.setTitle(getString(R.string.item_open));
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle(getString(R.string.item_delete));
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        lvCollectSwipeMenu.setMenuCreator(creator);
        lvCollectSwipeMenu.setAdapter(adapter);
        lvCollectSwipeMenu.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        presenter = new CollectHanldPresenter(this);
        presenter.quertUserCollectDatasFromBmob(BmobUser.getCurrentUser(this).getUsername(),this);
        lvCollectSwipeMenu.setOnMenuItemClickListener(this);
    }

    private void initToolbar() {
        ivToolbarSave.setVisibility(View.GONE);
        tvToolbarTitle.setText(getString(R.string.my_collect));
    }

    @OnClick(R.id.iv_toolbar_back)
    public void setOnClickListener() {
        this.finish();
    }




    public void showViewStubPage(int type) {
        View ivStubView = viewStub.inflate();
        tvErrorContent = ivStubView.findViewById(R.id.tv_warning_content);
        ivErrorIcon = ivStubView.findViewById(R.id.iv_error_icon);

        if (type == Constant.QUERY_ERROR) {
            tvErrorContent.setText(getString(R.string.query_collect_datas_is_null));
            ivErrorIcon.setImageResource(R.mipmap.icon_error_blue);
        } else if (type == Constant.SHOW_ERROR_PAGE) {
            tvErrorContent.setText(getString(R.string.error_page_tip));
            ivErrorIcon.setImageResource(R.mipmap.icon_red_error);
        }
        viewStub.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        currentClickPosition = position;
        if (index == 0) {//Open
            onMenuItenFunctionSelect(position, index);
        } else if (index == 1) {//Delete
            onMenuItenFunctionSelect(position, index);
        }
        return true;
    }

    public void onMenuItenFunctionSelect(int pos, int index) {
        if (index == 0) {//Open
            String type = dataBeen.get(pos).getArticleMold();
            Intent intent = new Intent(this, WebActivity.class);
            if (type.equals(getString(R.string.relax_media_mold))) {
                intent.putExtra(Constant.INTENT_WEB_TYPE, Constant.INTENT_WEB_VIDEO_TYPE);
            } else if (type.equals(getString(R.string.knownledge_api_mold))) {
                intent.putExtra(Constant.INTENT_WEB_TYPE, Constant.INTENT_WEB_KNOWLEDGE_TYPE);
            }
            intent.putExtra(Constant.INTENT_WEB_URL, dataBeen.get(pos).getArticleLink());
            startActivity(intent);
        } else if (index == 1) {//Delete
            presenter.deleteCollectDataForBmob(dataBeen.get(pos),this);
        }

    }

    @Override
    public void showDeleteCollectFailedPage(int code, String reason) {
        Snackbar.make(rlRootview,getString(R.string.delete_failed)+reason,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteCollectSuccessfulPage() {
        adapter.deleteItem(currentClickPosition);
        Snackbar.make(rlRootview,getString(R.string.delete_successful),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showQueryCollectDatasPage(List<CollectDataBean> dataBeen) {
        adapter.addLoadData(dataBeen);
    }

    @Override
    public void showQueryErrorPage(int code, String reason) {
        showViewStubPage(Constant.QUERY_ERROR);
    }
}
