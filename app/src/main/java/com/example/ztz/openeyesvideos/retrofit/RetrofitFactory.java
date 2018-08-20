package com.example.ztz.openeyesvideos.retrofit;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ztz on 2018/7/13.
 */

public class RetrofitFactory {
    private final String TAG = "Retrofit NET";
    private String mJson;

    public static final String RESULT_CODE = "0";
    public static final String RESULT_MESSAGE = "message";
    public static final String RESULT_CODE_OK = "1";

    private static final String SUCCESS = "0";
    public static final String FAIL = "1";
    public static final String ERROR = "2";
    public static final String ERROR_4 = "3";

    private static ApiService studyAPI = null;
    private static final Object monitor = new Object();
    private static Call<ResponseBody> mCall;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static RetrofitInit retroInit;
    private static RetrofitInit authInit;
    @SuppressLint("StaticFieldLeak")
    private static RetrofitFactory retrofitFactory;


    public static ApiService getRetroFactory(Context context) {
        synchronized (monitor) {
            if(retroInit==null){
                retroInit = new RetrofitInit(context);
            }else{
                retroInit.setContext(context);
            }
            if (studyAPI == null) {
                studyAPI = retroInit.getApi();
            }
            return studyAPI;
        }
    }

    private RetrofitFactory(Context context, Call call) {

    }

    public static RetrofitFactory getRetrofit(Context context, Call call){

        synchronized (RetrofitFactory.class){
            if (retrofitFactory == null){
                retrofitFactory = new RetrofitFactory(context, call);
            }

            retrofitFactory.mContext = context;
            retrofitFactory.mCall = call;
        }
        return retrofitFactory;
    }

    public Call<ResponseBody> handleResponse(final ResponseListener listener) {
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "onResponse: "+response.code());
                if (response.code() == 401) { //token过期
                    //mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
                if (response.code() == 500) { //服务器繁忙
                    listener.onFail("当前服务器繁忙，稍后试试");
                }
                if (response.isSuccessful() && response.errorBody() == null) {

                    try {
                        mJson = response.body().string();
                        JSONObject jsonObject = new JSONObject(mJson);
                        listener.onSuccess(mJson);
                        String code = jsonObject.getString("code");
                        Log.i(TAG, "onResponse: "+code);
                        switch (code) {
                            case SUCCESS:
                                break;
                            default:
                                listener.onSuccess(mJson);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFail(t.getMessage());
            }
        });

        return mCall;
    }

    public interface ResponseListener<Object> {
        void onSuccess(Object response);
        void onFail(Object msg);
    }

    /**
     * 取消请求
     */
    public void cancleRequest(){
        if(mCall!=null && mCall.isExecuted()){
            mCall.cancel();
        }
    }


    /**
     * 上传文件需要的方法
     */

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    /**
     * 创建文件描述
     * @param descriptionString
     * @return
     */
    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    /**
     * 创建上传的文件类型
     * @param partName
     * @param
     * @return
     */
    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, File file) {

        // 为file建立RequestBody实例
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
