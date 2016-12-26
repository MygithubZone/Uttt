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
import com.raythinks.utime.adapter.WhiteListAdapter;
import com.raythinks.utime.mvp.contract.MainContract;
import com.raythinks.utime.mvp.contract.WhiteListContract;
import com.raythinks.utime.mvp.presenter.WhiteListPresenterImpl;

import qiu.niorgai.StatusBarCompat;

/**
 * 功能：白名单<br>
 * 作者：赵海<br>
 * 时间：2016/12/21.
 */

public class WhiteListActivity extends BaseActivity<WhiteListPresenterImpl> implements WhiteListContract.View {
    private TextView tvTitleBack;
    private TextView tvTitleName;
    private TextView tvTitleRight;
    private TextView tvTitleRight1;
    private ImageView ivTitleRight;
    private TwinklingRefreshLayout ttrflRefresh;
    private RecyclerView rcList;
    private WhiteListAdapter adapter;

    @Override
    public WhiteListPresenterImpl getmPresenter() {
        return new WhiteListPresenterImpl(this, this);
    }

    @Override
    public void initView() {
        tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
        tvTitleName = (TextView) findViewById(R.id.tv_title_name);
        tvTitleRight = (TextView) findViewById(R.id.tv_title_right);
        tvTitleRight1 = (TextView) findViewById(R.id.tv_title_right1);
        ivTitleRight = (ImageView) findViewById(R.id.iv_title_right);
        ttrflRefresh = (TwinklingRefreshLayout) findViewById(R.id.ttrfl_refresh);
        rcList = (RecyclerView) findViewById(R.id.rc_list);
        tvTitleBack.setVisibility(View.VISIBLE);
        tvTitleName.setText(R.string.str_title_whitelist);
        adapter = new WhiteListAdapter(this);
        rcList.setAdapter(adapter);
        //setLayoutManager
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcList.setLayoutManager(linearLayoutManager);
        rcList.setAdapter(adapter);
        ttrflRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_whitelist;
    }
}
