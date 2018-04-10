package com.sicnu.personal.knowledgesharingapp.net;

import android.util.Log;

import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class RetrofitClient {
    private static RetrofitClient client;
    private static Retrofit retrofit;
    public static RetrofitClient getInstance(){
        if(client==null){
            synchronized (RetrofitClient.class){
                if(client==null && retrofit==null){
                    YLog.d("asdasdsada");
                    client = new RetrofitClient();
                }
            }
        }
        return client;
    }
    private RetrofitClient(){
        initRetrofit();
    }
    private class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.d("HttpLogInfo", message);
        }
    }
    private void initRetrofit() {
        if(retrofit==null){
            HttpLoggingInterceptor LoginInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            if(true) {
                LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            }else{
                LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }
            OkHttpClient okhttpClient =  new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    //获取request的创建者builder
                    Request.Builder builder = request.newBuilder();
                    YLog.d("Http pre _ url : "+request.url());
                    //从request中获取headers，通过给定的键url_name
                    List<String> headerValues = request.headers("url_type");
                    HttpUrl oldHttpUrl = request.url();
                    YLog.d("Http 222222 _ url : "+request.url());
                    if (headerValues != null && headerValues.size() > 0) {
                        //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                        builder.removeHeader("url_type");
                        //匹配获得新的BaseUrl
                        String headerValue = headerValues.get(0);
                        YLog.d("HeadValue : "+headerValue);
                        HttpUrl newBaseUrl = null;
                        if ("guide".equals(headerValue)) {
                            YLog.d("guide");
                            newBaseUrl = HttpUrl.parse(Constant.CUSTOM_SERVER_BASE_URL_GUIDE);
                        } else if ("gank_knowledge".equals(headerValue)) {
                            YLog.d("gank_knowledge");
                            newBaseUrl = HttpUrl.parse(Constant.BASE_URL_GANK);
                        } else if("pretty_picture".equals(headerValue)){
                            newBaseUrl = HttpUrl.parse(Constant.PRETTY_PICTUR_BASE_URL);
                        }else if("flash_picture".equals(headerValue)){
                            newBaseUrl = HttpUrl.parse(Constant.PRETTY_PICTUR_BASE_URL);
                        }
                        YLog.d("url : "+newBaseUrl);
                        YLog.d("old_url : "+oldHttpUrl.url());
                        //重建新的HttpUrl，修改需要修改的url部分
                        HttpUrl newFullUrl = oldHttpUrl
                                .newBuilder()
                                .scheme(newBaseUrl.scheme())
                                .host(newBaseUrl.host())
                                .port(newBaseUrl.port())
                                .build();
                        YLog.d("Http newFullUrl : "+newFullUrl.url());
                        //重建这个request，通过builder.url(newFullUrl).build()；
                        //然后返回一个response至此结束修改
                        return chain.proceed(builder.url(newFullUrl).build());
                    }else {
                        return chain.proceed(request);
                    }
                }
            }).connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addNetworkInterceptor(LoginInterceptor)
                    .retryOnConnectionFailure(true)
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(okhttpClient)
                    .baseUrl(Constant.BASE_URL_GANK)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
    }

    public <T> T createReq(Class<T> reqServer){
        return retrofit.create(reqServer);
    }
}
