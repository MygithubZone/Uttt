package com.raythinks.utime.mvp.model;
import android.content.Context;

import com.rayker.core.base.BaseModel;
import com.raythinks.utime.mvp.contract.MainContract;

/**
* Created by Administrator on 2016/12/16
*/

public class MainModelImpl extends BaseModel<MainContract.Presenter> implements MainContract.Model{

    public MainModelImpl(Context context, MainContract.Presenter mPresenter) {
        super(context, mPresenter);
    }
}