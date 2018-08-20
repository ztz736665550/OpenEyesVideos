package com.example.ztz.openeyesvideos.model

import android.content.Context
import com.example.ztz.openeyesvideos.bean.HomeBean
import com.example.ztz.openeyesvideos.presenter.HomePresenter
import com.example.ztz.openeyesvideos.retrofit.RetrofitFactory
import com.google.gson.Gson

/**
 * Created by ztz on 2018/7/27.
 */
class HomeModel {

    fun getData(homePresenter: HomePresenter, context: Context?) {
        val map = mutableMapOf<String,String>()
        val call = RetrofitFactory.getRetroFactory(context).getHome(map)
        RetrofitFactory.getRetrofit(context,call).handleResponse(object : RetrofitFactory.ResponseListener<String>{
            override fun onSuccess(response: String) {
                val json = Gson().fromJson(response, HomeBean::class.java)
                homePresenter.getDataSuccess(json)
            }

            override fun onFail(msg: String) {

            }
        })
    }
}