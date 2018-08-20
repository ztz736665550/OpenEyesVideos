package com.example.ztz.openeyesvideos.presenter

import android.content.Context
import com.example.ztz.openeyesvideos.base.BasePresenter
import com.example.ztz.openeyesvideos.bean.HomeBean
import com.example.ztz.openeyesvideos.contract.HomeContract
import com.example.ztz.openeyesvideos.model.HomeModel
import com.example.ztz.openeyesvideos.ui.fragment.HomeFragment

/**
 * Created by ztz on 2018/7/27.
 */
class HomePresenter : BasePresenter<HomeContract.View>() , HomeContract.Presenter {

    private var mBannerList : ArrayList<String> = arrayListOf()
    private var mBannerTitle : ArrayList<String> = arrayListOf()
    private var mHomeFragment : HomeFragment? = null

    private val mHomeModel by lazy {
        HomeModel()
    }

    fun getData(context: Context?, homeFragment: HomeFragment) {
        this.mHomeFragment = homeFragment
        mHomeModel.getData(this,context)
    }

    override fun getDataSuccess(homeBean: HomeBean) {
        val size = homeBean.issueList[0].itemList.size
        for (i in 1 .. size-1){
            mBannerList.add(homeBean.issueList[0].itemList[i].data.cover.feed)
            mBannerTitle.add(homeBean.issueList[0].itemList[i].data.title)
        }
        mHomeFragment?.complete(mBannerList,mBannerTitle,homeBean)
    }
}