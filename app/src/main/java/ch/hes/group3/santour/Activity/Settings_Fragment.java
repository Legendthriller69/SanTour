package ch.hes.group3.santour.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        // Required empty public constructor
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation_top, menu);
    }

    //Handle button activities
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


        //TODO: ONCLICKLISTENER FOR CHANGING LANGUAGE

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


    public void changeToFR(View v){
        String languageToLoad="fr";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale=locale;

        getResources().updateConfiguration(config, v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    public void changeToDE(View v){
        String languageToLoad="de";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale=locale;
        getResources().updateConfiguration(config, v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    public void changeToEN(View v){
        String languageToLoad="en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale=locale;
        getResources().updateConfiguration(config, v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }

}
