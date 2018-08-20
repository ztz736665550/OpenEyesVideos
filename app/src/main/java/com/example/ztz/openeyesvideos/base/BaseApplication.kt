package com.example.ztz.openeyesvideos.base

import android.app.Application
import com.example.ztz.openeyesvideos.retrofit.RetrofitFactory


/**
 * Created by ztz on 2018/6/19.
 */
class BaseApplication : Application() {

    companion object {
        private var instance : BaseApplication? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        RetrofitFactory.getRetroFactory(this)

        instance = this
    }
}