package com.example.ztz.openeyesvideos.base;

/**
 * Created by Administrator on 2017/8/15.
 *
 **/
public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
    private static final String TAG = "BasePresenter";
    private T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}