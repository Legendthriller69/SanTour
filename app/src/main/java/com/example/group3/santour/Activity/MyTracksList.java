package com.example.group3.santour.Activity;

import android.content.Context;
import android.net.Uri;
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
import android.widget.ListView;

import com.example.group3.santour.Adapter.AdapterMyTrackList;
import com.example.group3.santour.DTO.Track;

import java.util.List;

public class MyTracksList extends Fragment {

    //fragment
    private Fragment fragment;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Bundle bundle;

    //adapter
    private AdapterMyTrackList adapterMyTrackList;
    private ListView mListView;
    private List<Track> tracks;


    public MyTracksList() {
        // Required empty public constructor
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

        // init elements
        mListView = (ListView) view.findViewById(R.id.myTrackListView);

        //create the adapter
        tracks.add(MainActivity.getTrack()); // méthode a changer pour avoir la list de toutes les tracks
        adapterMyTrackList = new AdapterMyTrackList(getContext(), tracks);
        mListView.setAdapter(adapterMyTrackList);

        return view;
    }


}
