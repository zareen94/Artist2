package com.example.user.artist2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    TextView textViewArtistName;
    EditText editTextTrackName;
    SeekBar seekBarRating;

    Button buttonAddTrack;

    ListView listViewTrack;

    DatabaseReference databaseTrack;

    List<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);


        textViewArtistName =(TextView)findViewById(R.id.textViewArtistName);
        editTextTrackName =(EditText)findViewById(R.id.TrackName);
        seekBarRating =(SeekBar)findViewById(R.id.seekBarRating);

        buttonAddTrack =(Button)findViewById(R.id.buttonAddTrack);

        listViewTrack =(ListView)findViewById(R.id.listViewTrack);

        tracks = new ArrayList<>();

        Intent intent =getIntent();

        String id = intent.getStringExtra(MainActivity.ARTIST_ID);
        String name = intent.getStringExtra(MainActivity.ARTIST_NAME);

        textViewArtistName.setText(name);

        databaseTrack = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrack();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTrack.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tracks.clear();
                for (DataSnapshot tracksnapshot : dataSnapshot.getChildren() )
                {
                    Track track = tracksnapshot.getValue(Track.class);
                    tracks.add(track);
                }

                TrackList trakListAdapter = new TrackList(AddTrackActivity.this,tracks);
                listViewTrack.setAdapter(trakListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveTrack(){

        String trackname = editTextTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if(TextUtils.isEmpty(trackname))
        {
            Toast.makeText(this, "should not empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String id = databaseTrack.push().getKey();
            Track track = new Track(id,trackname,rating);
            databaseTrack.child(id).setValue(track);
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();
        }

    }
}
