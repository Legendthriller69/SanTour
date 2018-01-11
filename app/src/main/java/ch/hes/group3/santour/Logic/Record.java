package ch.hes.group3.santour.Logic;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.hes.group3.santour.Activity.MainActivity;
import ch.hes.group3.santour.DTO.Position;
import ch.hes.group3.santour.DTO.Track;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Firebase.TrackDB;

/**
 * Created by aleks on 22.11.2017.
 */

public class Record implements Serializable {
    private static FusedLocationProviderClient mFusedLocationClient;
    private static Activity activity;
    private static LocationRequest mLocationRequest;
    private static LocationCallback locationCallback;
    private static GoogleMap mMap;
    private static boolean isRecording = false;
    private static TextView txtDistance;
    private static String text = "0.0";


    //all linked to tracks
    private static Track track;
    private static double distance = 0;
    private static List<Position> positions = new ArrayList<>();


    //Last position
    private static Position lastPosition;

    private static float[] distances = new float[1];

    private Record() {

    }

    public static void getInstance(Activity activity, TextView txtDistance) {
        Record.activity = activity;
        Record.txtDistance = txtDistance;
        if (mFusedLocationClient == null) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        }
    }

    /**
     * start the recording of a track
     * and creates the locationUpdate
     */
    public static void startRecording() {
        setUserCurrentPosition();
        createLocationRequest();
        startLocationUpdates();
        isRecording = true;
    }

    /**
     * move the camera to the user position
     */
    public static void moveCameraToUserPosition() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            LatLng currentLocation;
                            if (location != null) {
                                // Logic to handle location object
                                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title(activity.getString(ch.hes.group3.santour.Activity.R.string.youAreHere)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13.0f));
                            }
                        }
                    });
        } catch (SecurityException e) {
        }
    }

    /**
     * get the user latitude and longitude
     * @param dataListener
     */
    public static void getUserLatLng(final DataListener dataListener) {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                dataListener.onSuccess(location);
                            }
                        }
                    });
        } catch (SecurityException e) {
        }
    }

    /**
     * set the user current position on the map
     */
    public static void setUserCurrentPosition() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            LatLng currentLocation;
                            if (location != null) {
                                // Logic to handle location object
                                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                                positions.add(new Position(location.getLongitude(), location.getLatitude(), location.getAltitude(), new Date().toString()));
                            } else
                                Toast.makeText(activity, activity.getString(ch.hes.group3.santour.Activity.R.string.check_location_activity), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (SecurityException e) {
        }
    }

    /**
     * creates the location request with an interval of 1 second
     */
    private static void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * starts the location update
     * method will be called at every location change
     */
    public static void startLocationUpdates() {
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
                            text = String.valueOf(Math.floor(distance * 100) / 100);
                            txtDistance.setText(text);

                            LatLng latLngLastPosition = new LatLng(lastPosition.getLatitude(), lastPosition.getLongitude());
                            LatLng latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addPolyline(new PolylineOptions().add(latLngLastPosition, latLngLocation).width(4f).color(Color.RED)
                                    .geodesic(true));
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

    /**
     * pause the location update
     */
    public static void pauseLocationUpdates() {
        if (locationCallback != null)
            mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    /**
     * stops the location update
     */
    private static void stopLocationUpdates() {
        //stop the location update
        if (locationCallback != null)
            mFusedLocationClient.removeLocationUpdates(locationCallback);

        isRecording = false;
    }

    /**
     * create the track into the database with all the needed informations
     * @param name
     * @param description
     * @param duration
     * @param idType
     * @param idUser
     */
    public static void createTrack(String name, String description, int duration, String idType, String idUser) {
        //stop the location updates
        stopLocationUpdates();

        //create the track
        track = MainActivity.getTrack();
        Log.e("ALEKS", track.getPods().size() + "");
        Log.e("ALEKS", track.getPois().size() + "");

        track.setName(name);
        track.setDuration(duration);
        track.setDistance(distance);
        track.setPositions(positions);
        track.setIdType(idType);
        track.setIdUser(idUser);
        track.setDescription(description);
        TrackDB.createTrack(track);
        MainActivity.setTrack(null);
    }

    /**
     * boolean to check if it is recording
     * @return
     */
    public static boolean isRecording() {
        return isRecording;
    }

    /**
     * check if the last position and the current location are the same
     * not to add twice some locations into the db
     * @param lastPosition
     * @param location
     * @return
     */
    private static boolean isSamePosition(Position lastPosition, Location location) {
        double newLong = Math.floor(location.getLongitude() * 100000) / 100000;
        double newLat = Math.floor(location.getLatitude() * 100000) / 100000;
        double lastLong = Math.floor(lastPosition.getLongitude() * 100000) / 100000;
        double lastLat = Math.floor(lastPosition.getLatitude() * 100000) / 100000;

        Location lastLocation = new Location("Last location");
        lastLocation.setLongitude(lastLong);
        lastLocation.setLatitude(lastLat);

        Log.e("LAST LATITUDE", lastLong + "");
        Log.e("LAST LONGITUDE", lastLat + "");
        Log.e("NEW LATITUDE", newLong + "");
        Log.e("NEW LONGITUDE", newLat + "");

        if ((newLong == lastLong && newLat == lastLat) || location.distanceTo(lastLocation) < 2)
            return true;
        return false;
    }

    /**
     * add marker on the map
     * @param latitude
     * @param longitude
     * @param text
     */
    public static void addMarker(double latitude, double longitude, String text) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(text));
    }

    /**
     * set the map to the current one
     * @param mMap
     */
    public static void setMap(GoogleMap mMap) {
        Record.mMap = mMap;
    }

    /**
     * get current distance
     * @return
     */
    public static String getDistanceText() {
        return text;
    }

    /**
     * update the map with all the needed informations
     */
    public static void updateMap() {
        if (MainActivity.getTrack() != null) {
            if (positions.size() >= 1) {
                Position currentPosition = positions.get(0);
                for (int i = 1; i < positions.size(); i++) {
                    Log.e("add polyline", "add polyline");
                    LatLng currentLatLng = new LatLng(currentPosition.getLatitude(), currentPosition.getLongitude());
                    LatLng nextLatLng = new LatLng(positions.get(i).getLatitude(), positions.get(i).getLongitude());
                    mMap.addPolyline(new PolylineOptions().add(currentLatLng, nextLatLng).width(4f).color(Color.RED)
                            .geodesic(true));
                    currentPosition = positions.get(i);
                }
            }
        }
    }

    /**
     * destroy the current record when the
     * track is saved
     */
    public static void destroy(){
        mFusedLocationClient = null;
        isRecording = false;
    }
}
