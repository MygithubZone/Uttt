package com.raythinks.utime.mvp.presenter;

import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.AppStatsFragsContract;

/**
 * Created by 赵海 on 2016/12/26
 */

public class AppStatsFragsPresenterImpl extends BasePresenter<AppStatsFragsContract.Model, AppStatsFragsContract.View> implements AppStatsFragsContract.Presenter {


    public AppStatsFragsPresenterImpl(Context context, AppStatsFragsContract.View view) {
        super(context, view);
    }

    @Override
    public AppStatsFragsContract.Model initModel() {
        return null;
    }
}