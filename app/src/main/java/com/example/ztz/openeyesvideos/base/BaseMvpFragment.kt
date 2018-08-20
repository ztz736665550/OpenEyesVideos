package com.example.ztz.openeyesvideos.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.multiple_status_view.MultipleStatusView
import com.example.ztz.openeyesvideos.utils.NetUtil
import org.jetbrains.anko.toast

/**
 * Created by Administrator on 2017/8/15.
 * fragment的基类（对应mvp中的fragment）
 */

abstract class BaseMvpFragment<V, P : BaseContract.BasePresenter<V>> : Fragment() {
    private lateinit var parentView: View
    internal open var activity: FragmentActivity? = null
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false

    protected var mContext: Context? = null
    //网络逻辑处理对象
    protected var mPresenter: P? = null

    val supportActivity: FragmentActivity?
        get() = super.getActivity()

    @get:LayoutRes abstract val layoutResId: Int

    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        parentView = inflater.inflate(layoutResId, container, false)
        activity = supportActivity
        mContext = activity
        return parentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isNet = NetUtil.checkNet(mContext)
        if (!isNet) {

            Toast.makeText(mContext, "请打开手机网络", Toast.LENGTH_SHORT).show()
            mLayoutStatusView?.showNoNetwork()
            return
        }
        //初始化
        mPresenter = initPresenter()
        initDatas()
        if (mPresenter != null) {
            mPresenter!!.attachView(this as V)
        }
        //多种状态切换的view 重试点击事件
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as FragmentActivity
    }

    override fun onDetach() {
        super.onDetach()
        this.activity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mPresenter != null) {
            mPresenter!!.detachView()
        }
    }

    protected abstract fun initPresenter(): P

    abstract fun initDatas()

    abstract fun lazyLoad()

    companion object {
        private val TAG = "BaseMvpFragment"
    }
}
