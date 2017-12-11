package com.example.group3.santour.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class WelcomePage extends AppCompatActivity {

    private Button btnRecord;
    private Button btnAbout;
    private Button btnSettings;

    final private int RECORDFRAGMENT = 1;
    final private int ABOUTFRAGMENT = 2;
    final private int SETTINGSFRAGMENT = 3;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout:

                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        setTitle(getString(R.string.Home));


        btnRecord = (Button) findViewById(R.id.btnCreateTrack);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnSettings = (Button) findViewById(R.id.btnSettings);

        btnRecord.setOnClickListener(new RecordTrackListener());
        btnAbout.setOnClickListener(new AboutPageListener());
        btnSettings.setOnClickListener(new SettingsPageListener());
    }


    private class RecordTrackListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WelcomePage.this, MainActivity.class);
            intent.putExtra("frgToLoad", RECORDFRAGMENT);
            startActivity(intent);
        }
    }

    private class AboutPageListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WelcomePage.this, MainActivity.class);
            intent.putExtra("frgToLoad", ABOUTFRAGMENT);
            startActivity(intent);
        }
    }

    private class SettingsPageListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WelcomePage.this, MainActivity.class);
            intent.putExtra("frgToLoad", SETTINGSFRAGMENT);
            startActivity(intent);
        }
    }
}
