package com.example.ztz.openeyesvideos.ui.activity

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.multiple_status_view.MultipleStatusView
import com.example.ztz.openeyesvideos.R
import com.example.ztz.openeyesvideos.base.BaseActivity
import com.example.ztz.openeyesvideos.ui.fragment.DiscoveryFragment
import com.example.ztz.openeyesvideos.ui.fragment.HomeFragment
import com.example.ztz.openeyesvideos.ui.fragment.HotFragment
import com.example.ztz.openeyesvideos.ui.fragment.MineFragment
import com.example.ztz.openeyesvideos.utils.NetUtil
import com.example.ztz.openeyesvideos.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {
    protected var mLayoutStatusView: MultipleStatusView? = null
    private var mHomeFragment : HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mMineFragment: MineFragment? = null
    private var mFragment: Fragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initWindow() {
        StatusBarUtil.setTransparent(this)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is HomeFragment -> mHomeFragment ?: let { mHomeFragment = fragment }
            is DiscoveryFragment -> mDiscoveryFragment ?: let { mDiscoveryFragment = fragment }
            is HotFragment -> mHotFragment ?: let { mHotFragment = fragment }
            is MineFragment -> mMineFragment ?: let { mMineFragment = fragment }
        }
    }

    /**
     * 切换fragment
     */
    private fun switchFragment(position: Int) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.apply {
            mHomeFragment ?: let { HomeFragment().let {
                mHomeFragment = it
                add(R.id.mMainFl,it)
            }}
            mDiscoveryFragment ?: let { DiscoveryFragment().let {
                mDiscoveryFragment = it
                add(R.id.mMainFl,it)
            }}
            mHotFragment ?: let { HotFragment().let {
                mHotFragment = it
                add(R.id.mMainFl,it)
            }}
            mMineFragment ?: let { MineFragment().let {
                mMineFragment = it
                add(R.id.mMainFl,it)
            }}
        }
        hideFragment(beginTransaction)
        when(position){
            0 //每日精选
            -> mHomeFragment?.let {
                beginTransaction.show(it)
            }
            1 //发现
            -> mDiscoveryFragment?.let {
                beginTransaction.show(it)
            }
            2 //热门
            -> mHotFragment?.let {
                beginTransaction.show(it)
            }
            3 //我的
            -> mMineFragment?.let {
                beginTransaction.show(it)
            }
            else ->{

            }
        }
        beginTransaction.commit()
    }

    /**
     * 隐藏所有fragment
     */
    private fun hideFragment(beginTransaction: FragmentTransaction) {
        mHomeFragment?.let { beginTransaction.hide(it)}
        mDiscoveryFragment?.let { beginTransaction.hide(it)}
        mHotFragment?.let { beginTransaction.hide(it)}
        mMineFragment?.let { beginTransaction.hide(it)}
    }

    override fun initDatas() {
        switchFragment(0)
        mBottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setActiveColor("#ffff00") //选中颜色
                .setInActiveColor("#ffffff") //未选中颜色
                .setBarBackgroundColor("#B3000000")//导航栏背景色
                .setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener{

                    override fun onTabReselected(position: Int) {

                    }

                    override fun onTabUnselected(position: Int) {

                    }

                    override fun onTabSelected(position: Int) {
                        switchFragment(position)
                    }
                })
        mBottomNavigationBar
                .addItem(BottomNavigationItem(R.mipmap.ic_home_normal,"每日精选"))
                .addItem(BottomNavigationItem(R.mipmap.ic_discovery_normal,"发现"))
                .addItem(BottomNavigationItem(R.mipmap.ic_hot_normal,"热门"))
                .addItem(BottomNavigationItem(R.mipmap.ic_mine_normal,"我的"))
                .setFirstSelectedPosition(0)
                .initialise()
    }

}
