package com.jamiepatel.theshoreditchof;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamiepatel on 10/10/2015.
 */
public class SpotListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Spot> spotList;

    public SpotListAdapter(Context context, ArrayList<Spot> spots){
        mContext = context;
        spotList = spots;
    }

    @Override
    public int getCount() {
        return spotList.size();
    }

    @Override
    public Object getItem(int i) {
        return spotList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SpotOverViewHolder holder;
        if (view == null){
            LayoutInflater messageInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = messageInflater.inflate(R.layout.spot_overview, null);
            holder = new SpotOverViewHolder();
            holder.cityName = (TextView) view.findViewById(R.id.city_name);
            holder.spotName = (TextView) view.findViewById(R.id.spot_name);
            view.setTag(holder);
        } else {
            holder = (SpotOverViewHolder) view.getTag();
        }

        Spot spot = (Spot) getItem(i);

        holder.cityName.setText(spot.cityName);
        holder.spotName.setText(spot.spotName);

        return view;
    }

    private static class SpotOverViewHolder {
        public TextView cityName;
        public TextView spotName;
    }
}
