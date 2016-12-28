package com.raythinks.utime.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.TrafficStats;
import android.support.design.internal.ForegroundLinearLayout;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.rayker.core.base.BaseFragment;
import com.raythinks.utime.R;
import com.raythinks.utime.activity.AppStatsActivity;
import com.raythinks.utime.callable.IMeterialClickCallable;
import com.raythinks.utime.mirror.utils.TrafficUtils;
import com.raythinks.utime.mvp.contract.StaticsContract;
import com.raythinks.utime.mvp.presenter.StaticsPresenterImpl;
import com.raythinks.utime.utils.CommomUtils;
import com.raythinks.utime.widget.FourThreePieChart;

import java.util.ArrayList;

/**
 * 功能：统计界面<br>
 * 作者：赵海<br>
 * 时间： 2016/12/15 0015<br>.
 * 版本：1.2.0
 */

public class StaticsFragment extends BaseFragment implements StaticsContract.View, OnChartValueSelectedListener, View.OnClickListener, IMeterialClickCallable {
    FourThreePieChart mPc;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    private TextView tvTitleBack;
    private TextView tvTitleName;
    private TextView tvTitleRight;
    private TextView tvTitleRight1;
    private ImageView ivTitleRight;
    private PieChart pcTime;
    private ForegroundLinearLayout fllStaticsCall;
    private ImageView ivStatsCall;
    private TextView tvStatsTypeCall;
    private TextView tvStatsTypeCallDes1;
    private TextView tvStatsTypeCallDes2;
    private ForegroundLinearLayout fllStaticsTraffic;
    private ImageView ivStatsMsg;
    private TextView tvStatsTypeTraffic;
    private TextView tvStatsTypeTrafficDes1;
    private TextView tvStatsTypeTrafficDes2;
    private ForegroundLinearLayout fllStaticsMsg;
    private ImageView ivStatsApp;
    private TextView tvStatsTypeMsg;
    private TextView tvStatsTypeMsgDes1;
    private TextView tvStatsTypeMsgDes2;
    private ForegroundLinearLayout fllStaticsApp;
    private ImageView ivStatsTraffic;
    private TextView tvStatsTypeApp;
    private TextView tvStatsTypeAppDes1;
    private TextView tvStatsTypeAppDes2;
    protected String[] mParties = new String[]{
            "Night Rest", "Games", "Work", "Study"
    };
    protected int[] mColor = new int[]{
            R.color.c_4CC2DC, R.color.c_4CB24C, R.color.c_FCAA04, R.color.c_FCD64C
    };

    @Override
    public StaticsPresenterImpl getmPresenter() {
        return new StaticsPresenterImpl(getActivity(), this);
    }

    @Override
    public void initView() {
        tvTitleBack = (TextView) getView().findViewById(R.id.tv_title_back);
        tvTitleName = (TextView) getView().findViewById(R.id.tv_title_name);
        tvTitleRight = (TextView) getView().findViewById(R.id.tv_title_right);
        tvTitleRight1 = (TextView) getView().findViewById(R.id.tv_title_right1);
        ivTitleRight = (ImageView) getView().findViewById(R.id.iv_title_right);
        pcTime = (PieChart) getView().findViewById(R.id.pc_time);
        fllStaticsCall = (ForegroundLinearLayout) getView().findViewById(R.id.fll_statics_call);
        ivStatsCall = (ImageView) getView().findViewById(R.id.iv_stats_call);
        tvStatsTypeCall = (TextView) getView().findViewById(R.id.tv_stats_type_call);
        tvStatsTypeCallDes1 = (TextView) getView().findViewById(R.id.tv_stats_type_call_des1);
        tvStatsTypeCallDes2 = (TextView) getView().findViewById(R.id.tv_stats_type_call_des2);
        fllStaticsTraffic = (ForegroundLinearLayout) getView().findViewById(R.id.fll_statics_traffic);
        ivStatsMsg = (ImageView) getView().findViewById(R.id.iv_stats_msg);
        tvStatsTypeTraffic = (TextView) getView().findViewById(R.id.tv_stats_type_traffic);
        tvStatsTypeTrafficDes1 = (TextView) getView().findViewById(R.id.tv_stats_type_traffic_des1);
        tvStatsTypeTrafficDes2 = (TextView) getView().findViewById(R.id.tv_stats_type_traffic_des2);
        fllStaticsMsg = (ForegroundLinearLayout) getView().findViewById(R.id.fll_statics_msg);
        ivStatsApp = (ImageView) getView().findViewById(R.id.iv_stats_app);
        tvStatsTypeMsg = (TextView) getView().findViewById(R.id.tv_stats_type_msg);
        tvStatsTypeMsgDes1 = (TextView) getView().findViewById(R.id.tv_stats_type_msg_des1);
        tvStatsTypeMsgDes2 = (TextView) getView().findViewById(R.id.tv_stats_type_msg_des2);
        fllStaticsApp = (ForegroundLinearLayout) getView().findViewById(R.id.fll_statics_app);
        ivStatsTraffic = (ImageView) getView().findViewById(R.id.iv_stats_traffic);
        tvStatsTypeApp = (TextView) getView().findViewById(R.id.tv_stats_type_app);
        tvStatsTypeAppDes1 = (TextView) getView().findViewById(R.id.tv_stats_type_app_des1);
        tvStatsTypeAppDes2 = (TextView) getView().findViewById(R.id.tv_stats_type_app_des2);

        fllStaticsCall = (ForegroundLinearLayout) getView().findViewById(R.id.fll_statics_call);
        fllStaticsCall.setOnClickListener(this);
        tvTitleName.setText(R.string.tb_stats);
        CommomUtils.makeMeterial(fllStaticsApp, this);
        initChart();

        tvStatsTypeTrafficDes1.setText(getResources().getString(R.string.str_statics_send) + TrafficUtils.formatMB(TrafficStats.getTotalTxBytes()));
        tvStatsTypeTrafficDes2.setText(getResources().getString(R.string.str_statics_recive) + TrafficUtils.formatMB(TrafficStats.getTotalRxBytes()));
    }

