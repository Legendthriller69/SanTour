package ch.hes.group3.santour.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Locale;


public class Settings_Fragment extends Fragment {

    private ImageView frButton;
    private ImageView enButton;
    private ImageView deButton;

    public Settings_Fragment() {
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation_top, menu);
    }

    /**
     * options item selected
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getActivity().finish();
                break;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //options menu
        setHasOptionsMenu(true);

        //initialize elements
        frButton = (ImageView) view.findViewById(R.id.imView_FrFlag);
        enButton = (ImageView) view.findViewById(R.id.imView_EnFlag);
        deButton = (ImageView) view.findViewById(R.id.imView_DeFlag);

        //listeners on buttons
        frButton.setOnClickListener(new FrListener());
        enButton.setOnClickListener(new EnListener());
        deButton.setOnClickListener(new DeListener());

        return view;
    }

    private class FrListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            changeToFR(view);
        }
    }

    private class EnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            changeToEN(view);
        }
    }

    private class DeListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            changeToDE(view);
        }
    }

    /**
     * change the language to french
     * @param v
     */
    public void changeToFR(View v) {
        String languageToLoad = "fr";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, v.getResources().getDisplayMetrics());

        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("LANGUAGE", languageToLoad).commit();
        getActivity().finish();
        WelcomePage.getActivity().finish();
        Intent myIntent = new Intent(v.getContext(), WelcomePage.class);
        startActivity(myIntent);
    }

    /**
     * change the language to deutsch
     * @param v
     */
    public void changeToDE(View v) {
        Log.e("je change la langue", "je change la langue");
        String languageToLoad = "de";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, v.getResources().getDisplayMetrics());

        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("LANGUAGE", languageToLoad).commit();
        getActivity().finish();
        WelcomePage.getActivity().finish();
        Intent myIntent = new Intent(v.getContext(), WelcomePage.class);
        startActivity(myIntent);
    }

    /**
     * change the language to english
     * @param v
     */
    public void changeToEN(View v) {
        String languageToLoad = "en-rGB";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, v.getResources().getDisplayMetrics());

        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("LANGUAGE", languageToLoad).commit();
        getActivity().finish();
        WelcomePage.getActivity().finish();
        Intent myIntent = new Intent(v.getContext(), WelcomePage.class);
        startActivity(myIntent);
    }

}
