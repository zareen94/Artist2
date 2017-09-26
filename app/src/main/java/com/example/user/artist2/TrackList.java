package com.example.user.artist2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24/9/2017.
 */

public class TrackList extends ArrayAdapter<Track> {

    private Activity context;
    private List<Track> tracks;

    public TrackList(Activity context, List<Track> tracks) {
        super(context, R.layout.track_list, tracks);

        this.context = context;
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.track_list, null, true);

        TextView textviewname = (TextView)listViewItem.findViewById(R.id.textViewName);
        TextView textviewrating = (TextView)listViewItem.findViewById(R.id.textViewRating);

        Track track = tracks.get(position);

        textviewname.setText(track.getTrackName());
        textviewrating.setText(String.valueOf(track.getTrackRating()));

        return listViewItem;
    }

}
