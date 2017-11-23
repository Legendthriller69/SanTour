package com.example.group3.santour.Logic;

import android.app.Activity;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.group3.santour.DTO.Position;
import com.example.group3.santour.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.group3.santour.DTO.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aleks on 22.11.2017.
 */

public class Record {
    private FusedLocationProviderClient mFusedLocationClient;
    private Activity activity;
    private LocationRequest mLocationRequest;
    private LocationCallback locationCallback;
    private GoogleMap mMap;
    private List<Position> positions;

    public Record(Activity activity, GoogleMap mMap) {
        this.activity = activity;
        this.mMap = mMap;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        positions = new ArrayList<>();
    }

    public void startRecording() {
        getUserCurrentPosition();
        createLocationRequest();
        startLocationUpdates();
    }

    public void getUserCurrentPosition() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                                positions.add(new Position(location.getLongitude(), location.getLatitude(), location.getAltitude(), new Date().toString()));
                            } else
                                Toast.makeText(activity, activity.getString(R.string.check_location_activity), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (SecurityException e) {
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startLocationUpdates() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    if(location.getLongitude() != positions.get(positions.size() - 1).getLongitude() || location.getLatitude() != positions.get(positions.size() - 1).getLatitude() || location.getAltitude() != positions.get(positions.size() - 1).getAltitude())
                        positions.add(new Position(location.getLongitude(), location.getLatitude(), location.getAltitude(), new Date().toString()));
                }
            }
        };

        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    locationCallback,
                    null /* Looper */);
        } catch (SecurityException e) {
        }
    }
}
