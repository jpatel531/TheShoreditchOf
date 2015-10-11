package com.jamiepatel.theshoreditchof;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jamiepatel on 10/10/2015.
 */
public class TrendyHangoutFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.trendy_hangout_view, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.hangout_name)).setText(args.getString("name"));
        ((TextView) rootView.findViewById(R.id.hangout_address)).setText(args.getString("address"));
        return rootView;
    }
}
