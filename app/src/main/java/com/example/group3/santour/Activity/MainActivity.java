package com.example.group3.santour.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.group3.santour.DTO.Position;
import com.example.group3.santour.Logic.Permissions;
import com.example.group3.santour.Logic.Record;
import com.example.group3.santour.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap mMap;
    private Record record;

    private Button btnStart;
    private Button btnPause;
    private Button btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create the map view
        mapView = (MapView) findViewById(R.id.mapView3);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //create the buttons
        btnStart = (Button) findViewById(R.id.btnStart);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnStop = (Button) findViewById(R.id.btnStop);

        //first ask for the permissions
        Permissions permissions = new Permissions();
        permissions.checkPermissions(MainActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(15.0f);

        // Add a marker in Sydney, Australia, and move the camera.
        record = new Record(MainActivity.this, mMap);
        record.setUserCurrentPosition();


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record.startRecording();
                Log.e("START", "Start recording");
                Log.e("STOP", "stop recording");
                Log.e("Positions size", record.getPositions().size() + "");
                for (Position p : record.getPositions()){
                    Log.e("Positions", p + "");
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record.pauseLocationUpdates();
                Log.e("PAUSE", "pause recording");
                Log.e("Positions size", record.getPositions().size() + "");
                for (Position p : record.getPositions()){
                    Log.e("Positions", p + "");
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record.stopLocationUpdates();
                Log.e("STOP", "stop recording");
                Log.e("Positions size", record.getPositions().size() + "");
                for (Position p : record.getPositions()){
                    Log.e("Positions", p + "");
                }
            }
        });
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
    public void onBackPressed() {
        if (record != null && record.isRecording()) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.application_close)
                    .setMessage(R.string.track_lost_message)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            finish();
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}


