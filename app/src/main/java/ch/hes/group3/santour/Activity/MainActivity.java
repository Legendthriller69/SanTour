package ch.hes.group3.santour.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import ch.hes.group3.santour.DTO.Track;
import ch.hes.group3.santour.Logic.Record;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    final private int RECORDFRAGMENT = 1;
    final private int ABOUTFRAGMENT = 2;
    final private int SETTINGSFRAGMENT = 3;
    final private int ALLTRACKSFRAGMENT = 4;

    //Track that will be used everywhere
    private static Track track;

    private static boolean isInRecordFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle(this.getString(R.string.start_recording));

        //title of the navigationBar
        setTitle(this.getString(R.string.start_recording));

        //everything linked to the fragments
        fragmentManager = getSupportFragmentManager();

        int intentFragment = getIntent().getExtras().getInt("frgToLoad");

        switch (intentFragment) {
            case RECORDFRAGMENT:
                track = null;
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
            case ALLTRACKSFRAGMENT:
                fragment = new MyTracks();
                setTitle(getString(R.string.myTracks));
                break;
        }
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if (!Record.isRecording())
            if (!DetailsExistingTracks.isInDetails()) {
                Record.destroy();
                finish();
                return;
            }

        if (Record.isRecording()) {
            if (isInRecordFragment) {
                Log.e("record fragment", "je suis dans le record fragment");
                Toast.makeText(this, R.string.saveTrackFirst, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            finish();
            Record.destroy();
            fragmentManager.popBackStack();
        }
    }

    public static Track getTrack() {
        return MainActivity.track;
    }

    public static void setTrack(Track track) {
        MainActivity.track = track;
    }

    public static void setIsInRecordFragment(boolean isInRecordFragment) {
        MainActivity.isInRecordFragment = isInRecordFragment;
    }
}


