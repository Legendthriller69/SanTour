package com.example.group3.santour.Activity;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.group3.santour.R;


public class Poi_Fragment extends Fragment {

    /*
    Constructor
     */
    public Poi_Fragment() {
        // Required empty public constructor
    }

    /*
    Variables declaration
    */
    private EditText edtxt_poiName;
    private String label_valuesGps;
    private ImageButton img_takePicture;
    private Image img_pictureView;
    private EditText edtxt_poiDescription;
    private Button btn_poiSave;



    /*
    Methode Oncreate()
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poi, container, false);
    }




}
