package com.example.ztz.openeyesvideos.contract

import com.example.ztz.openeyesvideos.base.BaseContract
import com.example.ztz.openeyesvideos.bean.HomeBean

/**
 * Created by ztz on 2018/7/27.
 */
interface HomeContract {

    interface View : BaseContract.BaseView {
        //fun getBanner(mBannerList: MutableList<String>)
         fun complete(mBannerList: ArrayList<String>,mBannerTitle: ArrayList<String>, homeBean: HomeBean)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun getDataSuccess(homeBean: HomeBean)
    }
}