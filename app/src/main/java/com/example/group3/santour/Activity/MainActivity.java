package com.example.group3.santour.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.Logic.Permissions;
import com.example.group3.santour.R;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private BottomNavigationView navigation;

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

        navigation.setOnNavigationItemSelectedListener(new BottomNavigation());

        //title of the navigationBar
        setTitle(this.getString(R.string.start_recording));

        //everything linked to the fragments
        fragment = new Record_Fragment();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        Log.e("COUNT MAIN ACTIVITY", fragmentManager.getBackStackEntryCount() + "");

    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public static Track getTrack() {
        return MainActivity.track;
    }

    public static void setTrack(Track track) {
        MainActivity.track = track;
    }

    private class BottomNavigation implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_about:
                    fragment = new Pod_Fragment(); // A CHANGER
                    break;
                case R.id.navigation_tracks:
                    fragment = new Poi_Fragment(); // A CHANGER
                    break;
                case R.id.navigation_settings:
                    fragment = new Record_Fragment();
                    break;
            }
            return true;
        }
    }
}


