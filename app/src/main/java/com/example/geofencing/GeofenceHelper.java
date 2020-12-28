package com.example.geofencing;

import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;

public class GeofenceHelper extends ContextWrapper {

    PendingIntent pendingIntent;

    int pendingIntentRequestCode = 11;

    public GeofenceHelper(Context base) {
        super(base);
    }

    public GeofencingRequest geofencingRequest(Geofence geofence){
        return new GeofencingRequest.Builder().addGeofence(geofence).setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER).build();
    }

    public Geofence geofenceResponse(String geofenceId, LatLng latLng,float radius,int transitionTypes){
        return new Geofence.Builder().setCircularRegion(latLng.latitude,latLng.longitude,radius).setRequestId(geofenceId).setTransitionTypes(transitionTypes).setLoiteringDelay(5000).setExpirationDuration(Geofence.NEVER_EXPIRE).build();
    }

    public PendingIntent getPendingIntent(){
        if(pendingIntent!=null){
            return pendingIntent;
        }else {
            Intent newPendingIntent = new Intent(this,GeofenceBroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,pendingIntentRequestCode,newPendingIntent,PendingIntent.FLAG_UPDATE_CURRENT); // flags are used for adding n removing geofence
            return pendingIntent;
        }
    }

    public String getErrorString(Exception e){
        if(e instanceof ApiException){
            ApiException apiException = (ApiException) e;
            switch (apiException.getStatusCode()){
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                    return "Geofence Not Available";

                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                    return "Get Too Many Geofences";

                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    return "Geofence Too Many Pending Intents";
            }
        }

        return e.getLocalizedMessage();
    }
}
