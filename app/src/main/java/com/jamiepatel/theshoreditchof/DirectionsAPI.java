package com.jamiepatel.theshoreditchof;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by jamiepatel on 11/10/2015.
 */
public class DirectionsAPI {

    final static String BASE_URL = "https://maps.googleapis.com/maps/api/directions/json?";

    public static void showDirections(final GoogleMap map, String locLat, TrendyArea area, Context context) {
        AsyncHttpClient client = new AsyncHttpClient();

        String url = BASE_URL +
                "origin=" +
                locLat +
                "&destination="+
                Double.toString(area.getLatitude())+
                ","+
                Double.toString(area.getLongitude())+
                "&key="+
                "AIzaSyBuPat5SV0NrarazjPahmaECMIrG1wdD7U";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject json = null;
                try {
                    String jsonString = new String(responseBody);
                    System.out.println(jsonString);
                    json = new JSONObject(jsonString);
                    JSONArray routes = json.optJSONArray("routes");
                    JSONObject route = routes.optJSONObject(0);
                    String overviewPolyline = route.optJSONObject("overview_polyline").optString("points");
                    List<LatLng> latLngs = PolyUtil.decode(overviewPolyline);
                    Polyline line = map.addPolyline(
                            new PolylineOptions()
                                    .add(latLngs.toArray(new LatLng[latLngs.size()]))
                                    .width(5)
                                    .color(Color.RED));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