    @Override
    public int setLayoutId() {
        return R.layout.frag_statics;
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Night Rest\n23:00-7:00");
        s.setSpan(new RelativeSizeSpan(1.4f), 0, 10, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 10, s.length() - 11, 0);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c_666666)), 10, s.length() - 11, 0);
        s.setSpan(new RelativeSizeSpan(.6f), 10, s.length() - 10, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 10, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), s.length() - 10, s.length(), 0);
        return s;
    }

    void initChart() {
        mPc = (FourThreePieChart) getView().findViewById(R.id.pc_time);
        mPc.setUsePercentValues(true);
        mPc.setExtraOffsets(5, 10, 5, 5);
        mPc.setDragDecelerationFrictionCoef(0.95f);
        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        mPc.setCenterTextTypeface(mTfLight);
        mPc.setCenterTextRadiusPercent(CommomUtils.dipToPx(getActivity(), 180));
        mPc.setDrawHoleEnabled(true);
//        mPc.setTransparentCircleColor(getResources().getColor(
//                R.color.color_black_trans_no1));
        mPc.setTransparentCircleAlpha(110);
        mPc.setHoleRadius(56f);
        mPc.setTransparentCircleRadius(64f);
        mPc.setDrawCenterText(true);
        mPc.setRotationAngle(0);
        mPc.setRotationEnabled(true);
        mPc.setHighlightPerTapEnabled(true);
        mPc.setOnChartValueSelectedListener(this);
        mPc.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        mPc.setUsePercentValues(true);
        mPc.getDescription().setEnabled(false);
        mPc.setExtraOffsets(5, 10, 5, 5);
        mPc.setDragDecelerationFrictionCoef(0.95f);

        mPc.setCenterTextTypeface(mTfLight);
        mPc.setCenterText(generateCenterSpannableText());

        mPc.setDrawHoleEnabled(true);
        mPc.setHoleColor(Color.WHITE);

        mPc.setTransparentCircleColor(Color.WHITE);
        mPc.setTransparentCircleAlpha(110);

        mPc.setHoleRadius(58f);
        mPc.setTransparentCircleRadius(61f);

        mPc.setDrawCenterText(true);

        mPc.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPc.setRotationEnabled(true);
        mPc.setHighlightPerTapEnabled(true);
        // mPc.setUnit(" €");
        // mPc.setDrawUnitsInChart(true);
        // addOrUpdate a selection listener
        mPc.setOnChartValueSelectedListener(this);
        setData(4, 100);
        mPc.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mPc.spin(2000, 0, 360);
        Legend l = mPc.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextColor(getResources().getColor(R.color.c_666666));
        // entry label styling
        mPc.setEntryLabelColor(Color.WHITE);
        mPc.setEntryLabelTypeface(mTfRegular);
        mPc.setEntryLabelTextSize(12f);
        mPc.setDrawEntryLabels(false);
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5), mParties[i % mParties.length]));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(0f);// //设置个饼状图之间的距离
        dataSet.setSelectionShift(5f);
        dataSet.setHighlightEnabled(true);
//        dataSet.setSelectionShift(5f);
        // addOrUpdate a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : mColor)
            colors.add(getResources().getColor(c));
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mPc.setData(data);

        // undo all highlights
        mPc.highlightValues(null);

        mPc.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onMetrialClick(View view) {
        switch (view.getId()) {
            case R.id.fll_statics_app:
                Intent appStats = new Intent(mActivity, AppStatsActivity.class);
                startActivity(appStats);
        }
    }
}
