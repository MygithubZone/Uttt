package com.raythinks.utime.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.rayker.core.base.BaseFragment;
import com.raythinks.utime.R;
import com.raythinks.utime.adapter.AppStatsDataAdapter;
import com.raythinks.utime.adapter.TrafficStatsAdapter;
import com.raythinks.utime.adapter.TrafficStatsDataAdapter;
import com.raythinks.utime.configs.ExtraConfigs;
import com.raythinks.utime.mirror.db.DBHelper;
import com.raythinks.utime.mirror.db.DayDBManager;
import com.raythinks.utime.mirror.model.AppUseStaticsModel;
import com.raythinks.utime.mirror.utils.CommonUtils;
import com.raythinks.utime.mvp.contract.AppStatsFragsContract;
import com.raythinks.utime.mvp.contract.TrafficStatsContract;
import com.raythinks.utime.mvp.contract.TrafficStatsFragsContract;
import com.raythinks.utime.mvp.presenter.AppStatsFragsPresenterImpl;
import com.raythinks.utime.mvp.presenter.TrafficStatsFragsPresenterImpl;

import java.util.List;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间：2016/12/26.
 */

public class TrafficStatsFragment extends BaseFragment<TrafficStatsFragsPresenterImpl> implements TrafficStatsFragsContract.View {
    private TwinklingRefreshLayout ttrflRefresh;
    private ListView llAppstats;
    TrafficStatsDataAdapter adapter;
    private Animation animation;
    private LayoutAnimationController controller;
    int timeType = 0;

    @Override
    public TrafficStatsFragsPresenterImpl getmPresenter() {
        return new TrafficStatsFragsPresenterImpl(mActivity, this);
    }

    @Override
    public void initView() {
        timeType = getArguments().getInt(ExtraConfigs.EXTRA_APPSTATS_TIMETYPE, 0);
        ttrflRefresh = (TwinklingRefreshLayout) getView().findViewById(R.id.ttrfl_refresh);
        llAppstats = (ListView) getView().findViewById(R.id.ll_appstats);
        adapter = new TrafficStatsDataAdapter(mActivity);
        llAppstats.setAdapter(adapter);
        if (timeType == 0) {
            updateList(DayDBManager.getInstance(getActivity()).findTrafficAll(1, CommonUtils.getNowTime()));
        } else if (timeType == 1) {
            updateList(DayDBManager.getInstance(getActivity()).findTrafficAll(2, CommonUtils.getCurrentWeekMonday()));
        } else {
            updateList(DayDBManager.getInstance(getActivity()).findTrafficAll(3, CommonUtils.getFirstDayOfMonth()));
        }
    }

    public void updateList(List<AppUseStaticsModel> list) {
        animation = new AlphaAnimation(0, 1); // AlphaAnimation 控制渐变透明的动画效果
        animation.setDuration(250);
        // animation = new ScaleAnimation(5,0,2,0); //RotateAnimation
        // 控制画面角度变化的动画效果
        // animation.setDuration(300);
        // animation = new TranslateAnimation(-250f, 0f, 0f, 0f);
        // animation.setDuration(250);
        // 1f为延时
        controller = new LayoutAnimationController(animation, 1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        llAppstats.setLayoutAnimation(controller);
        long total = 0;
        for (int i = 0; i < list.size(); i++) {
            {
                total = total + list.get(i).getWifiRx()+list.get(i).getWifiTx();
            }
            adapter.setData(list, total);
            llAppstats.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                }
            });
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.frag_appstats;
    }

    public static TrafficStatsFragment newInstance(int position) {
        TrafficStatsFragment fragment = new TrafficStatsFragment();
        Bundle bundleParams = new Bundle();
        bundleParams.putInt(ExtraConfigs.EXTRA_APPSTATS_TIMETYPE, position);
        fragment.setArguments(bundleParams);
        return fragment;
    }
}
