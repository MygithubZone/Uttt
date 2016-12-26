package com.raythinks.utime.mvp.presenter;

import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.LoginContract;

/**
 * Created by Administrator on 2016/12/21
 */

public class LoginPresenterImpl extends BasePresenter<LoginContract.Model, LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenterImpl(Context context, LoginContract.View view) {
        super(context, view);
    }

    @Override
    public LoginContract.Model initModel() {
        return null;
    }
}