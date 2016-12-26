package com.raythinks.utime.mvp.presenter;
import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.MainContract;
import com.raythinks.utime.mvp.contract.StaticsContract;
import com.raythinks.utime.mvp.model.StaticsModelImpl;

/**
* Created by 赵海 on 2016/12/18
*/

public class StaticsPresenterImpl extends BasePresenter<StaticsContract.Model,StaticsContract.View>implements StaticsContract.Presenter{

    public StaticsPresenterImpl(Context context, StaticsContract.View view) {
        super(context, view);
    }

    @Override
    public StaticsContract.Model initModel() {
        return new StaticsModelImpl(mContext,this);
    }
}