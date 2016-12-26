package com.raythinks.utime.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.PieChart;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/21 0021<br>.
 * 版本：1.2.0
 */

public class FourThreePieChart extends PieChart {
    public FourThreePieChart(Context context) {
        super(context);
    }

    public FourThreePieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int fourThreeHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthSpec) * 3 / 4,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, fourThreeHeight);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    public FourThreePieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
