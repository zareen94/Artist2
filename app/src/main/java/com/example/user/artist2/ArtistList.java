package com.example.user.artist2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 24/9/2017.
 */

public class ArtistList extends ArrayAdapter<Artist> {

    private Activity context;
    private List<Artist> artistList;

    public ArtistList(Activity context, List<Artist> artistList) {
        super(context, R.layout.list_layout, artistList);

        this.context = context;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textviewname = (TextView)listViewItem.findViewById(R.id.textViewName);
        TextView textviewgenre = (TextView)listViewItem.findViewById(R.id.textViewGenre);

        Artist artist = artistList.get(position);

        textviewname.setText(artist.getArtistName());
        textviewgenre.setText(artist.getArtistGenre());

        return listViewItem;
    }

}
