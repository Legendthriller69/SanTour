package com.example.group3.santour.Adapter;

/**
 * Created by DarkFace on 28 nov. 2017.
 */

import com.example.group3.santour.R;
import com.example.group3.santour.DTO.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class CategoriePod_Adapter extends ArrayAdapter<Category> {

        /*
        Déclaration des variables
         */
        public CategoriePod_Adapter(Context context, List<Category> categories){
            super(context, 0, categories);
        }

        /*
        Création des adapters pour les listView, cela prend une row et cela la définit comme model pour chaque ligne de la listView
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_categoriepod, parent, false);
            }


            CategoryViewHoldder viewHolder = (CategoryViewHoldder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new CategoryViewHoldder();
                viewHolder.nameCategory = (TextView) convertView.findViewById(R.id.nameCategoryRow);

                convertView.setTag(viewHolder);
            }

            //getitem (position) va récupérer l'item [position] de la list<Playground> playgrounds
            Category category = getItem(position);
            viewHolder.nameCategory.setText(category.getName());

            return convertView;
        }

        /*
        Tag Worker
         */
        private class CategoryViewHoldder{
            public TextView nameCategory;
        }


}
