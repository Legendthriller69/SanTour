package com.example.group3.santour.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.group3.santour.Logic.Record;
import com.example.group3.santour.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class Record_Fragment extends Fragment implements OnMapReadyCallback {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //buttons
    private Button btnStart;
    private Button btnPause;
    private Button btnStop;
    private Button btnAddPoi;
    private Button btnAddPod;

    //Record object
    private Record record;

    //Google map object
    private GoogleMap mMap;

    private OnFragmentInteractionListener mListener;

public class Record_Fragment extends Fragment {

    public Record_Fragment() {
        // Required empty public constructor
    }

    public static Record_Fragment newInstance(String param1, String param2) {
        Record_Fragment fragment = new Record_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        //get all buttons
        btnStart = (Button) view.findViewById(R.id.ButtonPlay);
        btnPause = (Button) view.findViewById(R.id.ButtonPause);
        btnStop = (Button) view.findViewById(R.id.ButtonSave);
        btnAddPod = (Button) view.findViewById(R.id.ButtonAddPOD);
        btnAddPoi = (Button) view.findViewById(R.id.ButtonAddPOI);

        //disable btnpause and stop
        btnPause.setEnabled(false);
        btnStop.setEnabled(false);

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(15.0f);

        //create the record object
        record = new Record(getActivity(), mMap);
        record.setUserCurrentPosition();

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class StartRecording implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            record.startRecording();
            btnStart.setEnabled(false);
            btnPause.setEnabled(true);
            btnStop.setEnabled(true);

        }
    }

    private class PauseRecording implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            record.pauseLocationUpdates();
            btnStart.setEnabled(true);
            btnPause.setEnabled(false);
            btnStop.setEnabled(false);
        }
    }

    private class StopRecording implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.application_close)
                    .setMessage(R.string.track_lost_message)
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
}
