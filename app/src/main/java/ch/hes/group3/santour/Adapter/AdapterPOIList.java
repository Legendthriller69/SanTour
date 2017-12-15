package ch.hes.group3.santour.Adapter;

/**
 * Created by aleks-lazic on 06.12.2017
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.hes.group3.santour.Activity.R;
import ch.hes.group3.santour.DTO.POI;

public class AdapterPOIList extends ArrayAdapter<POI> {

    private List<POI> poiList;
    private TextView txtName;


    public AdapterPOIList(Context context, List<POI> poiList) {
        super(context, 0, poiList);
        this.poiList = poiList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.row_poi_list, null);
        }

        POI poi = poiList.get(position);

        if (poi != null) {
            txtName = (TextView) convertView.findViewById(R.id.namePoiRow);
            txtName.setText(poi.getName());
        }

        return convertView;
    }


}
