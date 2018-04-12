package com.sicnu.personal.knowledgesharingapp.net;






import com.sicnu.personal.knowledgesharingapp.flash.databean.FlashPicDataBean;

import com.sicnu.personal.knowledgesharingapp.gank.model.databean.GankDataBean;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public interface ServerApi {
    @Headers("url_type:gank_knowledge")
    @GET("api/data/{type}/{count}/{page}")
    Observable<GankDataBean> getGankKnowledgeData(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page);

    @Headers("url_type:pretty_picture")
    @GET("{page}")
    Observable<PrettyDataBean> getPrettyPictureData(
            @Path("page") String page
    );
    @Headers("url_type:flash_picture")
    @GET("{info}")
    Observable<FlashPicDataBean> getFlashPicInfo(
            @Path("info") String info
    );
}
