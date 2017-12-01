package com.example.group3.santour.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.group3.santour.DTO.POD;
import com.example.group3.santour.DTO.PODCategory;
import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.R;

import java.util.List;

public class Pod_Details_Fragment extends Fragment {


    //elements
    private Button btnSave;

    //Track's objects
    private Track track;
    private POD pod;
    private List<PODCategory> podCategoryList;

    //fragment
    private Bundle bundle;
    private FragmentManager fragmentManager;

    public Pod_Details_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pod__details, container, false);

        //instantiate all elements
        btnSave = (Button) view.findViewById(R.id.btn_savePodDetails);

        //add listener to buttons
        btnSave.setOnClickListener(new SavePOD());

        //get current POD from Bundle
        bundle = getArguments();
        pod = (POD) bundle.getSerializable("POD");


        return view;
    }

    private class SavePOD implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            track = MainActivity.getTrack();
            track.getPods().add(pod);
            MainActivity.setTrack(track);
            fragmentManager = getActivity().getSupportFragmentManager();
            Log.e("COUNT pod details frag", fragmentManager.getBackStackEntryCount() + "");
            fragmentManager.popBackStack();
            fragmentManager.popBackStack();
        }
    }

}
