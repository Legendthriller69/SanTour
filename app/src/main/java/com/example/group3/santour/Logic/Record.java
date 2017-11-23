package com.example.group3.santour.Logic;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.group3.santour.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by aleks on 22.11.2017.
 */

public class Record {
    private FusedLocationProviderClient mFusedLocationClient;

    public Record() {

    }

    public void startRecording() {

    }

    public void getUserCurrentPosition(final Activity activity, final GoogleMap mMap) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.e("RENTRE", String.valueOf(location));
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                Log.e("Latitude", String.valueOf(location.getLatitude()));
                                Log.e("Longitude", String.valueOf(location.getLongitude()));
                                Log.e("Altitude", String.valueOf(location.getAltitude()));
                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                            } else
                                Toast.makeText(activity, activity.getString(R.string.check_location_activity), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch(SecurityException e) {
        }
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

}
