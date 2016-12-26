package com.raythinks.utime.mvp.presenter;
import android.content.Context;

import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.mvp.contract.EditSceneContract;

/**
* Created by 赵海 on 2016/12/25
*/

public class EditScenePresenterImpl  extends BasePresenter<EditSceneContract.Model,EditSceneContract.View> implements EditSceneContract.Presenter{

    public EditScenePresenterImpl(Context context, EditSceneContract.View view) {
        super(context, view);
    }

    @Override
    public EditSceneContract.Model initModel() {
        return null;
    }
}