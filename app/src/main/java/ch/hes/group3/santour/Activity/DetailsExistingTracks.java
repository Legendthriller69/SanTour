package ch.hes.group3.santour.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import ch.hes.group3.santour.DTO.Category;
import ch.hes.group3.santour.DTO.POD;
import ch.hes.group3.santour.DTO.POI;
import ch.hes.group3.santour.DTO.Position;
import ch.hes.group3.santour.DTO.Track;
import ch.hes.group3.santour.DTO.Type;
import ch.hes.group3.santour.Firebase.CategoryDB;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Logic.MapUpdate;


public class DetailsExistingTracks extends Fragment implements OnMapReadyCallback, MapUpdate {

    //elements
    private Button listPOIBtn;
    private Button listPODBtn;
    private TextView txtDistance;
    private TextView txtTime;
    private TextView txtName;

    //fragment
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Bundle bundle;
    private int indexTrack;
    private Track track;
    private static boolean inDetails = false;
    private static List<Category> categories;
    private Type type;

    //maps object
    private MapView mapView;
    private GoogleMap mMap;

    public DetailsExistingTracks() {
        inDetails = true;
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.back_record_menu, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.back_record:
                fragmentManager.popBackStack();
                break;
        }

        return true;
    }

    /**
     * creation of the view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_existing_tracks, container, false);

        // options menu
        setHasOptionsMenu(true);

        fragmentManager = getActivity().getSupportFragmentManager();

        //initialize elements
        txtDistance = (TextView) view.findViewById(R.id.Distance);
        txtTime = (TextView) view.findViewById(R.id.Duree);
        txtName = (TextView) view.findViewById(R.id.TitleTrack);

        //get arguments from fragment
        bundle = getArguments();
        indexTrack = bundle.getInt("index");
        track = WelcomePage.getTracks().get(indexTrack);

        //set name
        setType();
        txtName.setText(track.getName() + " - type : " + type.getName());

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

        setCategories();

        return view;
    }

    /**
     * instantiate google map async
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        zoomMap();
        addPolylineOnMap();
        addMarkerOnMap();
        addBeginEndMarkerOnMap();
    }

    /**
     * zoom on the map at the center of the track
     */
    @Override
    public void zoomMap() {
        Position position = track.getPositions().get(track.getPositions().size() / 2);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(position.getLatitude(), position.getLongitude()), 15));
    }

    /**
     * add polyline on the map with all the track's positions
     */
    @Override
    public void addPolylineOnMap() {
        List<LatLng> points = new ArrayList<>();
        for (int i = 0; i < track.getPositions().size(); i++) {
            Position position = track.getPositions().get(i);
            points.add(new LatLng(position.getLatitude(), position.getLongitude()));
        }

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(points).width(8).color(Color.GRAY);
        mMap.addPolyline(polylineOptions);
    }

    /**
     * add all the markers for the pod and poi
     */
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

    /**
     * add begin and end marker on the map in a different color
     */
    @Override
    public void addBeginEndMarkerOnMap() {
        LatLng position;
        //begin marker
        position = new LatLng(track.getPositions().get(0).getLatitude(), track.getPositions().get(0).getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(getString(R.string.beginTrack))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //end marker
        position = new LatLng(track.getPositions().get(track.getPositions().size() - 1).getLatitude(), track.getPositions().get(track.getPositions().size() - 1).getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(getString(R.string.endTrack))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
    }

    /**
     * listener to show the list of all the poi for the current track
     */
    private class ListPOIListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (track.getPois().size() > 0) {
                fragment = new ListPOIs();
                bundle = new Bundle();
                bundle.putSerializable("TRACK", track);
                fragment.setArguments(bundle);
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();
            } else {
                Toast.makeText(getActivity(), R.string.noPOIForThisTrack, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * listener to get all the pod of the current track
     */
    private class ListPODListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (track.getPods().size() > 0) {
                fragment = new ListPODs();
                bundle = new Bundle();
                bundle.putSerializable("TRACK", track);
                fragment.setArguments(bundle);
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();
            } else {
                Toast.makeText(getActivity(), R.string.noPODForTrack, Toast.LENGTH_SHORT).show();
            }
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

    public static List<Category> getCategories() {
        return DetailsExistingTracks.categories;
    }

    /**
     * set all the categories with the list from firebase
     */
    private void setCategories() {
        CategoryDB.getAllCategories(new DataListener() {
            @Override
            public void onSuccess(Object object) {
                categories = (List<Category>) object;
            }

            @Override
            public void onFailed(Object object) {

            }
        });
    }

    /**
     * set the type of the track
     */
    private void setType() {
        for (int i = 0; i < WelcomePage.getTypes().size(); i++) {
            Type currentType = WelcomePage.getTypes().get(i);
            if (currentType.getId().equals(track.getIdType())) {
                type = currentType;
                return;
            }
        }
    }
}
