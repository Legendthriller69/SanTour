package com.example.group3.santour.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.group3.santour.DTO.POI;
import com.example.group3.santour.DTO.Position;
import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.Firebase.DataListener;
import com.example.group3.santour.Logic.Camera;
import com.example.group3.santour.Logic.Record;

import java.util.Date;


public class Poi_Fragment extends Fragment {

    //elements
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

    //fragments
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Bundle bundle;
    private boolean update;
    private int index;

    public Poi_Fragment() {
        poi = new POI();
        poi.setPicture("");
        update = false;
        index = -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poi, container, false);

        //init elements
        img_takePicture = (ImageButton) view.findViewById(R.id.imageButton);
        img_pictureView = (ImageView) view.findViewById(R.id.imageView);
        label_valuesGps = (TextView) view.findViewById(R.id.label_valuesGps);
        btn_poiSave = (Button) view.findViewById(R.id.btn_save);
        edtxt_poiName = (EditText) view.findViewById(R.id.input_NamePoi);
        edtxt_poiDescription = (EditText) view.findViewById(R.id.input_descriptinPoi);

        if (getArguments() != null) {
            update = true;
            //get the bundle
            bundle = getArguments();
            poi = (POI) bundle.getSerializable("POI");
            index = Integer.parseInt(bundle.getString("index"));

            //create camera class
            camera = new Camera();

            //init gui
            initGUI(poi);
        } else {
            //instantiate record
            record = new Record(getActivity());

            //get user current position for the POD
            record.getUserLatLng(new DataListener() {
                @Override
                public void onSuccess(Object object) {
                    Location location = (Location) object;
                    String latLng = "Longitude : " + location.getLongitude() + ", Latitude : " + location.getLatitude();
                    label_valuesGps.setText(latLng);
                    position = new Position(location.getLongitude(), location.getLatitude(), location.getAltitude(), new Date().toString());
                }

                @Override
                public void onFailed(Object dbError) {
                }
            });


        }

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
                if (poi.getPosition() == null) {
                    poi.setPosition(position);
                }

                //update or add the poi
                if (index != -1) {
                    track.getPois().set(index, poi);
                } else {
                    track.getPois().add(poi);
                }

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
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.choice_picture_title)
                    .setMessage(R.string.choice_picture_message)
                    .setPositiveButton(R.string.choice_picture_camera, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            camera.setChoice("camera");
                            //camera.launchCamera(Poi_Fragment.this);
                            camera.takePictureIntent(Poi_Fragment.this);
                        }

                    })
                    .setNegativeButton(R.string.choice_picture_gallery, new DialogInterface.OnClickListener() {
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
        Log.e("TEST-TEST", "Data : " + data) ;
        if (camera.getChoice() == "camera") {

            Bitmap picture = BitmapFactory.decodeFile(camera.getAbsPathPicture());
            Log.e("TEST-TEST", "Path is : " + camera.getAbsPathPicture()) ;


            img_pictureView.setImageBitmap(picture);
            //then encode the picture and add to the string
            poi.setPicture(camera.encodeBitmap(picture));

        } else {
            camera.addToImageViewGallery(requestCode, resultCode, getActivity(), img_pictureView, data);
            //then encode the picture and add to the string
            poi.setPicture(camera.encodeImageWithGallery());
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

    private void initGUI(POI poi) {
        edtxt_poiName.setText(poi.getName());
        edtxt_poiDescription.setText(poi.getDescription());
        String latLng = "Longitude : " + poi.getPosition().getLongitude() + ", Latitude : " + poi.getPosition().getLatitude();
        label_valuesGps.setText(latLng);
        camera.decodeB64Bitmap(poi.getPicture(), img_pictureView);
    }
}
