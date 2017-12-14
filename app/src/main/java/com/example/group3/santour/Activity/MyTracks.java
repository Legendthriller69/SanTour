package com.example.group3.santour.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.group3.santour.Adapter.AdapterMyTrackList;
import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.Firebase.Authentication;
import com.example.group3.santour.Firebase.DataListener;
import com.example.group3.santour.Firebase.TrackDB;

import java.util.List;

public class MyTracks extends Fragment {

    private AdapterMyTrackList adapterTrack;
    private ListView mListView;

    public MyTracks() {

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

        mListView = (ListView) view.findViewById(R.id.myTrackListView);

        TrackDB.getAllTracksByIdUser(Authentication.getCurrentUser().getId(), new DataListener() {
            @Override
            public void onSuccess(Object object) {
                List<Track> tracks = (List<Track>) object;
                for (int i = 0; i < tracks.size(); i++) {
                    Log.e("track", tracks.get(i).getName());
                }
                Log.e("tracks size", tracks.size() + "");
                adapterTrack = new AdapterMyTrackList(getContext(), tracks);
                mListView.setAdapter(adapterTrack);
            }

            @Override
            public void onFailed(Object object) {

            }
        });

        return view;
    }


}
