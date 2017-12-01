package com.example.group3.santour.Adapter;

/**
 * Created by DarkFace on 28 nov. 2017.
 */

import com.example.group3.santour.DTO.PODCategory;
import com.example.group3.santour.Firebase.CategoryDB;
import com.example.group3.santour.R;
import com.example.group3.santour.DTO.Category;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriePod_Adapter extends ArrayAdapter<PODCategory> {

    private List<PODCategory> podCategories;
    private TextView txtView;
    private SeekBar seekBar;
    private List<Category> categories;

    public CategoriePod_Adapter(Context context, List<PODCategory> podCategories, List<Category> categories) {
        super(context, 0, podCategories);
        this.podCategories = podCategories;
        this.categories = categories;
        createPODCategories();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.row_categoriepod, null);
        }

        PODCategory podCategory = podCategories.get(position);
        Category category = categories.get(position);

        if (podCategory != null) {
            txtView = (TextView) convertView.findViewById(R.id.nameCategoryRow);
            seekBar = (SeekBar) convertView.findViewById(R.id.seekBar);

            txtView.setText(category.getName());
            seekBar.setProgress(0);
            seekBar.setMax(10);
            seekBar.setOnSeekBarChangeListener(new SeekChange(position));
        }

        return convertView;
    }

    private void createPODCategories() {
        for (int i = 0; i < categories.size(); i++) {
            podCategories.add(new PODCategory(categories.get(i).getId(), 0));
        }
    }

    private class SeekChange implements SeekBar.OnSeekBarChangeListener {
        private int position;

        public SeekChange(int position){
            this.position = position;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            podCategories.get(position).setValue(i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

}
