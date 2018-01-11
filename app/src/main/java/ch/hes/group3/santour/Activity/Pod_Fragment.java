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

import ch.hes.group3.santour.DTO.POD;
import ch.hes.group3.santour.DTO.Position;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Firebase.StoragePicture;
import ch.hes.group3.santour.Logic.Camera;
import ch.hes.group3.santour.Logic.Record;


public class Pod_Fragment extends Fragment {

    //elements
    private EditText txtName;
    private ImageButton btnTakePicture;
    private ImageView pictureView;
    private EditText txtDescription;
    private Button btnNext;
    private TextView txtLng;
    private TextView txtLat;

    //Track's objects
    private POD pod;
    private Position position;
    private Camera camera;

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

    private StoragePicture storePic;
    private String filenamePOD_Cam;
    private String pathFileStore;
    private boolean pictureChanged;


    public Pod_Fragment() {
        pod = new POD();
        pod.setPicture("");
        update = false;
        pictureChanged = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pod, container, false);

        MainActivity.setIsInRecordFragment(false);

        //instantiate elements
        btnNext = (Button) view.findViewById(R.id.btn_next);
        txtName = (EditText) view.findViewById(R.id.input_NamePod);
        txtDescription = (EditText) view.findViewById(R.id.input_descriptionPod);
        btnTakePicture = (ImageButton) view.findViewById(R.id.imageButton);
        pictureView = (ImageView) view.findViewById(R.id.imageView);
        txtLng = (TextView) view.findViewById(R.id.label_valuesGpsLongitude);
        txtLat = (TextView) view.findViewById(R.id.label_valuesGpsLattitude);

        storePic = new StoragePicture();

        if (getArguments() != null) {
            update = true;
            //get the bundle
            bundle = getArguments();
            pod = (POD) bundle.getSerializable("POD");
            index = Integer.parseInt(bundle.getString("index"));

            //create the camera class
            camera = new Camera();

            //init the gui
            initGUI(pod);
        } else {
            //get user current position for the POD
            Record.getUserLatLng(new DataListener() {
                @Override
                public void onSuccess(Object object) {
                    Location location = (Location) object;
                    String lat = "Latitude : " + location.getLatitude();
                    String longi = "Longitude : " + location.getLongitude();
                    txtLng.setText(longi);
                    txtLat.setText(lat);
                    position = new Position(location.getLongitude(), location.getLatitude(), location.getAltitude(), new Date().toString());
                }

                @Override
                public void onFailed(Object dbError) {
                }
            });
        }

        //on click for buttons
        btnNext.setOnClickListener(new NextPOD());
        btnTakePicture.setOnClickListener(new TakePicture());

        return view;
    }

    /**
     * listener on a button to go to the categories of the pod
     */
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

                if (!update) {
                    pod.setPicture(filenamePOD_Cam);
                    storePic.uploadPicture(pathFileStore, filenamePOD_Cam);
                }

                String oldPictureName;
                if (update) {
                    if (pictureChanged) {
                        oldPictureName = pod.getPicture();
                        pod.setPicture(filenamePOD_Cam);
                        storePic.uploadPicture(pathFileStore, filenamePOD_Cam);
                        storePic.deletePicture(oldPictureName);
                    }
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

    /**
     * listener on the button to add a picture to the pod
     * you can choose between an import from the gallery
     * and to take the picture directly from your phone
     */
    private class TakePicture implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            TextView myTitle = new TextView(getContext());
            myTitle.setText(R.string.choice_picture_title);
            myTitle.setTextSize(25);
            myTitle.setTextColor(getResources().getColor(R.color.red_main));
            myTitle.setPadding(80, 30, 10, 10);
            if (camera == null)
                camera = new Camera();
            new AlertDialog.Builder(getActivity())
                    .setCustomTitle(myTitle)
                    .setMessage(R.string.choice_picture_message)
                    .setPositiveButton(R.string.choice_picture_camera, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            camera.setChoice("camera");
                            camera.takePictureIntent(Pod_Fragment.this);
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
        pictureChanged = true;
        if (camera.getChoice() == "camera") {

            Bitmap picture = BitmapFactory.decodeFile(camera.getAbsPathPicture());

            Bitmap rotatedPic = null;
            try {
                rotatedPic = camera.rotatePicture(picture);
            } catch (IOException e) {
                e.printStackTrace();
            }

            pictureView.setImageBitmap(rotatedPic);
            //then encode the picture and add to the string
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            filenamePOD_Cam = "POD_" + timeStamp;
            pathFileStore = camera.getAbsPathPicture();


        } else {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            filenamePOD_Cam = "POD_" + timeStamp;

            camera.addToImageViewGallery(requestCode, resultCode, getActivity(), pictureView, data);

            pathFileStore = camera.getImgDecodableString();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * form validation to check that everything is filled in for the form
     * @return
     */
    private boolean formValidation() {
        if (txtName.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.addNamePOD, Toast.LENGTH_SHORT).show();
            return false;
        } else if (txtDescription.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.addDescriptionPOD, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(filenamePOD_Cam)) {
            Toast.makeText(getContext(), R.string.addPicturePOD, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * initialize the GUI on the creation of the ragment by setting everything needed in the view
     * @param pod
     */
    private void initGUI(POD pod) {
        txtName.setText(pod.getName());
        txtDescription.setText(pod.getDescription());
        String lat = getString(R.string.latitude) + pod.getPosition().getLatitude();
        String longi = getString(R.string.longitude) + pod.getPosition().getLongitude();
        txtLng.setText(longi);
        txtLat.setText(lat);
        filenamePOD_Cam = pod.getPicture();
        storePic.downloadPicture(pod.getPicture(), new DataListener() {
            @Override
            public void onSuccess(Object object) {
                byte[] byteArray = (byte[]) object;
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                pictureView.setImageBitmap(bitmap);
            }

            @Override
            public void onFailed(Object object) {

            }
        });
    }
}