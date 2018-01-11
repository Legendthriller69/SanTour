package ch.hes.group3.santour.Adapter;

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

import ch.hes.group3.santour.DTO.Category;
import ch.hes.group3.santour.DTO.PODCategory;

import java.util.List;

public class CategoriePod_Adapter extends ArrayAdapter<PODCategory> {

    private List<PODCategory> podCategories;
    private TextView txtView;
    private SeekBar seekBar;
    private TextView txtViewValue;
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
        this.context = context;
        this.podCategories = podCategories;
        this.categories = categories;
        this.update = update;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            vi = LayoutInflater.from(context);
            convertView = vi.inflate(ch.hes.group3.santour.Activity.R.layout.row_categoriepod, null);
        }

        PODCategory podCategory = podCategories.get(position);
        Category category = categories.get(position);

        if (podCategory != null) {
            txtView = (TextView) convertView.findViewById(ch.hes.group3.santour.Activity.R.id.nameCategoryRow);
            txtViewValue = (TextView) convertView.findViewById(ch.hes.group3.santour.Activity.R.id.ValueDetails);
            seekBar = (SeekBar) convertView.findViewById(ch.hes.group3.santour.Activity.R.id.seekBar);

            txtView.setText(category.getName());
            txtViewValue.setText(String.valueOf(0));
            seekBar.setProgress(0);
            seekBar.setMax(10);
            if (update) {
                seekBar.setProgress(podCategory.getValue());
                txtViewValue.setText(String.valueOf(seekBar.getProgress()));
            }
            seekBar.setOnSeekBarChangeListener(new SeekChange(position, txtViewValue));
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
        private TextView txtViewValue;

        public SeekChange(int position, TextView txtViewValue) {
            this.position = position;
            this.txtViewValue = txtViewValue;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            podCategories.get(position).setValue(i);
            txtViewValue.setText(String.valueOf(i));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    public List<PODCategory> getPodCategories() {
        return podCategories;
    }
}
