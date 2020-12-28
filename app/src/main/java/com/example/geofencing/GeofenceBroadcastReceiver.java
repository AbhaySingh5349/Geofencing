package com.example.geofencing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context,"Geofence Triggered..",Toast.LENGTH_SHORT).show();

        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        String geofenceId;

        if(geofencingEvent.hasError()){
            Toast.makeText(context,"Error Receiving Geofence Event..",Toast.LENGTH_SHORT).show();
        }
        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for(Geofence geofence: geofenceList){
            geofenceId = geofence.getRequestId();
        }
        Location geofenceTriggeredLocation = geofencingEvent.getTriggeringLocation();

        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType){
            case  Geofence.GEOFENCE_TRANSITION_ENTER:
                  Toast.makeText(context,"Geofence Transition Enter",Toast.LENGTH_SHORT).show();
                  notificationHelper.sendHighPriorityNotification("Geofence Transition Enter","",MapsActivity.class);
                  break;

            case Geofence.GEOFENCE_TRANSITION_DWELL:
                  Toast.makeText(context,"Geofence Transition Dwell",Toast.LENGTH_SHORT).show();
                  notificationHelper.sendHighPriorityNotification("Geofence Transition Dwell","",MapsActivity.class);
                  break;

            case Geofence.GEOFENCE_TRANSITION_EXIT:
                  Toast.makeText(context,"Geofence Transition Exit",Toast.LENGTH_SHORT).show();
                  notificationHelper.sendHighPriorityNotification("Geofence Transition Exit","",MapsActivity.class);
                  break;
        }
    }
}
