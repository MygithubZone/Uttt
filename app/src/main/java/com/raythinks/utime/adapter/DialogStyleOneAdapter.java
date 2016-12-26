package com.raythinks.utime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.raythinks.utime.R;
import com.raythinks.utime.model.SelectedModel;
import com.raythinks.utime.utils.CommomUtils;

import java.util.List;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间：2016/12/25.
 */

public class DialogStyleOneAdapter extends BaseAdapter {
    List<SelectedModel> data;
    boolean isDes;

    public DialogStyleOneAdapter(List<SelectedModel> list, boolean isDes) {
        this.data=list;
        this.isDes = isDes;
    }

    @Override
    public int getCount() {
        return CommomUtils.isEmptyList(data) ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_styleone, null);
            viewHolder.tvSelectName = (TextView) convertView.findViewById(R.id.tv_select_name);
            viewHolder.tvSelectDes = (TextView) convertView.findViewById(R.id.tv_select_des);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvSelectName.setText(data.get(position).getName());
        if (isDes) {
            viewHolder.tvSelectDes.setVisibility(View.VISIBLE);
            viewHolder.tvSelectDes.setText(data.get(position).getDes());
        } else {
            viewHolder.tvSelectDes.setVisibility(View.GONE);
            viewHolder.tvSelectDes.setText("");
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvSelectName;
        TextView tvSelectDes;

    }
}
