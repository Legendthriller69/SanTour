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
import android.widget.TextView;
import android.widget.Toast;

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

    //Record object for current location
    private Record record;

    //Track's objects
    private POD pod;
    private Position position;
    private Camera camera;

    //fragments
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Bundle bundle;

    //picture elements
    private int requestCode;
    private int resultCode;
    private Bitmap bitmap;


    public Pod_Fragment() {
        pod = new POD();
        pod.setPicture("");
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
        btnNext.setOnClickListener(new NextPOD());
        btnTakePicture.setOnClickListener(new TakePicture());

        return view;
    }

    private class NextPOD implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Log.e("FORM VALIDATION", formValidation() + "");
            if (formValidation()) {
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
            }
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
        if (data != null) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");

            //first add the image to the camera
            camera.addToImageView(requestCode, resultCode, bitmap, getActivity(), pictureView);

            //then encode the picture and add to the string
            pod.setPicture(camera.encodeBitmap(bitmap));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bitmap != null) {
            txtName.setText(pod.getName());
            txtDescription.setText(pod.getDescription());
            camera.addToImageViewCamera(requestCode, resultCode, bitmap, getActivity(), pictureView);
        }
    }

    private boolean formValidation() {
        if (txtName.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.addNamePOD, Toast.LENGTH_SHORT).show();
            return false;
        } else if (txtDescription.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.addDescriptionPOD, Toast.LENGTH_SHORT).show();
            return false;
        } else if (pod.getPicture().equals("")) {
            Toast.makeText(getContext(), R.string.addPicturePOD, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
