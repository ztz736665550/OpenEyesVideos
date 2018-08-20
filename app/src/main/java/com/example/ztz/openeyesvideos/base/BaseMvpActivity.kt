package com.example.ztz.openeyesvideos.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.multiple_status_view.MultipleStatusView
import com.example.ztz.openeyesvideos.utils.NetUtil
import org.jetbrains.anko.toast

/**
 * Created by Administrator on 2017/8/15.
 * Activity基类，可以设置统一的dialog、权限设置等(对应mvp中的activity)
 */

abstract class BaseMvpActivity<V, P : BaseContract.BasePresenter<V>> : AppCompatActivity() {

    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null
    protected lateinit var mContext: Context
    //网络逻辑处理对象
    protected var mPresenter: P? = null
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        //设置软键盘、沉浸式
        initWindow()
        //设置布局文件
        setContentView(layoutId)
        val isNet = NetUtil.checkNet(mContext)
        if (!isNet) {
            toast("请打开手机网络")
            return
        }
        //初始化
        mPresenter = initPresenter()
        initDatas()
        configViews()
        initListener()
        if (mPresenter != null) {
            mPresenter!!.attachView(this as V)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter!!.detachView()
        }
    }

    protected abstract fun initPresenter(): P
    abstract fun initWindow()
    /**
     * 对各种控件进行设置、适配、填充数据
     */
    abstract fun configViews()

    /**
     * 初始化控件数据
     */
    abstract fun initDatas()

    private fun initListener() {
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }
    /**
     * 重新开始请求
     */
    abstract fun start()
}
