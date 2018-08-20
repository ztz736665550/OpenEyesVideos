package com.example.ztz.openeyesvideos.retrofit.interceptor;

import android.content.Context;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络拦截器
 * Created by ztz on 2018/7/13.
 */

public class RequestInterceptor implements Interceptor {

    private Context context;
    //token
    //private String token = "test-access-token";

    public RequestInterceptor(Context context) {
        this.context=context;
    }

    public void setContext(Context context){
        this.context=context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //String t = SpUtil.getToken(context,"-1");//token
        //if (!"-1".equals(t)) {
        //    token = t;
        //}
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type","application/json")
                //.addHeader("X-Access-Token",token)
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}
