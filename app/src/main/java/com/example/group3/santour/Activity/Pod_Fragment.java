package com.example.group3.santour.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group3.santour.DTO.POD;
import com.example.group3.santour.DTO.Position;
import com.example.group3.santour.Firebase.DataListener;
import com.example.group3.santour.Logic.Camera;
import com.example.group3.santour.Logic.Record;
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

    //ScrollView
    private ScrollView scrollView;

    //fragments
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Bundle bundle;
    private boolean update;
    private int index;

    //picture elements
    private int requestCode;
    private int resultCode;
    private Bitmap bitmap;
    private Intent data;


    public Pod_Fragment() {
        pod = new POD();
        pod.setPicture("");
        update = false;
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

        if (getArguments() != null) {
            update = true;
            //get the bundle
            bundle = getArguments();
            pod = (POD) bundle.getSerializable("POD");
            index = Integer.parseInt(bundle.getString("index"));

            //create the camera
            camera = new Camera();

            //init the gui
            initGUI(pod);
        } else {
            Toast.makeText(getActivity(), "SANS BUNDLE", Toast.LENGTH_SHORT).show();
            //instantiate record
            record = new Record(getActivity());

            //get user current position for the POD
            record.getUserLatLng(new DataListener() {
                @Override
                public void onSuccess(Object object) {
                    Location location = (Location) object;
                    String latLng = "Longitude : " + location.getLongitude() + ", Latitude : " + location.getLatitude();
                    txtLatLng.setText(latLng);
                    position = new Position(location.getLongitude(), location.getLatitude(), location.getAltitude(), new Date().toString());
                }

                @Override
                public void onFailed(DatabaseError dbError) {
                }
            });
        }

        //on click for buttons
        btnNext.setOnClickListener(new NextPOD());
        btnTakePicture.setOnClickListener(new TakePicture());

        return view;
    }

    private class NextPOD implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (formValidation()) {
                //set the pod values
                pod.setName(txtName.getText().toString());
                pod.setDescription(txtDescription.getText().toString());
                if (pod.getPosition() == null) {
                    pod.setPosition(position);
                }

                //create new bundle to put the pod object
                bundle = new Bundle();
                bundle.putSerializable("POD", pod);
                if (update)
                    bundle.putString("index", String.valueOf(index));

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
            if (camera == null)
                camera = new Camera();
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.choice_picture_title)
                    .setMessage(R.string.choice_picture_message)
                    .setPositiveButton(R.string.choice_picture_camera, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            camera.setChoice("camera");
                            camera.launchCamera(Pod_Fragment.this);
                        }
                    })
                    .setNegativeButton(R.string.choice_picture_gallery, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            camera.setChoice("gallery");
                            camera.launchImportImage(Pod_Fragment.this);
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            this.data = data;
            if (camera.getChoice() == "camera") {
                //get the bitmap from the intent
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");

                //first add the image to the camera
                camera.addToImageViewCamera(requestCode, resultCode, bitmap, getActivity(), pictureView);

                //then encode the picture and add to the string
                pod.setPicture(camera.encodeBitmap(bitmap));
            } else {
                camera.addToImageViewGallery(requestCode, resultCode, getActivity(), pictureView, data);
                //then encode the picture and add to the string
                pod.setPicture(camera.encodeImageWithGallery());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.e("BITMAP", (bitmap != null) + "");
//        if (bitmap != null) {
//            if (camera.getChoice() == "camera") {
//                Log.e("CHOICE", camera.getChoice());
//                camera.addToImageViewCamera(requestCode, resultCode, bitmap, getActivity(), pictureView);
//            } else {
//                Log.e("CHOICE", camera.getChoice());
//                camera.addToImageViewGallery(requestCode, resultCode, getActivity(), pictureView, data);
//            }
//        }
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

    private void initGUI(POD pod) {
        txtName.setText(pod.getName());
        txtDescription.setText(pod.getDescription());
        String latLng = "Longitude : " + pod.getPosition().getLongitude() + ", Latitude : " + pod.getPosition().getLatitude();
        txtLatLng.setText(latLng);
        camera.decodeB64Bitmap(pod.getPicture(), pictureView);
    }
}