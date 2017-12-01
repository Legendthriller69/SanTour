package com.example.group3.santour.Activity;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.group3.santour.R;


public class Pod_Fragment extends Fragment {

    public Pod_Fragment() {
        // Required empty public constructor
    }


    /*
    Variables declaration
    */
    private EditText edtxt_podName;
    private String label_valuesGps;
    private ImageButton img_takePicture;
    private Image img_pictureView;
    private EditText edtxt_podDescription;
    private Button btn_podNext;


    private Fragment fragment;
    private FragmentManager fragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_pod, container, false);
        btn_podNext=(Button) view.findViewById(R.id.btn_next);


//        btn_podNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(getContext(), "Btn pushed", Toast.LENGTH_SHORT).show();
//                fragment = new Pod_Details_Frgament();
//                fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.main_container, fragment).commit();
//                transaction.addToBackStack(null);
//            }
//        });

        return view;
    }




}
