package com.rayker.core.base;

import android.content.Context;

import com.rayker.core.net.HttpRequst;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/16 0016<br>.
 * 版本：1.2.0
 */
public abstract class BasePresenter<M, V>  {
    public M model;
    public V view;
    public Context mContext;

    public BasePresenter(Context context, V view) {
        this.mContext = context;
        this.view = view;
        model = initModel();
    }

    public abstract M initModel();

    public void showDialog(String dialogToast) {

    }

    public void hide() {

    }

    public void onStart() {

    }

    public void onDestory() {
        HttpRequst.cancelCall(mContext.getClass().getName());
    }
}
