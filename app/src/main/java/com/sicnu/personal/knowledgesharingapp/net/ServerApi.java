package com.sicnu.personal.knowledgesharingapp.net;






import com.sicnu.personal.knowledgesharingapp.home.model.databean.GankKnowledgeDataBean;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public interface ServerApi {
    @Headers("url_type:gank_knowledge")
    @GET("{type}/{count}/{page}")
    Observable<GankKnowledgeDataBean> getGankKnowledgeData(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page);
}
