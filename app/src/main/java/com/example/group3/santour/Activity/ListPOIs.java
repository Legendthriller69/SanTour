package com.example.group3.santour.Activity;

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
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.group3.santour.Adapter.AdapterPOIList;
import com.example.group3.santour.DTO.POI;

import java.util.List;


public class ListPOIs extends Fragment {

    //fragment
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Bundle bundle;

    //adapter
    private AdapterPOIList adapterPOIList;
    private ListView mListView;
    private List<POI> pois;

    public ListPOIs() {
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.back_record_menu, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_record:
                fragmentManager.popBackStack();
                break;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_pois, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        //options menu
        setHasOptionsMenu(true);

        //init elements
        mListView = (ListView) view.findViewById(R.id.listViewPOI);

        //create adapter
        pois = MainActivity.getTrack().getPois();
        adapterPOIList = new AdapterPOIList(getContext(), pois);
        mListView.setAdapter(adapterPOIList);
        mListView.setOnItemClickListener(new ItemClickPOI());

        return view;
    }

    private class ItemClickPOI implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //create new bundle to put the poi object
            bundle = new Bundle();
            bundle.putSerializable("POI", pois.get(i));
            bundle.putString("index", String.valueOf(i));

            //create the fragment and add the bundle to the arguments
            fragment = new Poi_Fragment();
            fragment.setArguments(bundle);

            //switch to the new fragment
            transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.main_container, fragment).commit();

        }
    }

}
