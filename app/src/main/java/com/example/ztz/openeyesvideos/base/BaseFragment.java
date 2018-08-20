package com.example.ztz.openeyesvideos.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/8/15.
 * fragment的基类（对应mvp中的fragment）
 */

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseMvpFragment";
    protected View parentView;
    protected FragmentActivity activity;
    protected Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        mContext = activity;
        return parentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configViews();
        initDatas();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    protected View getParentView() {
        return parentView;
    }

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    public Context getApplicationContext() {
        return this.activity == null ? (getActivity() == null ? null : getActivity()
                .getApplicationContext()) : this.activity.getApplicationContext();
    }

    public abstract int getLayoutResId();
    public abstract void configViews();
    public abstract void initDatas();
}
