package com.sicnu.personal.knowledgesharingapp.collection.model.datasource;

import android.content.Context;

import com.sicnu.personal.knowledgesharingapp.collection.model.CollectBmobCallBack;
import com.sicnu.personal.knowledgesharingapp.collection.model.databean.CollectDataBean;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class CollectResponse {
    private CollectDataBean dataBean;
    private Context mContext;


    public CollectResponse(Context context) {
        this.mContext = context;

    }

    public void addCollectDataToBmob(final CollectDataBean dataBean, final CollectBmobCallBack.CollectCallBack collectCallBack) {
        YLog.d("BmobCollect :Find addCollectDataToBmob :");
        final BmobQuery<CollectDataBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userName", dataBean.getUserName());
        query.addWhereEqualTo("articleTitle", dataBean.getArticleTitle());
        query.addWhereEqualTo("articleType", dataBean.getArticleType());
        query.addWhereEqualTo("articleLink", dataBean.getArticleLink());
        query.setLimit(10);
        query.findObjects(mContext, new FindListener<CollectDataBean>() {
            @Override
            public void onSuccess(List<CollectDataBean> list) {
                YLog.d("BmobCollect :Find onSuccess :"+list.size());
                if (list != null && list.size() != 0) {
                    collectCallBack.theCollectDataIsExited();
                } else {
                    dataBean.save(mContext, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            YLog.d("BmobCollect :save onSuccess :");
                            collectCallBack.collectSuccessful();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            YLog.d("BmobCollect :save onFailure :"+s);
                            collectCallBack.collectFailed(i, s);
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {
                collectCallBack.queryCollectFailed(i, s);
            }
        });
    }

    public void queryCollectDataFromBmob(String userName, final CollectBmobCallBack.QueryCollectCallBack collectCallBack) {
       YLog.d("Bmob Query");
        BmobQuery<CollectDataBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userName", userName);
        query.setLimit(100);
        query.findObjects(mContext, new FindListener<CollectDataBean>() {
            @Override
            public void onSuccess(List<CollectDataBean> list) {
                YLog.d("Bmob Query onSuccess");
                if (list != null && list.size()>0) {
                    collectCallBack.queryCollectionSuccessful(list);
                } else {
                    YLog.d("Bmob Query onSuccess22222");
                    collectCallBack.queryCollectFailed(1001, "No Datas");
                }
            }

            @Override
            public void onError(int i, String s) {
                YLog.d("Bmob Query onError");
                collectCallBack.queryCollectFailed(i, s);
            }
        });
    }
    public void deleteCollectDataForBmob(CollectDataBean dataBean, final CollectBmobCallBack.DeleteCollectCallBack collectCallBack){
        dataBean.delete(mContext, new DeleteListener() {
            @Override
            public void onSuccess() {
                collectCallBack.deleteCollectSuccessfil();
            }

            @Override
            public void onFailure(int i, String s) {
                collectCallBack.deleteCollectFailed(i,s);
            }
        });
    }
}
