package com.raythinks.utime.mvp.presenter;

import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.WhiteListContract;

/**
 * Created by 赵海 on 2016/12/21
 */

public class WhiteListPresenterImpl extends BasePresenter<WhiteListContract.Model, WhiteListContract.View> implements WhiteListContract.Presenter {

    public WhiteListPresenterImpl(Context context, WhiteListContract.View view) {
        super(context, view);
    }

    @Override
    public WhiteListContract.Model initModel() {
        return null;
    }
}