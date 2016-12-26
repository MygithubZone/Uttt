package com.raythinks.utime.mvp.presenter;

import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.NewSceneModeContract;

/**
 * Created by 赵海 on 2016/12/22
 */

public class NewSceneModePresenterImpl extends BasePresenter<NewSceneModeContract.Model, NewSceneModeContract.View> implements NewSceneModeContract.Presenter {

    public NewSceneModePresenterImpl(Context context, NewSceneModeContract.View view) {
        super(context, view);
    }

    @Override
    public NewSceneModeContract.Model initModel() {
        return null;
    }
}