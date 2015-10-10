package com.jamiepatel.theshoreditchof;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks, ResultCallback<Status>, GoogleApiClient.OnConnectionFailedListener {

    SpotListAdapter spotListAdapter;
    ArrayList<Geofence> geofences;
    PendingIntent geofencePendingIntent;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Spot> spotList = Factory.createSpotList(this);
        spotListAdapter = new SpotListAdapter(this, spotList);
        ListView spotListView = (ListView) findViewById(R.id.spot_list);
        spotListView.setAdapter(spotListAdapter);

        spotListView.setOnItemClickListener(this);

        geofences = new ArrayList<Geofence>();

        for (Spot spot : spotList){
            geofences.add(new Geofence.Builder()
                .setRequestId(spot.spotName)
                .setCircularRegion(
                        spot.latitude,
                        spot.longitude,
                        50000
                )
                .setExpirationDuration(100000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build()
            );
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofences);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent(){
        if (geofencePendingIntent != null){
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeodetectionService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Spot spot = (Spot) spotListAdapter.getItem(i);
        Intent showMapIntent = new Intent(this, SpotMapActivity.class);
        showMapIntent.putExtra("latitude", spot.latitude);
        showMapIntent.putExtra("longitude", spot.longitude);
        startActivity(showMapIntent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                getGeofencingRequest(),
                getGeofencePendingIntent()
        ).setResultCallback(this);
        System.out.println("geofence set");
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("connection suspended");
    }

    @Override
    public void onResult(Status status) {
        System.out.println("status received");
        System.out.println(status);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("connection failed");

    }
}
