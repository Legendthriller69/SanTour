package com.example.group3.santour.Adapter;

/**
 * Created by DarkFace on 28 nov. 2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group3.santour.Activity.R;
import com.example.group3.santour.DTO.Category;
import com.example.group3.santour.DTO.PODCategory;

import java.util.List;

public class CategoriePod_Adapter extends ArrayAdapter<PODCategory> {

    private List<PODCategory> podCategories;
    private TextView txtView;
    private SeekBar seekBar;
    private List<Category> categories;
    private boolean update;
    private Context context;
    private LayoutInflater vi;

    public CategoriePod_Adapter(Context context, List<PODCategory> podCategories, List<Category> categories) {
        super(context, 0, podCategories);
        this.context = context;
        this.podCategories = podCategories;
        this.categories = categories;
        createPODCategories();
    }

    public CategoriePod_Adapter(Context context, List<PODCategory> podCategories, List<Category> categories, boolean update) {
        super(context, 0, podCategories);
        this.podCategories = podCategories;
        this.categories = categories;
        this.update = update;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
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
            if (update) {
                seekBar.setProgress(podCategory.getValue());
            }
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

        public SeekChange(int position) {
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
