package ch.hes.group3.santour.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ch.hes.group3.santour.DTO.POD;
import ch.hes.group3.santour.DTO.POI;
import ch.hes.group3.santour.DTO.Position;
import ch.hes.group3.santour.DTO.Track;
import ch.hes.group3.santour.Logic.MapUpdate;


public class DetailsExistingTracks extends Fragment implements OnMapReadyCallback, MapUpdate {

    //elements
    private Button listPOIBtn;
    private Button listPODBtn;
    private TextView txtDistance;
    private TextView txtTime;
    private TextView txtName;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Bundle bundle;
    private int indexTrack;
    private Track track;
    private static boolean inDetails = false;

    //maps object
    private MapView mapView;
    private GoogleMap mMap;

    public DetailsExistingTracks() {
        inDetails = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_existing_tracks, container, false);

        //initialize elements
        txtDistance = (TextView) view.findViewById(R.id.Distance);
        txtTime = (TextView) view.findViewById(R.id.Duree);
        txtName = (TextView) view.findViewById(R.id.TitleTrack);

        //get arguments from fragment
        bundle = getArguments();
        indexTrack = bundle.getInt("index");
        track = WelcomePage.getTracks().get(indexTrack);

        //set name
        txtName.setText(track.getName());

        //set time and distance
        if (track.getDistance() < 1000) {
            txtDistance.setText((new DecimalFormat("#.##").format(track.getDistance())) + " m");
        } else {
            txtDistance.setText((new DecimalFormat("#.##").format(track.getDistance() / 1000)) + " km");
        }

        if (track.getDuration() < 3600) {
            txtTime.setText((new DecimalFormat("#.##").format((double) track.getDuration() / 60)) + " min");
        } else {
            txtTime.setText((new DecimalFormat("#.##").format((double) track.getDuration() / 3600)) + " h");
        }


        //create the mapview
        mapView = (MapView) view.findViewById(R.id.Map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        listPODBtn = (Button) view.findViewById(R.id.btnListPOD);
        listPOIBtn = (Button) view.findViewById(R.id.btnListPOI);

        listPOIBtn.setOnClickListener(new ListPOIListener());
        listPODBtn.setOnClickListener(new ListPODListener());


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        zoomMap();
        addPolylineOnMap();
        addMarkerOnMap();
    }

    @Override
    public void zoomMap() {
        Position position = track.getPositions().get(track.getPositions().size() / 2);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(position.getLatitude(), position.getLongitude()), 15));
    }

    @Override
    public void addPolylineOnMap() {
        List<LatLng> points = new ArrayList<>();
        for (int i = 0; i < track.getPositions().size(); i++) {
            Position position = track.getPositions().get(i);
            points.add(new LatLng(position.getLatitude(), position.getLongitude()));
        }

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(points).width(5).color(Color.RED);
        mMap.addPolyline(polylineOptions);
    }

    @Override
    public void addMarkerOnMap() {
        LatLng position;
        POI currentPoi;
        POD currentPod;

        //loop on all poi
        for (int i = 0; i < track.getPois().size(); i++) {
            currentPoi = track.getPois().get(i);
            position = new LatLng(currentPoi.getPosition().getLatitude(), currentPoi.getPosition().getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title("POI : " + currentPoi.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }

        //loop on all pod
        for (int i = 0; i < track.getPods().size(); i++) {
            currentPod = track.getPods().get(i);
            position = new LatLng(currentPod.getPosition().getLatitude(), currentPod.getPosition().getLongitude());
            mMap.addMarker(new MarkerOptions().
                    position(position).
                    title("POD : " + currentPod.getName()));
        }
    }

    private class ListPOIListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class ListPODListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public static boolean isInDetails() {
        return inDetails;
    }

    public static void setInDetails(boolean inDetails) {
        DetailsExistingTracks.inDetails = inDetails;
    }
}
