package com.raythinks.utime.mvp.model;

import android.content.Context;

import com.rayker.core.base.BaseModel;
import com.raythinks.utime.mvp.contract.StaticsContract;
import com.raythinks.utime.mvp.presenter.StaticsPresenterImpl;

/**
 * Created by 赵海 on 2016/12/18
 */

public class StaticsModelImpl extends BaseModel<StaticsContract.Presenter> implements StaticsContract.Model {

    public StaticsModelImpl(Context context, StaticsContract.Presenter mPresenter) {
        super(context, mPresenter);
    }
}