package com.raythinks.utime.mvp.presenter;

import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.MainContract;
import com.raythinks.utime.mvp.model.MainModelImpl;

/**
 * Created by Administrator on 2016/12/16
 */

public class MainPresenterImpl extends BasePresenter<MainContract.Model, MainContract.View> implements MainContract.Presenter {

    public MainPresenterImpl(Context context, MainContract.View absView) {
        super(context, absView);
    }

    @Override
    public MainContract.Model initModel() {
        return new MainModelImpl(mContext, this);
    }

    @Override
    public void login(int Type) {
        
    }
}