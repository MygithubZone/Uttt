package com.raythinks.utime.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.raythinks.utime.R;
import com.raythinks.utime.mvp.contract.RankContract;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间：2016/12/19.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    private Context mContext;

    public RankAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_ranks, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvRankTopnum.setText("" + (position + 1));
        holder.ivRankStatics.setText((12 - position % 10) + "x");
    }


    @Override
    public int getItemCount() {
        return 15;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRankTopnum;
        ImageView ivRankAppicon;
        TextView ivRankAppname;
        TextView ivRankStatics;

        public ViewHolder(View convertView) {
            super(convertView);
            tvRankTopnum = (TextView) convertView.findViewById(R.id.tv_rank_topnum);
            ivRankAppicon = (ImageView) convertView.findViewById(R.id.iv_rank_appicon);
            ivRankAppname = (TextView) convertView.findViewById(R.id.iv_rank_appname);
            ivRankStatics = (TextView) convertView.findViewById(R.id.iv_rank_statics);
        }


    }
}
