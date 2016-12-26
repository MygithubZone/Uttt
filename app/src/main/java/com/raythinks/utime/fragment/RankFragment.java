package com.raythinks.utime.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.rayker.core.base.BaseFragment;
import com.raythinks.utime.R;
import com.raythinks.utime.adapter.RankAdapter;
import com.raythinks.utime.mvp.contract.RankContract;
import com.raythinks.utime.mvp.presenter.RankPresenterImpl;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/15 0015<br>.
 * 版本：1.2.0
 */

public class RankFragment extends BaseFragment implements RankContract.View {
    private TextView tvRankHot;
    private TextView tvRankTime;
    private TextView tvRankFrequency;
    private TwinklingRefreshLayout ttrflRankRefresh;
    private RecyclerView rcRankList;
    private RankAdapter adapter;

    @Override
    public RankPresenterImpl getmPresenter() {
        return new RankPresenterImpl(mActivity, this);
    }

    @Override
    public void initView() {
        tvRankHot = (TextView) getView().findViewById(R.id.tv_rank_hot);
        tvRankTime = (TextView) getView().findViewById(R.id.tv_rank_time);
        tvRankFrequency = (TextView) getView().findViewById(R.id.tv_rank_frequency);
        ttrflRankRefresh = (TwinklingRefreshLayout) getView().findViewById(R.id.ttrfl_rank_refresh);
        ttrflRankRefresh.setOverScrollRefreshShow(false);
        rcRankList = (RecyclerView) getView().findViewById(R.id.rc_rank_list);
        adapter = new RankAdapter(mActivity);
        //setLayoutManager
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcRankList.setLayoutManager(linearLayoutManager);
        rcRankList.setAdapter(adapter);
        ttrflRankRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
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
        tvRankHot.setSelected(true);

}

    @Override
    public int setLayoutId() {
        return R.layout.frag_rank;
    }
}
