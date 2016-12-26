package com.raythinks.utime.activity;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.rayker.core.base.BaseActivity;
import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.R;
import com.raythinks.utime.adapter.SelectSoftAdapter;
import com.raythinks.utime.adapter.WhiteListAdapter;
import com.raythinks.utime.mvp.contract.SelectedSoftContract;
import com.raythinks.utime.mvp.presenter.SelectedSoftPresenterImpl;

import qiu.niorgai.StatusBarCompat;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/23 0023<br>.
 * 版本：1.2.0
 */

public class SelectedSoftActivity extends BaseActivity<SelectedSoftPresenterImpl> implements SelectedSoftContract.View {
    private TextView tvTitleBack;
    private TextView tvTitleName;
    private TextView tvTitleRight;
    private TextView tvTitleRight1;
    private ImageView ivTitleRight;
    private RecyclerView rcList;
    private SelectSoftAdapter adapter;

    @Override
    public SelectedSoftPresenterImpl getmPresenter() {
        return new SelectedSoftPresenterImpl(this, this);
    }

    @Override
    public void initView() {
        tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
        tvTitleName = (TextView) findViewById(R.id.tv_title_name);
        tvTitleRight = (TextView) findViewById(R.id.tv_title_right);
        tvTitleRight1 = (TextView) findViewById(R.id.tv_title_right1);
        ivTitleRight = (ImageView) findViewById(R.id.iv_title_right);
        rcList = (RecyclerView) findViewById(R.id.rc_list);
        tvTitleBack.setVisibility(View.VISIBLE);
        tvTitleName.setText(R.string.str_title_whitelist);
        adapter = new SelectSoftAdapter(this);
        rcList.setAdapter(adapter);
        //setLayoutManager
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcList.setLayoutManager(linearLayoutManager);
        rcList.setAdapter(adapter);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_select_soft;
    }
}
