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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ch.hes.group3.santour.DTO.Category;
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
    private ListView listViewCategories;

    private FragmentManager fragmentManager;

    //Bundle
    private Bundle bundle;

    //POD object
    private POD pod;
    private String[] categories;
    private List<Category> categoryList;
    private ArrayAdapter<String> adapter;

    //Firebase storage
    private StoragePicture storagePicture;

    public PodDetailsUser_Fragment() {}

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
        View view = inflater.inflate(R.layout.fragment_pod_details_user, container, false);

        // options menu
        setHasOptionsMenu(true);

        fragmentManager = getActivity().getSupportFragmentManager();

        imgView = (ImageView) view.findViewById(R.id.ImgView_PodUser);
        txtLatitude = (TextView) view.findViewById(R.id.txtV_GpsUseLat);
        txtLongitude = (TextView) view.findViewById(R.id.txtV_GpsUseLong);
        txtName = (TextView) view.findViewById(R.id.txtV_NamePodUser);
        txtDescription = (TextView) view.findViewById(R.id.txtV_PodDescripUser);
        listViewCategories = (ListView) view.findViewById(R.id.ListV_PodUser);

        storagePicture = new StoragePicture();

        bundle = getArguments();
        pod = (POD) bundle.getSerializable("POD");

        //set all elements values
        //txtLatitude.setText(pod.getPosition().getLatitude() + "");
        //txtLongitude.setText(pod.getPosition().getLongitude() + "");
        txtName.setText(pod.getName());
        txtDescription.setText(pod.getDescription());
        setDifficulties();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categories);
        listViewCategories.setAdapter(adapter);

        //get the picture from firebase storage
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

    private void setDifficulties(){
        categories = new String[pod.getPodCategories().size()];
        categoryList = DetailsExistingTracks.getCategories();
        for(int i = 0 ; i<categoryList.size() ; i++){
            categories[i] = "\t" + categoryList.get(i).getName() + " : \t" + pod.getPodCategories().get(i).getValue();
        }
    }

}