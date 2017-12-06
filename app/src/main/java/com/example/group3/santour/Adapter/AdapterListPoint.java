package com.example.group3.santour.Adapter;

/**
 * Created by aleks-lazic on 06.12.2017
 */

import com.example.group3.santour.DTO.PODCategory;
import com.example.group3.santour.DTO.Point;
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

public class AdapterListPoint extends ArrayAdapter<Point> {

    private List<Point> pointList;


    public AdapterListPoint(Context context, List<Point> pointList) {
        super(context, 0, pointList);
        this.pointList = pointList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.row_categoriepod, null);
        }

//        PODCategory podCategory = podCategories.get(position);
//        Category category = categories.get(position);
//
//        if (podCategory != null) {
//
//        }

        return convertView;
    }


}
