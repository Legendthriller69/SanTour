package com.example.group3.santour.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.Logic.Permissions;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private BottomNavigationView navigation;

    final private int RECORDFRAGMENT = 1;
    final private int ABOUTFRAGMENT = 2;
    final private int SETTINGSFRAGMENT = 3;

    //Track that will be used everywhere
    private static Track track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle(this.getString(R.string.start_recording));

        //ask for permissions
        Permissions permissions = new Permissions();
        permissions.checkPermissions(this);

        //set up the navigation bar
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.inflateMenu(R.menu.navigation);



        //title of the navigationBar
        setTitle(this.getString(R.string.start_recording));

        //everything linked to the fragments

        fragmentManager = getSupportFragmentManager();

        int intentFragment = getIntent().getExtras().getInt("frgToLoad");

        switch(intentFragment){
            case RECORDFRAGMENT:
                fragment = new Record_Fragment();
                break;
            case ABOUTFRAGMENT:
                fragment = new About_Fragment();
                break;
            case SETTINGSFRAGMENT:
                fragment = new Settings_Fragment();
                break;
        }

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();



        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.navigation_about:
                        fragment = new About_Fragment();
                        break;
                    case R.id.navigation_tracks:
                        fragment = new Record_Fragment();
                        break;
                    case R.id.navigation_settings:
                        fragment = new Settings_Fragment();
                        break;
                }

                updateNavigationBarState(item.getItemId());
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.e("BACKSTACK ENTRY COUNT", fragmentManager.getBackStackEntryCount() + "");
        if(fragmentManager.getBackStackEntryCount()>0){
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    public static Track getTrack() {
        return MainActivity.track;
    }

    public static void setTrack(Track track) {
        MainActivity.track = track;
    }

    private void updateNavigationBarState(int tabId){
        Menu menu = navigation.getMenu();

        // check every tab and when the id of the current tab is the same as the one selected set this tab as selected
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == tabId);
        }
    }
}


