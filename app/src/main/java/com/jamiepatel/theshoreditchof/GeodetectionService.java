package com.jamiepatel.theshoreditchof;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by jamiepatel on 10/10/2015.
 */
public class GeodetectionService extends IntentService {

    public GeodetectionService() {
        super("GeodetectionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("EVENT!!!");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()){
            System.out.println(geofencingEvent.getErrorCode());
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
            String geofenceId = geofencingEvent.getTriggeringGeofences().get(0).getRequestId();
            Spot spot = Spot.fromID(geofenceId);
            sendNotification(spot);
        }
    }

    private void sendNotification(Spot spot){
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, SpotMapActivity.class);
        intent.putExtra("latitude", spot.latitude);
        intent.putExtra("longitude", spot.longitude);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Hearty365")
                .setContentTitle("The Shoreditch of...")
                .setContentText(spot.cityName)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");

        notificationManager.notify(1, b.build());

    }
}
