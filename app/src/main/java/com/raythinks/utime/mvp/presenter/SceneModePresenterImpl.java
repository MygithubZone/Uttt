package com.raythinks.utime.mvp.presenter;

import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.SceneModeContract;

/**
 * Created by 赵海 on 2016/12/18
 */

public class SceneModePresenterImpl extends BasePresenter<SceneModeContract.Model, SceneModeContract.View> implements SceneModeContract.Presenter {

    public SceneModePresenterImpl(Context context, SceneModeContract.View view) {
        super(context, view);
    }

    @Override
    public SceneModeContract.Model initModel() {
        return null;
    }
}