package com.jamiepatel.theshoreditchof;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jamiepatel on 10/10/2015.
 */
public class Factory {

    public static ArrayList<Spot> createSpotList(Context context){
        final ArrayList<Spot> list = new ArrayList<Spot>();
        list.add(new Spot("London", "Shoreditch", context));
        list.add(new Spot("Berlin", "Kreuzberg", context));
        list.add(new Spot("New York", "Williamsburg", context));
        return list;
    }

}
