package ch.hes.group3.santour.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import ch.hes.group3.santour.DTO.Track;
import ch.hes.group3.santour.DTO.Type;
import ch.hes.group3.santour.Firebase.Authentication;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Firebase.TypeDB;
import ch.hes.group3.santour.Logic.Record;

public class Record_Fragment extends Fragment implements OnMapReadyCallback {

    //elements
    private ImageButton btnStart;
    private ImageButton btnPause;
    private ImageButton btnStop;
    private ImageButton btnAddPoi;
    private ImageButton btnAddPod;
    private TextView txtDistance;
    private EditText txtTrackName;
    private Chronometer chrono;

    //Maps objects
    private MapView mapView;
    private GoogleMap mMap;
    private Long timeWhenPause;
    private LocationManager locationManager;

    //fragments
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private boolean outOfView;

    //types
    private List<Type> types;
    private String[] typesString;

    public Record_Fragment() {
        timeWhenPause = Long.valueOf(0);
        outOfView = false;
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_menu, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_pod:
                if (MainActivity.getTrack() != null && MainActivity.getTrack().getPods().size() > 0) {
                    btnPause.callOnClick();
                    outOfView = true;
                    fragment = new ListPODs();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment).commit();
                    transaction.addToBackStack(null);
                    break;
                } else {
                    Toast.makeText(getContext(), R.string.CreatePODFirst, Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.list_poi:
                if (MainActivity.getTrack() != null && MainActivity.getTrack().getPois().size() > 0) {
                    btnPause.callOnClick();
                    outOfView = true;
                    fragment = new ListPOIs();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment).commit();
                    transaction.addToBackStack(null);
                    break;
                } else {
                    Toast.makeText(getContext(), R.string.CreatePOIFirst, Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.home:
                if (!Record.isRecording()) {
                    getActivity().finish();
                    break;
                } else {
                    Toast.makeText(getActivity(), R.string.saveTrackFirst, Toast.LENGTH_SHORT).show();
                    break;
                }
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        MainActivity.setIsInRecordFragment(true);
        //options menu
        setHasOptionsMenu(true);

        //instantiate map view
        mapView = (MapView) view.findViewById(R.id.Map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //location manager
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        //instantiate all the elements
        btnStart = (ImageButton) view.findViewById(R.id.ButtonPlay);
        btnPause = (ImageButton) view.findViewById(R.id.ButtonPause);
        btnStop = (ImageButton) view.findViewById(R.id.ButtonSave);
        btnAddPod = (ImageButton) view.findViewById(R.id.ButtonAddPOD);
        btnAddPoi = (ImageButton) view.findViewById(R.id.ButtonAddPOI);
        txtDistance = (TextView) view.findViewById(R.id.txtDistance);
        txtTrackName = (EditText) view.findViewById(R.id.txtTrackName);
        chrono = (Chronometer) view.findViewById(R.id.chrono);

        Record.getInstance(getActivity(), txtDistance);

        //disable btnPause and btnStop and addpod addpoi
        btnStart.setBackgroundColor(getResources().getColor(R.color.red_main));
        btnPause.setEnabled(false);
        btnPause.setBackgroundColor(Color.TRANSPARENT);
        btnStop.setEnabled(false);
        btnStop.setBackgroundColor(Color.TRANSPARENT);
        btnAddPoi.setEnabled(false);
        btnAddPoi.setBackgroundColor(Color.TRANSPARENT);
        btnAddPod.setEnabled(false);
        btnAddPod.setBackgroundColor(Color.TRANSPARENT);

        //onclick for start pause stop
        btnStart.setOnClickListener(new StartRecording());
        btnPause.setOnClickListener(new PauseRecording());
        btnStop.setOnClickListener(new StopRecording());

        //navigation button to pod
        btnAddPod = (ImageButton) view.findViewById(R.id.ButtonAddPOD);
        btnAddPod.setOnClickListener(new AddPOD());

        //navigation button to poi
        btnAddPoi = (ImageButton) view.findViewById(R.id.ButtonAddPOI);
        btnAddPoi.setOnClickListener(new AddPOI());

        //trackname text
        txtTrackName.setText("");

        //load list of types
        TypeDB.getAllTypes(new DataListener() {
            @Override
            public void onSuccess(Object object) {
                types = (ArrayList<Type>) object;
                typesString = new String[types.size()];
                for (int i = 0; i < types.size(); i++) {
                    typesString[i] = types.get(i).getName();
                }
            }

            @Override
            public void onFailed(Object object) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * instantiage the map on map ready async method
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Record.setMap(mMap);
        Record.moveCameraToUserPosition();
        Record.updateMap();
    }

    /**
     * listener to add a new pod
     */
    private class AddPOD implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            btnPause.callOnClick();
            outOfView = true;
            fragment = new Pod_Fragment();
            fragmentManager = getActivity().getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    /**
     * listener to add a new poi
     */
    private class AddPOI implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            btnPause.callOnClick();
            outOfView = true;
            fragment = new Poi_Fragment();
            fragmentManager = getActivity().getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();
        }
    }

    /**
     * listener to start recording the track
     */
    private class StartRecording implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //create a new track in the main activity now that he has started the track
            if (MainActivity.getTrack() == null) {
                MainActivity.setTrack(new Track());
            }

            //call the method to start recording
            Record.startRecording();

            //enable or disable buttons needed
            btnStart.setEnabled(false);
            btnStart.setBackgroundColor(Color.TRANSPARENT);
            btnPause.setEnabled(true);
            btnPause.setBackgroundColor(getResources().getColor(R.color.red_main));
            btnStop.setEnabled(true);
            btnStop.setBackgroundColor(getResources().getColor(R.color.red_main));
            btnAddPod.setEnabled(true);
            btnAddPod.setBackgroundColor(getResources().getColor(R.color.red_main));
            btnAddPoi.setEnabled(true);
            btnAddPoi.setBackgroundColor(getResources().getColor(R.color.red_main));

            //start the chronometer
            chrono.setBase(SystemClock.elapsedRealtime() + timeWhenPause);
            chrono.start();
        }
    }

    /**
     * listener to pause recording for the track
     */
    private class PauseRecording implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Record.pauseLocationUpdates();
            btnStart.setEnabled(true);
            btnStart.setBackgroundColor(getResources().getColor(R.color.red_main));
            btnPause.setEnabled(false);
            btnPause.setBackgroundColor(Color.TRANSPARENT);
            btnStop.setEnabled(false);
            btnStop.setBackgroundColor(Color.TRANSPARENT);
            timeWhenPause = chrono.getBase() - SystemClock.elapsedRealtime();
            chrono.stop();
        }
    }

    /**
     * listener to stop recording for the track and save the track into the db
     */
    private class StopRecording implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (formValidation()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.typeTrack);
                builder.setItems(typesString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Record.createTrack(txtTrackName.getText().toString(), "no description", (int) ((SystemClock.elapsedRealtime() - chrono.getBase()) / 1000), types.get(i).getId(), Authentication.getCurrentUser().getId());
                        Toast.makeText(getActivity(), R.string.trackSaved, Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            createGpsDisabledAlert();
        }

        if (outOfView)
            if (Record.isRecording()) {
                txtDistance.setText(Record.getDistanceText());
                btnStart.callOnClick();
                outOfView = false;
            }
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

    /**
     * create an alert dialog to say that the gps is disabled
     * will start another activity to activate the gps
     */
    private void createGpsDisabledAlert() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
        localBuilder
                .setMessage(R.string.gpsDisabled)
                .setCancelable(false)
                .setPositiveButton(R.string.activateGPS,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                Record_Fragment.this.showGpsOptions();
                            }
                        }
                );
        localBuilder.setNegativeButton(R.string.notActivateGPS,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.cancel();
                        getActivity().finish();
                    }
                }
        );
        localBuilder.create().show();
    }

    /**
     * starts the activity to activate the location
     */
    private void showGpsOptions() {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    /**
     * check that everything is correctly filled
     * shows a toast if not
     * @return
     */
    private boolean formValidation() {
        if (txtTrackName.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.NameTrackFirst, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
