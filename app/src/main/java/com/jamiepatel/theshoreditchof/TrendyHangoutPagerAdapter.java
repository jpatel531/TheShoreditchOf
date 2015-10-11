package com.jamiepatel.theshoreditchof;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jamiepatel on 10/10/2015.
 */
public class TrendyHangoutPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<TrendyHangout> trendyHangouts;

    public TrendyHangoutPagerAdapter(FragmentManager fm, ArrayList<TrendyHangout> trendyHangouts) {
        super(fm);
        this.trendyHangouts = trendyHangouts;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new TrendyHangoutFragment();
        TrendyHangout hangout = trendyHangouts.get(position);
        Bundle args = new Bundle();
        args.putString("name", hangout.name);
        args.putString("address", hangout.address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return trendyHangouts.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
