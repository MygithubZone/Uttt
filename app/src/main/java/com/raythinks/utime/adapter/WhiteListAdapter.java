package com.raythinks.utime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.raythinks.utime.R;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间：2016/12/19.
 */

public class WhiteListAdapter extends RecyclerView.Adapter<WhiteListAdapter.ViewHolder> {

    private Context mContext;

    public WhiteListAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_whitelist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
    }


    @Override
    public int getItemCount() {
        return 15;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRappicon;
        TextView tvAppname;
        Switch stIsopen;

        public ViewHolder(View convertView) {
            super(convertView);
            ivRappicon = (ImageView) convertView.findViewById(R.id.iv_rappicon);
            tvAppname = (TextView) convertView.findViewById(R.id.tv_appname);
            stIsopen = (Switch) convertView.findViewById(R.id.st_isopen);

        }


    }
}
