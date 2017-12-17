package ch.hes.group3.santour.Activity;

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
import android.text.TextUtils;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.hes.group3.santour.DTO.POI;
import ch.hes.group3.santour.DTO.Position;
import ch.hes.group3.santour.DTO.Track;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Firebase.StoragePicture;
import ch.hes.group3.santour.Logic.Camera;
import ch.hes.group3.santour.Logic.Record;


public class Poi_Fragment extends Fragment {

    //elements
    private EditText edtxt_poiName;
    private TextView label_valuesGpsLongetude;
    private TextView label_valuesGpsLattitude;
    private ImageButton img_takePicture;
    private ImageView img_pictureView;
    private EditText edtxt_poiDescription;
    private Button btn_poiSave;

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
    private boolean pictureChanged;
    private int index;

    private StoragePicture storePic;
    private String filenamePOI_Cam;
    private String pathFileStore;


    public Poi_Fragment() {
        poi = new POI();
        poi.setPicture("");
        update = false;
        index = -1;
        pictureChanged = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for tghis fragment
        View view = inflater.inflate(R.layout.fragment_poi, container, false);

        //init elements
        img_takePicture = (ImageButton) view.findViewById(R.id.imageButton);
        img_pictureView = (ImageView) view.findViewById(R.id.imageView);
        label_valuesGpsLongetude = (TextView) view.findViewById(R.id.label_valuesGpsLongitude);
        label_valuesGpsLattitude = (TextView) view.findViewById(R.id.label_valuesGpsLattitude);
        btn_poiSave = (Button) view.findViewById(R.id.btn_save);
        edtxt_poiName = (EditText) view.findViewById(R.id.input_NamePoi);
        edtxt_poiDescription = (EditText) view.findViewById(R.id.input_descriptinPoi);

        storePic = new StoragePicture();

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
            //get user current position for the POD
            Record.getUserLatLng(new DataListener() {
                @Override
                public void onSuccess(Object object) {
                    Location location = (Location) object;
                    String lat = "Latitude : " + location.getLatitude();
                    String longi = "Longitude : " + location.getLongitude();
                    label_valuesGpsLongetude.setText(longi);
                    label_valuesGpsLattitude.setText(lat);
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

                if (!update) {
                    poi.setPicture(filenamePOI_Cam);
                    storePic.uploadPicture(pathFileStore, filenamePOI_Cam);
                }

                String oldPictureName;
                if (update) {
                    if (pictureChanged) {
                        oldPictureName = poi.getPicture();
                        poi.setPicture(filenamePOI_Cam);
                        storePic.uploadPicture(pathFileStore, filenamePOI_Cam);
                        storePic.deletePicture(oldPictureName);
                    }
                }

                //update or add the poi
                if (index != -1) {
                    track.getPois().set(index, poi);
                } else {
                    track.getPois().add(poi);
                }

                MainActivity.setTrack(track);

                //add the marker to the map
                Record.addMarker(poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getName());

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
        pictureChanged = true;
        if (camera.getChoice() == "camera") {

            Bitmap picture = BitmapFactory.decodeFile(camera.getAbsPathPicture());
            Log.e("TEST-TEST", "Path is : " + camera.getAbsPathPicture());

            Bitmap rotatedPic = null;
            try {
                rotatedPic = camera.rotatePicture(picture);
            } catch (IOException e) {
                e.printStackTrace();
            }

            img_pictureView.setImageBitmap(rotatedPic);
            //then encode the picture and add to the string
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            filenamePOI_Cam = "POI_" + timeStamp;
            pathFileStore = camera.getAbsPathPicture();

        } else {

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            filenamePOI_Cam = "POI_" + timeStamp;

            camera.addToImageViewGallery(requestCode, resultCode, getActivity(), img_pictureView, data);

            pathFileStore = camera.getImgDecodableString();

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
        } else if (TextUtils.isEmpty(filenamePOI_Cam)) {
            Toast.makeText(getContext(), R.string.addPicturePOI, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void initGUI(POI poi) {
        edtxt_poiName.setText(poi.getName());
        edtxt_poiDescription.setText(poi.getDescription());
        String lat = getString(R.string.latitude) + poi.getPosition().getLatitude();
        String longi = getString(R.string.longitude) + poi.getPosition().getLongitude();
        label_valuesGpsLongetude.setText(longi);
        label_valuesGpsLattitude.setText(lat);
        filenamePOI_Cam = poi.getPicture();
        storePic.downloadPicture(poi.getPicture(), new DataListener() {
            @Override
            public void onSuccess(Object object) {
                byte[] byteArray = (byte[]) object;
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                img_pictureView.setImageBitmap(bitmap);
            }

            @Override
            public void onFailed(Object object) {

            }
        });
    }
}
