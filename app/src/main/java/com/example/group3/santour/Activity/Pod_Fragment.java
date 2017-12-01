package com.example.group3.santour.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.group3.santour.DTO.POD;
import com.example.group3.santour.DTO.Position;
import com.example.group3.santour.Firebase.DataListener;
import com.example.group3.santour.Logic.Camera;
import com.example.group3.santour.Logic.Record;
import com.example.group3.santour.R;
import com.google.firebase.database.DatabaseError;

import java.util.Date;


public class Pod_Fragment extends Fragment {

    //elements
    private EditText txtName;
    private ImageButton btnTakePicture;
    private ImageView pictureView;
    private EditText txtDescription;
    private Button btnNext;
    private TextView txtLatLng;
    private Button btn_podNext;

    //Record object for current location
    private Record record;

    //Track's objects
    private POD pod;
    private Position position;
    private Camera camera;

    //ScrollView
    private ScrollView scrollView;

    //fragments
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Bundle bundle;


    public Pod_Fragment() {
        pod = new POD();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pod, container, false);

        //instantiate elements
        btnNext = (Button) view.findViewById(R.id.btn_next);
        txtName = (EditText) view.findViewById(R.id.input_NamePod);
        txtDescription = (EditText) view.findViewById(R.id.input_descriptionPod);
        btnTakePicture = (ImageButton) view.findViewById(R.id.imageButton);
        pictureView = (ImageView) view.findViewById(R.id.imageView);
        txtLatLng = (TextView) view.findViewById(R.id.label_valuesGps);
        btn_podNext=(Button) view.findViewById(R.id.btn_next);

        //instantiate record
        record = new Record(getActivity());

        //get user current position for the POD
        record.getUserLatLng(new DataListener() {
            @Override
            public void onSuccess(Object object) {
                Location location = (Location) object;
                String latLng = "Longitude : " + location.getLatitude() + ", Latitude : " + location.getLongitude();
                txtLatLng.setText(latLng);
                position = new Position(location.getLongitude(), location.getLatitude(), location.getAltitude(), new Date().toString());
            }

            @Override
            public void onFailed(DatabaseError dbError) {
            }
        });

        //on click for buttons
      //  btnNext.setOnClickListener(new NextPOD());
        btnTakePicture.setOnClickListener(new TakePicture());

        return view;
    }

    private class NextPOD implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //set the pod values
            pod.setName(txtName.getText().toString());
            pod.setDescription(txtDescription.getText().toString());
            pod.setPosition(position);


            //create new bundle to put the pod object
            bundle = new Bundle();
            bundle.putSerializable("POD", pod);

            //create the fragment and add the bundle to the arguments
            fragment = new Pod_Details_Fragment();
            fragment.setArguments(bundle);

            //switch to the new fragment with transaction
            fragmentManager = getActivity().getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            Log.e("COUNT pod fragment", fragmentManager.getBackStackEntryCount() + "");

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

        }
    }

    private class TakePicture implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            camera = new Camera();
            camera.launchCamera(Pod_Fragment.this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //get the bitmap from the intent
        Bundle extras = data.getExtras();
        Bitmap bitmap = (Bitmap) extras.get("data");

        //first add the image to the camera
        camera.addToImageView(requestCode, resultCode, bitmap, getActivity(), pictureView);

        //then encode the picture and add to the string
        pod.setPicture(camera.encodeBitmap(bitmap));
    }
}
