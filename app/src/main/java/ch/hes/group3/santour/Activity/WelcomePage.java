package ch.hes.group3.santour.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ch.hes.group3.santour.DTO.Role;
import ch.hes.group3.santour.DTO.Track;
import ch.hes.group3.santour.DTO.Type;
import ch.hes.group3.santour.Firebase.Authentication;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Firebase.RoleDB;
import ch.hes.group3.santour.Firebase.TypeDB;

public class WelcomePage extends AppCompatActivity {

    private Button btnRecord;
    private Button btnAbout;
    private Button btnSettings;
    private Button btnAllTracks;

    final private int RECORDFRAGMENT = 1;
    final private int ABOUTFRAGMENT = 2;
    final private int SETTINGSFRAGMENT = 3;
    final private int ALLTRACKSFRAGMENT = 4;

    private static List<Type> types;
    //List of tracks by user
    private static List<Track> tracks;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Authentication.logout(this);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
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
        btnAllTracks = (Button) findViewById(R.id.btnAllTracks);

        //get all types
        TypeDB.getAllTypes(new DataListener() {
            @Override
            public void onSuccess(Object object) {
                types = (ArrayList<Type>) object;
            }

            @Override
            public void onFailed(Object object) {

            }
        });

        if (Authentication.getCurrentRole() == null) {
            RoleDB.getRoleById(Authentication.getCurrentUser().getIdRole(), new DataListener() {
                @Override
                public void onSuccess(Object object) {
                    Role role = (Role) object;
                    if (role.getName().equals("user")) {
                        btnRecord.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onFailed(Object object) {
                }
            });
        } else {
            if (Authentication.getCurrentRole().getName().equals("user")) {
                btnRecord.setVisibility(View.GONE);
            }
        }

        btnRecord.setOnClickListener(new RecordTrackListener());
        btnAbout.setOnClickListener(new AboutPageListener());
        btnSettings.setOnClickListener(new SettingsPageListener());
        btnAllTracks.setOnClickListener(new MyTracksListener());
    }

    public static List<Track> getTracks() {
        return tracks;
    }

    public static void setTracks(List<Track> tracks) {
        WelcomePage.tracks = tracks;
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
            finish();
            Intent intent = new Intent(WelcomePage.this, MainActivity.class);
            intent.putExtra("frgToLoad", SETTINGSFRAGMENT);
            startActivity(intent);
        }
    }

    private class MyTracksListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WelcomePage.this, MainActivity.class);
            intent.putExtra("frgToLoad", ALLTRACKSFRAGMENT);
            startActivity(intent);
        }
    }

    public static List<Type> getTypes() {
        return types;
    }

    public static void setTypes(List<Type> types) {
        WelcomePage.types = types;
    }
}
