package com.rayker.core.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 功能：RaykerFragment基础类<br>
 * 作者：RaykerTeam
 * 时间： 2016/11/3 0003<br>.
 * 版本：1.0.0
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    private static String TAG = null;
    protected BaseActivity mActivity;
    public P mPresenter;

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayoutId(), null, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mPresenter = getmPresenter();
        mPresenter.onStart();
        TAG = getClass().getName();
    }

    public abstract P getmPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
        }
    }

    public abstract void initView();

    /**
     * setContentView(id layout)
     *
     * @return
     */
    public abstract int setLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
    }
}
