package com.jamiepatel.theshoreditchof;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SpotMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private double latitude = 0;
    private double longitude = 0;
    Spot spot;
    Marker currentMarker;

    TrendyHangoutPagerAdapter trendyHangoutPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spot = Spot.fromID(getIntent().getExtras().getString("spot_id"));
        latitude = spot.latitude;
        longitude = spot.longitude;

        setContentView(R.layout.activity_spot_map);
        setUpMapIfNeeded();

        findTrendyHangouts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LatLng position = new LatLng(latitude, longitude);
        currentMarker = mMap.addMarker(new MarkerOptions().position(position).title(spot.spotName));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
    }

    private void findTrendyHangouts(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://tso.ngrok.com/trendy_hangouts?lat="+
                Double.toString(latitude) +
                "&long=" +
                Double.toString(longitude);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
                Gson gson = new Gson();
                final ArrayList<TrendyHangout> trendyHangouts = gson.fromJson(new String(responseBody), new TypeToken<List<TrendyHangout>>(){}.getType());
                trendyHangoutPagerAdapter = new TrendyHangoutPagerAdapter(getSupportFragmentManager(), trendyHangouts);
                final ViewPager trendyHangoutPager = (ViewPager) findViewById(R.id.trendy_hangout_pager);
                trendyHangoutPager.setAdapter(trendyHangoutPagerAdapter);
                trendyHangoutPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        currentMarker.remove();
                        TrendyHangout hangout = trendyHangouts.get(i);
                        LatLng position = new LatLng(hangout.latitude, hangout.longitude);
                        currentMarker = mMap.addMarker(new MarkerOptions().position(position).title(hangout.name));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


}
