package ch.hes.group3.santour.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hes.group3.santour.DTO.POI;
import ch.hes.group3.santour.Firebase.DataListener;
import ch.hes.group3.santour.Firebase.StoragePicture;


public class PoiDetailsUser_Fragment extends Fragment {

    //declare all elements
    private ImageView imgView;
    private TextView txtLatitude;
    private TextView txtLongitude;
    private TextView txtName;
    private TextView txtDescription;
    private FragmentManager fragmentManager;

    private POI poi;
    private Bundle bundle;
    private StoragePicture storagePicture;


    public PoiDetailsUser_Fragment() {
        // Required empty public constructor
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.back_record_menu, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.back_record:
                fragmentManager.popBackStack();
                break;
        }

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poi_details_user, container, false);

        // options menu
        setHasOptionsMenu(true);

        fragmentManager = getActivity().getSupportFragmentManager();

        //instantiate all elements
        imgView = (ImageView) view.findViewById(R.id.ImgView_PoIUser);
        txtLongitude = (TextView) view.findViewById(R.id.txtV_GpsUseLong);
        txtLatitude = (TextView) view.findViewById(R.id.txtV_GpsUseLat);
        txtName = (TextView) view.findViewById(R.id.txtV_NamePoIUser);
        txtDescription = (TextView) view.findViewById(R.id.txtV_PoIDescripUser);

        storagePicture = new StoragePicture();

        bundle = getArguments();
        poi = (POI) bundle.getSerializable("POI");

        storagePicture.downloadPicture(poi.getPicture(), new DataListener() {
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
        txtName.setText(poi.getName());
        txtDescription.setText(poi.getDescription());
        //txtLatitude.setText("Latitude : " + poi.getPosition().getLatitude());
        //txtLongitude.setText("Longitude : " + poi.getPosition().getLongitude());

        //set values from the poi
        return view;
    }


}
