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
import ch.hes.group3.santour.DTO.POD;

public class AdapterPODList extends ArrayAdapter<POD> {

    private List<POD> podList;
    private TextView txtName;


    public AdapterPODList(Context context, List<POD> podList) {
        super(context, 0, podList);
        this.podList = podList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.row_pod_list, null);
        }

        POD pod = podList.get(position);

        if (pod != null) {
            txtName = (TextView) convertView.findViewById(R.id.namePodRow);
            txtName.setText(pod.getName());
        }

        return convertView;
    }


}
