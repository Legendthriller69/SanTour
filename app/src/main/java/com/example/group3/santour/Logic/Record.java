package com.example.group3.santour.Logic;

import android.app.Activity;
import android.location.Location;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group3.santour.DTO.POD;
import com.example.group3.santour.DTO.POI;
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
    private boolean isRecording;
    private TextView txtDistance;

    //all linked to tracks
    private String name;
    private String description;
    private double distance;
    private int duration;
    private List<POI> pois;
    private List<POD> pods;
    private List<Position> positions;
    private String idUser;
    private String idType;

    //Chrono
    private Chronometer chrono;

    //Last position
    private Position lastPosition;

    private float[] distances;

    public Record(Activity activity, GoogleMap mMap, TextView txtDistance) {
        this.activity = activity;
        this.mMap = mMap;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        positions = new ArrayList<>();
        pois = new ArrayList<>();
        pods = new ArrayList<>();
        isRecording = false;
        distance = 0;
        distances = new float[1];
        this.txtDistance = txtDistance;
    }

    public void startRecording() {
        setUserCurrentPosition();
        createLocationRequest();
        startLocationUpdates();
        isRecording = true;
    }

    public void moveCameraToUserPosition() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            mMap.clear();
                            LatLng currentLocation;
                            if (location != null) {
                                // Logic to handle location object
                                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                            } else
                                Toast.makeText(activity, activity.getString(R.string.check_location_activity), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (SecurityException e) {
        }
    }

    public void setUserCurrentPosition() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            mMap.clear();
                            LatLng currentLocation;
                            if (location != null) {
                                // Logic to handle location object
                                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
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
                if (positions.size() != 0) {
                    for (Location location : locationResult.getLocations()) {
                        lastPosition = positions.get(positions.size() - 1);
                        if (!isSamePosition(lastPosition, location)) {
                            positions.add(new Position(location.getLongitude(), location.getLatitude(), location.getAltitude(), new Date().toString()));
                            Location.distanceBetween(lastPosition.getLatitude(), lastPosition.getLongitude(), location.getLatitude(), location.getLongitude(), distances);
                            distance += distances[0];
                            String text = String.valueOf(Math.floor(distance * 100) / 100);
                            txtDistance.setText(text);
                            Log.e("DISTANCE", distance + "");
                        }
                    }
                }
            }
        };

        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    locationCallback,
                    null /* Looper */);
        } catch (SecurityException e) {
            Log.e("Security Exception", e.getMessage());
        }
    }

    public void pauseLocationUpdates() {
        if (locationCallback != null)
            mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public void stopLocationUpdates() {
        //stop the location update
        if (locationCallback != null)
            mFusedLocationClient.removeLocationUpdates(locationCallback);


        //create a new track with all informations

    }

    public boolean isRecording() {
        return isRecording;
    }

    private boolean isSamePosition(Position lastPosition, Location location) {
        double newLong = Math.floor(location.getLongitude() * 100000) / 100000;
        double newLat = Math.floor(location.getLatitude() * 100000) / 100000;
        double lastLong = Math.floor(lastPosition.getLongitude() * 100000) / 100000;
        double lastLat = Math.floor(lastPosition.getLatitude() * 100000) / 100000;
        Log.e("LAST LATITUDE", lastLong + "");
        Log.e("LAST LONGITUDE", lastLat + "");
        Log.e("NEW LATITUDE", newLong + "");
        Log.e("NEW LONGITUDE", newLat + "");
        if (newLong == lastLong && newLat == lastLat)
            return true;
        return false;
    }

    public List<Position> getPositions() {
        return positions;
    }
}
