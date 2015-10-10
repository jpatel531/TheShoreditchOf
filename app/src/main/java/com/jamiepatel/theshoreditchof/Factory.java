package com.jamiepatel.theshoreditchof;

import android.content.Context;

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
