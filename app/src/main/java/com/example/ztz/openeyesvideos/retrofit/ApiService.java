package com.example.ztz.openeyesvideos.retrofit;

import com.example.ztz.openeyesvideos.common.UrlConfig;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by ztz on 2018/7/13.
 * API
 */
public interface ApiService {
    /**
     * 首页
     * @param map
     * @return
     */
    @POST(UrlConfig.HOME_URL)
    Call<ResponseBody> getHome(@QueryMap Map<String, String> map);

}
