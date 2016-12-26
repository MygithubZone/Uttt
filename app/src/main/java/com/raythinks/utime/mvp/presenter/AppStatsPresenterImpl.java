package com.raythinks.utime.mvp.presenter;
import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.AppStatsContract;

/**
* Created by 赵海 on 2016/12/26
*/

public class AppStatsPresenterImpl extends BasePresenter<AppStatsContract.Model,AppStatsContract.View> implements AppStatsContract.Presenter{

    public AppStatsPresenterImpl(Context context, AppStatsContract.View view) {
        super(context, view);
    }

    @Override
    public AppStatsContract.Model initModel() {
        return null;
    }
}