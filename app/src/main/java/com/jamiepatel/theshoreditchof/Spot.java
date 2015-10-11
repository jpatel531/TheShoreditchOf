package com.jamiepatel.theshoreditchof;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jamiepatel on 10/10/2015.
 */
public class Spot implements TrendyArea{

    public final String cityName;
    public final String spotName;
    public double latitude = 0;
    public double longitude = 0;

    private static HashMap<String, Spot> SPOT_LOOKUP = new HashMap<String, Spot>();

    public static Spot fromID(String ID){
        return SPOT_LOOKUP.get(ID);
    }

    public Spot(String cityName, String spotName, Context context){
        this.cityName = cityName;
        this.spotName = spotName;

        final String query = cityName + " " + spotName;

        final Geocoder geocoder = new Geocoder(context);

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(query, 1);
            Address address = addresses.get(0);
            latitude = address.getLatitude();
            longitude = address.getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SPOT_LOOKUP.put(spotName, this);

    }

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }
}
