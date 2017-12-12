package com.example.group3.santour.Activity;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.Logic.Record;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

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

    //Record object
    private Record record;

    //hello
    //Maps objects
    private MapView mapView;
    private GoogleMap mMap;
    private Long timeWhenPause;
    private LocationManager locationManager;

    //fragments
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;



    public Record_Fragment() {
        timeWhenPause = Long.valueOf(0);
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
                    fragment = new ListPODs();
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
                    fragment = new ListPOIs();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment).commit();
                    transaction.addToBackStack(null);
                    break;
                } else {
                    Toast.makeText(getContext(), R.string.CreatePOIFirst, Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.home:
                if (record != null && !record.isRecording()) {
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

        //options menu
        setHasOptionsMenu(true);

        //instantiate map view
        mapView = (MapView) view.findViewById(R.id.Map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (record == null) {
            record = new Record(getActivity(), mMap, txtDistance);
        }

        record.moveCameraToUserPosition();
    }

    private class AddPOD implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            btnPause.callOnClick();
            fragment = new Pod_Fragment();
            fragmentManager = getActivity().getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private class AddPOI implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            btnPause.callOnClick();
            fragment = new Poi_Fragment();
            fragmentManager = getActivity().getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.main_container, fragment).commit();
        }
    }

    private class StartRecording implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //create a new track in the main activity now that he has started the track
            if (MainActivity.getTrack() == null) {
                MainActivity.setTrack(new Track());
            }

            //call the method to start recording
            record.startRecording();

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

    private class PauseRecording implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            record.pauseLocationUpdates();
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

    private class StopRecording implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (formValidation()) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.save_track_confirmation_title)
                        .setMessage(R.string.save_track_confirmation_text)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                record.createTrack(txtTrackName.getText().toString(), "DESCRIPTION A FAIRE", (int) ((SystemClock.elapsedRealtime() - chrono.getBase()) / 1000), "idTypeAFAIRE", "idStringAFAIRE");
                                Toast.makeText(getActivity(), R.string.trackSaved, Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
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

        if (record != null) {
            txtDistance.setText(record.getDistanceText());
            btnStart.callOnClick();
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

    public void onBackPressed() {
        if (record != null && record.isRecording()) {
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.application_close)
                    .setMessage(R.string.track_lost_message)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            getActivity().finish();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void createGpsDisabledAlert() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
        localBuilder
                .setMessage("Le GPS est inactif, voulez-vous l'activer ?")
                .setCancelable(false)
                .setPositiveButton("Activer GPS ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                Record_Fragment.this.showGpsOptions();
                            }
                        }
                );
        localBuilder.setNegativeButton("Ne pas l'activer ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.cancel();
                        getActivity().finish();
                    }
                }
        );
        localBuilder.create().show();
    }

    private void showGpsOptions() {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    private boolean formValidation() {
        if (txtTrackName.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.NameTrackFirst, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
