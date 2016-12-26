package com.raythinks.utime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.raythinks.utime.R;
import com.raythinks.utime.mvp.contract.SceneModeContract;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/21 0021<br>.
 * 版本：1.2.0
 */

public class SceneModeAdapter extends BaseAdapter {
    Context context;
    SceneModeContract.Presenter presenter;
    public SceneModeAdapter(Context context) {
        this.context = context;
    }
    public SceneModeAdapter(Context context, SceneModeContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return 4;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_scene_model, null);
            viewHolder.tvSceneTime = (TextView) convertView.findViewById(R.id.tv_scene_time);
            viewHolder.tvSceneName = (TextView) convertView.findViewById(R.id.tv_scene_name);
            viewHolder.tvSceneRepeat = (TextView) convertView.findViewById(R.id.tv_scene_repeat);
            viewHolder.stSceneIsopen = (Switch) convertView.findViewById(R.id.st_scene_isopen);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvSceneTime;
        TextView tvSceneName;
        TextView tvSceneRepeat;
        Switch stSceneIsopen;
    }
}
