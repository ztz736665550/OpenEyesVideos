package com.example.ztz.openeyesvideos.retrofit;

import android.content.Context;

import com.example.ztz.openeyesvideos.common.UrlConfig;
import com.example.ztz.openeyesvideos.retrofit.interceptor.RequestInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ztz on 2018/7/13.
 */

public class RetrofitInit {
    private ApiService service;
    private RequestInterceptor interceptor;

    RetrofitInit(Context context){
        //请求拦截器
        interceptor = new RequestInterceptor(context);
        OkHttpClient.Builder okhttpclient = new OkHttpClient.Builder();
        okhttpclient
                .addInterceptor(interceptor)
                //.addInterceptor(new MoreBaseUrlInterceptor()) //解决多个baseUrl问题
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okhttpclient.build())
                .build();
        service = retrofit.create(ApiService.class);
    }

    /**
     * 传递当前界面的context给请求拦截器
     * @param context
     */
    public void setContext(Context context){
        if(interceptor!=null){
            interceptor.setContext(context);
        }
    }

    public ApiService getApi() {
        return service;
    }

}
