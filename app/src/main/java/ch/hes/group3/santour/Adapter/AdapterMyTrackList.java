package ch.hes.group3.santour.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ch.hes.group3.santour.DTO.Track;

import java.util.List;

/**
 * Created by DarkFace on 14 déc. 2017.
 */

public class AdapterMyTrackList extends ArrayAdapter<Track> {

    private List<Track> trackList;
    private TextView txtName;


    public AdapterMyTrackList(Context context, List<Track> trackList) {
        super(context, 0, trackList);
        this.trackList = trackList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(ch.hes.group3.santour.Activity.R.layout.row_track_list, null);
        }

        Track track = trackList.get(position);

        if (track != null) {
            txtName = (TextView) convertView.findViewById(ch.hes.group3.santour.Activity.R.id.myTrackRow);
            txtName.setText(track.getName());
        }

        return convertView;
    }

}
