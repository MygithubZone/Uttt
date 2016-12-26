package com.rayker.core.base;

import android.content.Context;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/16 0016<br>.
 * 版本：1.2.0
 */

public abstract class BaseModel<P>{
    public P mPresenter;
    Context context;
    public BaseModel(Context context, P mPresenter) {
        this.mPresenter = mPresenter;
        this.context=context;
    }
}
