package com.example.group3.santour.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class DetailsExistingTracks extends Fragment {

    private Button listPOIBtn;
    private Button listPODBtn;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public DetailsExistingTracks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_existing_tracks, container, false);


        listPODBtn = (Button) view.findViewById(R.id.btnListPOD);
        listPOIBtn = (Button) view.findViewById(R.id.btnListPOI);

        listPOIBtn.setOnClickListener(new ListPOIListener());
        listPODBtn.setOnClickListener(new ListPODListener());


        return view;
    }

    private class ListPOIListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (MainActivity.getTrack() != null && MainActivity.getTrack().getPois().size() > 0) {
                fragment = new ListPOIs();
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);
            } else {
                Toast.makeText(getContext(), R.string.noPOI, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ListPODListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (MainActivity.getTrack() != null && MainActivity.getTrack().getPods().size() > 0) {
                fragment = new ListPODs();
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);
            } else {
                Toast.makeText(getContext(), R.string.noPOD, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
