package com.example.ztz.openeyesvideos.ui.fragment

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.ztz.openeyesvideos.R
import com.example.ztz.openeyesvideos.R.id.*
import com.example.ztz.openeyesvideos.base.BaseMvpFragment
import com.example.ztz.openeyesvideos.bean.HomeBean
import com.example.ztz.openeyesvideos.contract.HomeContract
import com.example.ztz.openeyesvideos.presenter.HomePresenter
import com.example.ztz.openeyesvideos.ui.activity.SearchActivity
import com.example.ztz.openeyesvideos.ui.activity.VideoPlayActivity
import com.example.ztz.openeyesvideos.ui.adapter.HomeRecyclerAdapter
import com.example.ztz.openeyesvideos.utils.AnimationUtil
import com.example.ztz.openeyesvideos.utils.ToastUtil
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by ztz on 2018/7/26.
 *
 * 首页
 */

class HomeFragment() : BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(),HomeContract.View{

    private var mDistanceY = 0;
    private var mHomeRecyclerAdapter : HomeRecyclerAdapter?= null
    private val mHomePresenter by lazy {HomePresenter()}
    private val mLinearLayoutManager by lazy {LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false) }

    override val layoutResId: Int = R.layout.fragment_home

    override fun initPresenter(): HomeContract.Presenter {
        mHomePresenter.attachView(this)
        return mHomePresenter
    }

    override fun initDatas() {
        mLayoutStatusView = mMultipleStatusView
        mLayoutStatusView?.showLoading()
        mHomePresenter.getData(context,this)
        //刷新
        mRefreshLayoutHm.setOnRefreshListener(object : OnRefreshListener{
            override fun onRefresh(refreshLayout: RefreshLayout) {
                mRefreshLayoutHm.finishRefresh(2000)
            }
        })
        //加载
        mRefreshLayoutHm.setOnLoadMoreListener(object : OnLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mRefreshLayoutHm.finishLoadMore(2000)
            }
        })

        //搜索图标的显示隐藏
        mRecyclerviewHm.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mDistanceY += dy//滑动的距离
                val toolbarHeight = mTitleHome.bottom//toolbar高度
                if (mDistanceY <= toolbarHeight) {
                    AnimationUtil.with().moveToViewTop(mTitleHome,200)
                } else {
                    AnimationUtil.with().topMoveToViewLocation(mTitleHome,500)
                }
            }
        })
        //搜索
        mTitleHome.setOnClickListener {
            val intent  = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mHomePresenter.getData(context,this)
    }

    override fun showError(e: Throwable?) {
        mLayoutStatusView?.showError()
    }

    override fun complete() {
        mLayoutStatusView?.showContent()
    }

    override fun complete(mBannerList: ArrayList<String>,mBannerTitle: ArrayList<String>, homeBean: HomeBean) {
        mLayoutStatusView?.showContent()
        mRecyclerviewHm.layoutManager = mLinearLayoutManager
        mHomeRecyclerAdapter = HomeRecyclerAdapter(this.context!!, mBannerList,mBannerTitle, homeBean.issueList[0].itemList as ArrayList<HomeBean.Issue.Item>)
        mRecyclerviewHm.adapter = mHomeRecyclerAdapter
        val nextPageUrl = homeBean.nextPageUrl;
        mRefreshLayoutHm.finishRefresh();
        mRefreshLayoutHm.finishLoadMore();
        //条目点击
        mHomeRecyclerAdapter?.setOnMyItemClickLitener { view, position ->
            val intent = Intent(activity,VideoPlayActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHomePresenter.detachView()
    }
}