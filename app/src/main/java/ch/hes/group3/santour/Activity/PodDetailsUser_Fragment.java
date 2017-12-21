package ch.hes.group3.santour.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class PodDetailsUser_Fragment extends Fragment {

    private ImageView imgView;
    private TextView txtLatitude;
    private TextView txtLongitude;
    private TextView txtName;
    private TextView txtDescription;
    private TextView listViewCategories;

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


    }

}