package com.example.group3.santour.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.Firebase.Authentication;
import com.example.group3.santour.Firebase.DataListener;
import com.example.group3.santour.Firebase.TrackDB;

import java.util.List;

public class MyTracksList extends Fragment {


    public MyTracksList() {

    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation_top, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getActivity().finish();
                break;
        }
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_tracks_list, container, false);

        //options menu
        setHasOptionsMenu(true);

        TrackDB.getAllTracksByIdUser(Authentication.getCurrentUser().getId(), new DataListener() {
            @Override
            public void onSuccess(Object object) {
                List<Track> tracks = (List<Track>) object;
                for (int i = 0; i < tracks.size(); i++) {
                    Log.e("track", tracks.get(i).getName());
                }
            }

            @Override
            public void onFailed(Object object) {

            }
        });

        return view;
    }


}
