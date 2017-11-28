package com.example.group3.santour.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group3.santour.DTO.Position;
import com.example.group3.santour.Logic.Record;
import com.example.group3.santour.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

public class Record_Fragment extends Fragment implements OnMapReadyCallback {

    //Hello


    //elements
    private ImageButton btnStart;
    private ImageButton btnPause;
    private ImageButton btnStop;
    private ImageButton btnAddPoi;
    private ImageButton btnAddPod;
    private TextView txtDistance;
    private Chronometer chrono;

    //Record object
    private Record record;

    //Google map object
    private MapView mapView;
    private GoogleMap mMap;
    private Long timeWhenPause;

    private Fragment fragment;
    private FragmentManager fragmentManager;

    public Record_Fragment() {
        timeWhenPause = Long.valueOf(0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        //instantiate map view
        mapView = (MapView) view.findViewById(R.id.Map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //instantiate all the elements
        btnStart = (ImageButton) view.findViewById(R.id.ButtonPlay);
        btnPause = (ImageButton) view.findViewById(R.id.ButtonPause);
        btnStop = (ImageButton) view.findViewById(R.id.ButtonSave);
        btnAddPod = (ImageButton) view.findViewById(R.id.ButtonAddPOD);
        btnAddPoi = (ImageButton) view.findViewById(R.id.ButtonAddPOI);
        txtDistance = (TextView) view.findViewById(R.id.txtDistance);
        chrono = (Chronometer) view.findViewById(R.id.chrono);

        //disable btnPause and btnStop
        btnPause.setEnabled(false);
        btnStop.setEnabled(false);


        //navigation button to pod
        btnAddPod=(ImageButton) view.findViewById(R.id.ButtonAddPOD);
        btnAddPod.setOnClickListener(new AddPOD());

        //navigation button to poi
        btnAddPoi=(ImageButton) view.findViewById(R.id.ButtonAddPOI);
        btnAddPoi.setOnClickListener(new AddPOI());

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(15.0f);

        //add buttons listeners
        btnStart.setOnClickListener(new StartRecording());
        btnPause.setOnClickListener(new PauseRecording());
        btnStop.setOnClickListener(new StopRecording());

        //create the record object
        record = new Record(getActivity(), mMap, txtDistance);
        record.setUserCurrentPosition();

    }

    private class AddPOD implements  View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(), "Btn add POd pushed", Toast.LENGTH_SHORT).show();
            fragment = new Pod_Fragment();
            fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.main_container, fragment).commit();
        }
    }

    private class AddPOI implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(), "Btn add POI pushed", Toast.LENGTH_SHORT).show();
            fragment = new Poi_Fragment();
            fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.main_container, fragment).commit();
        }
    }

    private class StartRecording implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            record.startRecording();
            btnStart.setEnabled(false);
            btnPause.setEnabled(true);
            btnStop.setEnabled(true);
            chrono.setBase(SystemClock.elapsedRealtime() + timeWhenPause);
            chrono.start();
        }
    }

    private class PauseRecording implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            record.pauseLocationUpdates();
            btnStart.setEnabled(true);
            btnPause.setEnabled(false);
            btnStop.setEnabled(false);
            timeWhenPause = chrono.getBase() - SystemClock.elapsedRealtime();
            chrono.stop();
        }
    }

    private class StopRecording implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.save_track_confirmation_title)
                    .setMessage(R.string.save_track_confirmation_text)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            record.stopLocationUpdates();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
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

}
