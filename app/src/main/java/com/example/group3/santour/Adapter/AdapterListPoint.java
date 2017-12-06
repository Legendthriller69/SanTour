package com.example.group3.santour.Adapter;

/**
 * Created by aleks-lazic on 06.12.2017
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.group3.santour.Activity.R;


import com.example.group3.santour.DTO.Point;

import java.util.List;

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
