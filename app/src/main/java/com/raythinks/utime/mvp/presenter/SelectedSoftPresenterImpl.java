package com.raythinks.utime.mvp.presenter;
import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.SelectedSoftContract;

/**
* Created by Administrator on 2016/12/23
*/

public class SelectedSoftPresenterImpl extends BasePresenter<SelectedSoftContract.Model,SelectedSoftContract.View> implements SelectedSoftContract.Presenter{

    public SelectedSoftPresenterImpl(Context context, SelectedSoftContract.View view) {
        super(context, view);
    }

    @Override
    public SelectedSoftContract.Model initModel() {
        return null;
    }
}