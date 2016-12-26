package com.raythinks.utime.mvp.presenter;

import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.RankContract;

/**
 * Created by 赵海 on 2016/12/18
 */

public class RankPresenterImpl extends BasePresenter<RankContract.Model, RankContract.View> implements RankContract.Presenter {

    public RankPresenterImpl(Context context, RankContract.View view) {
        super(context, view);
    }

    @Override
    public RankContract.Model initModel() {
        return null;
    }
}