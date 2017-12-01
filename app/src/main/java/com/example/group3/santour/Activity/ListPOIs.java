package com.example.group3.santour.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.group3.santour.R;


public class ListPOIs extends Fragment {

    private Fragment fragment;
    private FragmentManager fragmentManager;

    public ListPOIs() {
        // Required empty public constructor
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.back_record_menu, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
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


        setHasOptionsMenu(true);

        fragmentManager = getActivity().getSupportFragmentManager();





        return view;
    }

}
