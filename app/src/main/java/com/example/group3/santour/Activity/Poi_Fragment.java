package com.example.group3.santour.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.example.group3.santour.DTO.POI;
import com.example.group3.santour.DTO.Position;
import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.Firebase.DataListener;
import com.example.group3.santour.Logic.Camera;
import com.example.group3.santour.Logic.Record;
import com.example.group3.santour.R;
import com.google.firebase.database.DatabaseError;

import java.util.Date;


public class Poi_Fragment extends Fragment {


    /*
    Variables declaration - Elements in view
    */
    private EditText edtxt_poiName;
    private TextView label_valuesGps;
    private ImageButton img_takePicture;
    private ImageView img_pictureView;
    private EditText edtxt_poiDescription;
    private Button btn_poiSave;

    //Record object for current location
    private Record record;

    //Track's object
    private Track track;
    private Position position;
    private Camera camera;
    private POI poi;

    //Fragment
    private FragmentManager fragmentManager;

    public Poi_Fragment() {
        poi = new POI();
        poi.setPicture("");
    }


    /*
    Methode Oncreate()
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poi, container, false);

        img_takePicture = (ImageButton) view.findViewById(R.id.imageButton);
        img_pictureView = (ImageView) view.findViewById(R.id.imageView);
        label_valuesGps = (TextView) view.findViewById(R.id.label_valuesGps);
        btn_poiSave = (Button) view.findViewById(R.id.btn_save);
        edtxt_poiName = (EditText) view.findViewById(R.id.input_NamePoi);
        //Need to change the ID in layount
        edtxt_poiDescription = (EditText) view.findViewById(R.id.input_descriptinPoi);


        //instantiate record
        record = new Record(getActivity());

        //get user current position for the POD
        record.getUserLatLng(new DataListener() {
            @Override
            public void onSuccess(Object object) {
                Location location = (Location) object;
                String latLng = "Longitude : " + location.getLatitude() + ", Latitude : " + location.getLongitude();
                label_valuesGps.setText(latLng);
                position = new Position(location.getLongitude(), location.getLatitude(), location.getAltitude(), new Date().toString());
            }

            @Override
            public void onFailed(DatabaseError dbError) {
            }
        });

        //on click for buttons
        img_takePicture.setOnClickListener(new TakePicture());
        btn_poiSave.setOnClickListener(new savePOI());


        return view;

    }

    private class savePOI implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            if (formValidation()) {
                //get the current track
                track = MainActivity.getTrack();

                //set the poi values
                poi.setName(edtxt_poiName.getText().toString());
                poi.setDescription(edtxt_poiDescription.getText().toString());
                poi.setPosition(position);

                //add to the pois list
                track.getPois().add(poi);

                MainActivity.setTrack(track);

                fragmentManager = getActivity().getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    FragmentManager.BackStackEntry first = fragmentManager
                            .getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
                    fragmentManager.popBackStack(first.getId(),
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

            }
        }
    }

    private class TakePicture implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            camera = new Camera();
            //camera.launchCamera(Poi_Fragment.this);
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Choice")
                    .setMessage("Camera or import from gallery")
                    .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            camera.setChoice("camera");
                            camera.launchCamera(Poi_Fragment.this);
                        }

                    })
                    .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            camera.setChoice("gallery");
                            camera.launchImportImage(Poi_Fragment.this);
                        }
                    })
                    .show();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {

            if (camera.getChoice() == "camera") {
                //get the bitmap from the intent

                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");

                //first add the image to the camera
                camera.addToImageViewCamera(requestCode, resultCode, bitmap, getActivity(), img_pictureView);
                //camera.dispatchTakePictureIntent(Poi_Fragment.this);

                //then encode the picture and add to the string
                poi.setPicture(camera.encodeBitmap(bitmap));
            } else {
                camera.addToImageViewGallery(requestCode, resultCode, getActivity(), img_pictureView, data);
                //then encode the picture and add to the string
                poi.setPicture(camera.encodeImageWithGallery());
            }

        }
    }

    //Change the id's
    private boolean formValidation() {
        if (edtxt_poiName.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.addNamePOI, Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtxt_poiDescription.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.addDescriptionPOI, Toast.LENGTH_SHORT).show();
            return false;
        } else if (poi.getPicture().equals("")) {
            Toast.makeText(getContext(), R.string.addPicturePOI, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
