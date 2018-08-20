package com.example.ztz.openeyesvideos.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.ztz.openeyesvideos.utils.NetUtil;

/**
 * Created by Administrator on 2017/8/15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initWindow();
        setContentView(getLayoutId());
        initDatas();
    }

    public abstract int getLayoutId();
    public abstract void initWindow();
    public abstract void initDatas();
}
