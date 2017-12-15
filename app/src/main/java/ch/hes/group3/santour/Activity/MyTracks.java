package ch.hes.group3.santour.Activity;

import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.ListView;

import ch.hes.group3.santour.Adapter.AdapterMyTrackList;
import ch.hes.group3.santour.DTO.Track;
import ch.hes.group3.santour.Firebase.Authentication;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Firebase.TrackDB;

import java.util.List;

public class MyTracks extends Fragment {

    private AdapterMyTrackList adapterTrack;
    private ListView mListView;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public MyTracks() {
        DetailsExistingTracks.setInDetails(false);
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

        fragmentManager = getActivity().getSupportFragmentManager();
        //options menu
        setHasOptionsMenu(true);

        mListView = (ListView) view.findViewById(R.id.myTrackListView);

        TrackDB.getAllTracksByIdUser(Authentication.getCurrentUser().getId(), new DataListener() {
            @Override
            public void onSuccess(Object object) {
                List<Track> tracks = (List<Track>) object;
                WelcomePage.setTracks(tracks);
                for (int i = 0; i < tracks.size(); i++) {
                    Log.e("track", tracks.get(i).getName());
                }
                Log.e("tracks size", tracks.size() + "");
                adapterTrack = new AdapterMyTrackList(getContext(), tracks);
                mListView.setAdapter(adapterTrack);
                mListView.setOnItemClickListener(new ItemClickTrack());
            }

            @Override
            public void onFailed(Object object) {

            }
        });

        return view;
    }

    private class ItemClickTrack implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Bundle bundle = new Bundle();
            bundle.putInt("index", i);

            //create the fragment and add the bundle to the arguments
            fragment = new DetailsExistingTracks();
            fragment.setArguments(bundle);

            //switch to the new fragment
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();

        }
    }


}
