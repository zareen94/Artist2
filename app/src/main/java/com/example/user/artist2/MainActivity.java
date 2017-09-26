package com.example.user.artist2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseArtis;

    ListView listViewArtist;
    public static final String ARTIST_NAME="artistname;";

    public static final String ARTIST_ID="artistid";


    EditText editTextname;
    Button buttonAdd;
    Spinner spinnergenre;
    List<Artist> artistList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseArtis = FirebaseDatabase.getInstance().getReference("artis");

        editTextname =(EditText)findViewById(R.id.editText);
        spinnergenre = (Spinner) findViewById(R.id.spinner);
        buttonAdd =(Button)findViewById(R.id.button2);
        listViewArtist =(ListView)findViewById(R.id.listVieqArtist);

        artistList = new ArrayList<>();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtis();

            }
        });

        listViewArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Artist artist = artistList.get(position);
                Intent intent =new Intent(getApplicationContext(),AddTrackActivity.class);

                intent.putExtra(ARTIST_ID,artist.getArtistId());
                intent.putExtra(ARTIST_NAME,artist.getArtistName());
                startActivity(intent);

            }
        });

        listViewArtist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Artist artist =artistList.get(position);

                showUpdateDialog(artist.getArtistId(),artist.getArtistName());

                return false;
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseArtis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artistList.clear();
                for(DataSnapshot artistsnapshot : dataSnapshot.getChildren()){

                    Artist artist = artistsnapshot.getValue(Artist.class);

                    artistList.add(artist);
                }

                ArtistList adapter = new ArtistList(MainActivity.this, artistList);
                listViewArtist.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void showUpdateDialog(final String artistId, String artisName)
    {

        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView =inflater.inflate(R.layout.update_dialog,null);

        dialogbuilder.setView(dialogView);

        final EditText editTextName = (EditText)dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = (Button)dialogView.findViewById(R.id.buttonupdate);
        final Spinner spinnergenre =(Spinner)dialogView.findViewById(R.id.spinnerGenre);
        final Button buttonDelete =(Button)dialogView.findViewById(R.id.buttondelete);



        dialogbuilder.setTitle("Updating Artist " +artisName);

        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.show();
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editTextName.getText().toString().trim();
                String genre = spinnergenre.getSelectedItem().toString();


                if(TextUtils.isEmpty(name))
                {
                    editTextName.setError("Name Required");
                    return;
                }

                    updateArtist(artistId,name,genre);
                    alertDialog.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteArtist(artistId);
                alertDialog.dismiss();

            }
        });


    }

    private void DeleteArtist(String artistId) {

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("artis").child(artistId);

        dbref.removeValue();

        Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_LONG).show();

    }

    private boolean updateArtist(String id,String name,String genre){

        DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("artis").child(id);

        Artist artist =new Artist(id,name,genre);

        databaseReference.setValue(artist);
        Toast.makeText(this,"Artist Update",Toast.LENGTH_LONG).show();

        return  true;

    }



    private void addArtis(){
        String name =editTextname.getText().toString().trim();
        String genre = spinnergenre.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name))
        {
            String id = databaseArtis.push().getKey();
            Artist artist = new Artist(id,name,genre);

            databaseArtis.child(id).setValue(artist);
            Toast.makeText(this,"Artis Added",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"You should enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
