package com.example.group3.santour.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.group3.santour.Adapter.CategoriePod_Adapter;
import com.example.group3.santour.DTO.Category;
import com.example.group3.santour.DTO.POD;
import com.example.group3.santour.DTO.PODCategory;
import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.Firebase.CategoryDB;
import com.example.group3.santour.Firebase.DataListener;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class Pod_Details_Fragment extends Fragment {

    //elements
    private Button btnSave;
    private ListView mListView;

    //Track's objects
    private Track track;
    private POD pod;
    private List<PODCategory> podCategoryList;
    private List<Category> categories;

    //Adapter
    private CategoriePod_Adapter adapterCategory;

    //fragment
    private Bundle bundle;
    private FragmentManager fragmentManager;
    private int index;

    public Pod_Details_Fragment() {
        podCategoryList = new ArrayList<>();
        categories = new ArrayList<>();
        index = -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pod__details, container, false);

        //instantiate all elements
        btnSave = (Button) view.findViewById(R.id.btn_savePodDetails);
        mListView = (ListView) view.findViewById(R.id.listView);

        //get current POD from Bundle
        bundle = getArguments();
        pod = (POD) bundle.getSerializable("POD");
        if (bundle.getString("index") != null) {
            index = Integer.parseInt(bundle.getString("index"));
        }


        //get all categories from firebase
        CategoryDB.getAllCategories(new DataListener() {
            @Override
            public void onSuccess(Object object) {
                //get the categories
                categories = (List<Category>) object;

                //create the adapter
                if (index == -1) {
                    adapterCategory = new CategoriePod_Adapter(getContext(), podCategoryList, categories);
                } else {
                    adapterCategory = new CategoriePod_Adapter(getContext(), pod.getPodCategories(), categories, true);
                }
                mListView.setAdapter(adapterCategory);

                //add listener to buttons
                btnSave.setOnClickListener(new SavePOD());

            }

            @Override
            public void onFailed(DatabaseError dbError) {

            }
        });

        return view;
    }

    private class SavePOD implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            track = MainActivity.getTrack();
            pod.setPodCategories(podCategoryList);

            //update or add the poi
            if (index != -1) {
                track.getPods().set(index, pod);
            } else {
                track.getPods().add(pod);
            }

            MainActivity.setTrack(track);

            fragmentManager = getActivity().getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry first = fragmentManager
                        .getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 2);
                fragmentManager.popBackStack(first.getId(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

}
