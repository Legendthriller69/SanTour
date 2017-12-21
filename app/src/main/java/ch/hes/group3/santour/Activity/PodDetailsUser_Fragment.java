package ch.hes.group3.santour.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hes.group3.santour.DTO.POD;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Firebase.StoragePicture;


public class PodDetailsUser_Fragment extends Fragment {

    //elements
    private ImageView imgView;
    private TextView txtLatitude;
    private TextView txtLongitude;
    private TextView txtName;
    private TextView txtDescription;
    private TextView listViewCategories;

    //Bundle
    private Bundle bundle;

    //POD object
    private POD pod;

    //Firebase storage
    private StoragePicture storagePicture;

    public PodDetailsUser_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pod_details_user, container, false);

        imgView = (ImageView) view.findViewById(R.id.ImgView_PodUser);
        txtLatitude = (TextView) view.findViewById(R.id.txtV_GpsUseLat);
        txtLongitude = (TextView) view.findViewById(R.id.txtV_GpsUseLong);
        txtName = (TextView) view.findViewById(R.id.txtV_NamePodUser);
        txtDescription = (TextView) view.findViewById(R.id.txtV_PodDescripUser);

        storagePicture = new StoragePicture();

        bundle = getArguments();
        pod = (POD)bundle.getSerializable("POD");

        txtLatitude.setText(pod.getPosition().getLatitude() + "");
        txtLongitude.setText(pod.getPosition().getLongitude() + "");
        txtName.setText(pod.getName());
        txtDescription.setText(pod.getDescription());

        storagePicture.downloadPicture(pod.getPicture(), new DataListener() {
            @Override
            public void onSuccess(Object object) {
                byte[] byteArray = (byte[]) object;
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imgView.setImageBitmap(bitmap);
            }

            @Override
            public void onFailed(Object object) {

            }
        });





        return view;

    }

}