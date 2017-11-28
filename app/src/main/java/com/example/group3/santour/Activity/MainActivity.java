package com.example.group3.santour.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.group3.santour.Logic.Permissions;
import com.example.group3.santour.R;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle(this.getString(R.string.start_recording));

        //ask for permissions
        Permissions permissions = new Permissions();
        permissions.checkPermissions(this);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.inflateMenu(R.menu.navigation);

        fragmentManager = getSupportFragmentManager();

        this.setTitle(this.getString(R.string.start_recording));



        fragment = new Record_Fragment();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
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

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount()>0){
            fragmentManager.popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
}


