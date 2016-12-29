package com.raythinks.utime.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.raythinks.utime.R;
import com.raythinks.utime.mirror.model.AppUseStaticsModel;
import com.raythinks.utime.mirror.utils.CommonUtils;
import com.raythinks.utime.mvp.contract.SceneModeContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间：2016/12/26.
 */

public class TrafficStatsDataAdapter extends BaseAdapter {
    Context context;
    SceneModeContract.Presenter presenter;
    private List<AppUseStaticsModel> data = new ArrayList<AppUseStaticsModel>();
    public int currentOldPosi = -1;
    int total;

    @Override
    public int getCount() {
        return data == null ? 0 : (data.size() > 7 ? 7 : data.size());
    }

    public TrafficStatsDataAdapter(Context context) {
        this.context = context;
    }


    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_appstats, null);
            viewHolder.tvAppstatsTopnum = (TextView) convertView.findViewById(R.id.tv_appstats_topnum);
            viewHolder.ivAppstatsAppname = (TextView) convertView.findViewById(R.id.iv_appstats_appname);
            viewHolder.rpbAppstatsData = (RoundCornerProgressBar) convertView.findViewById(R.id.rpb_appstats_data);
            viewHolder.tvAppstatsData = (TextView) convertView.findViewById(R.id.tv_appstats_data);
            viewHolder.ivAppstatsIcon = (ImageView) convertView.findViewById(R.id.iv_appstats_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            viewHolder.ivAppstatsIcon.setImageDrawable(context.getPackageManager().getApplicationIcon(data.get(position).getPkgName()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        viewHolder.tvAppstatsTopnum.setText((position + 1) + "");
        viewHolder.ivAppstatsAppname.setText(data.get(position).getAppName());
        viewHolder.rpbAppstatsData.setMax(total);
        viewHolder.rpbAppstatsData.setProgress(data.get(position).getUseTime());
        viewHolder.rpbAppstatsData.invalidate();
        viewHolder.tvAppstatsData.setText(CommonUtils.getFormatTime(data.get(
                position).getUseTime()));
        return convertView;
    }

    public void setData(List<AppUseStaticsModel> data, int total) {
        this.data = data;
        this.total = total;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView tvAppstatsTopnum;
        TextView ivAppstatsAppname;
        RoundCornerProgressBar rpbAppstatsData;
        TextView tvAppstatsData;
        ImageView ivAppstatsIcon;

    }
}
