package com.raythinks.utime.activity;

import com.rayker.core.base.BaseActivity;
import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.R;
import com.raythinks.utime.mvp.contract.LoginContract;
import com.raythinks.utime.mvp.presenter.LoginPresenterImpl;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/21 0021<br>.
 * 版本：1.2.0
 */

public class LoginActivity extends BaseActivity<LoginPresenterImpl> implements LoginContract.View {
    @Override
    public LoginPresenterImpl getmPresenter() {
        return new LoginPresenterImpl(this, this);
    }

    @Override
    public void initView() {

    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
    }
}
