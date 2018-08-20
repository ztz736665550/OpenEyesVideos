package com.example.ztz.openeyesvideos.ui.activity

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import com.example.ztz.openeyesvideos.R
import com.example.ztz.openeyesvideos.base.BaseActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivity

/**
 * Created by ztz on 2018/6/20.
 *
 * 欢迎页Activity
 */
class WelComeActivity : BaseActivity(), View.OnClickListener {

    //kotlin获取抽象类的实例 mCountDownTimer
    private var mCountDownTimer = object : CountDownTimer(4000,1000){
        override fun onFinish() {
            mTimerTv.text = "0秒后跳转"
            startActivity<MainActivity>()
            finish()
        }

        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            val l = millisUntilFinished / 1000;
            mTimerTv.text = "${l}秒后跳转"
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initWindow() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun initDatas() {
        mTimerTv.setOnClickListener(this)
        mCountDownTimer.start()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.mTimerTv -> startActivity<MainActivity>()
        }
        finish()
        mCountDownTimer.cancel()
    }
}