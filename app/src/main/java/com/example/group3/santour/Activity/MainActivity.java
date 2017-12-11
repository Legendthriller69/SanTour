package com.example.group3.santour.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.Logic.Permissions;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

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


        //title of the navigationBar
        setTitle(this.getString(R.string.start_recording));

        //everything linked to the fragments
        fragmentManager = getSupportFragmentManager();

        int intentFragment = getIntent().getExtras().getInt("frgToLoad");

        switch (intentFragment) {
            case RECORDFRAGMENT:
                fragment = new Record_Fragment();
                setTitle(getString(R.string.Recording));
                break;
            case ABOUTFRAGMENT:
                fragment = new About_Fragment();
                setTitle(getString(R.string.about));
                break;
            case SETTINGSFRAGMENT:
                fragment = new Settings_Fragment();
                setTitle(getString(R.string.settings));
                break;
        }
        transaction = fragmentManager.beginTransaction();
        Log.e("FRAGMENT", "" + fragmentManager.getBackStackEntryCount());
        Log.e("FRAGMENT", "" + fragmentManager.getBackStackEntryCount());
        transaction.replace(R.id.main_container, fragment).commit();
        Log.e("FRAGMENT", "" + fragmentManager.getBackStackEntryCount());
    }

    @Override
    public void onBackPressed() {
        Log.e("FRAGMENT", "" + fragmentManager.getBackStackEntryCount());
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            }
            else {
                finish();
            }
    }

    public static Track getTrack() {
        return MainActivity.track;
    }

    public static void setTrack(Track track) {
        MainActivity.track = track;
    }

}


