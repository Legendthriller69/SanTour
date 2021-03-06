package ch.hes.group3.santour.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ch.hes.group3.santour.Adapter.AdapterPODList;
import ch.hes.group3.santour.DTO.POD;
import ch.hes.group3.santour.DTO.Track;


public class ListPODs extends Fragment {

    //fragment
    private Fragment fragment;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Bundle bundle;

    //adapter
    private AdapterPODList adapterPODList;
    private ListView mListView;
    private List<POD> pods;

    public ListPODs() {
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
        View view = inflater.inflate(R.layout.fragment_list_pods, container, false);

        MainActivity.setIsInRecordFragment(false);

        // options menu
        setHasOptionsMenu(true);

        fragmentManager = getActivity().getSupportFragmentManager();

        // init elements
        mListView = (ListView) view.findViewById(R.id.listViewPOI);

        //create the adapter
        if(getArguments() != null){
            bundle = getArguments();
            pods = ((Track) bundle.getSerializable("TRACK")).getPods();
        } else {
            pods = MainActivity.getTrack().getPods();
        }
        adapterPODList = new AdapterPODList(getContext(), pods);
        mListView.setAdapter(adapterPODList);
        mListView.setOnItemClickListener(new ItemClickPOD());

        return view;
    }

    /**
     * click on the pod list to go to the details of a POD
     */
    private class ItemClickPOD implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //create new bundle to put the pod object
            bundle = new Bundle();
            bundle.putSerializable("POD", pods.get(i));
            bundle.putString("index", String.valueOf(i));

            if(getArguments() == null){
                //create the fragment and add the bundle to the arguments
                fragment = new Pod_Fragment();
            } else {
                fragment = new PodDetailsUser_Fragment();
            }
            //set arguments to fragment
            fragment.setArguments(bundle);

            //switch to the new fragment
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();
        }
    }
}
