package com.jamiepatel.theshoreditchof;

/**
 * Created by jamiepatel on 10/10/2015.
 */
public class TrendyHangout implements TrendyArea {
    public String name;
    public String address;
    public Double latitude;
    public Double longitude;
    public String url;
    public int expensiveness;

    public TrendyHangout(String name, String address){
        this.name = name;
        this.address = address;
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
