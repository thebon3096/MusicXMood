package bovin.project.musicxmood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bonny Haveliwala on 006 6 Apr 2016.
 */
public class ArtistMusicActivity extends AppCompatActivity {

    DatabaseHandler db;
    ArrayList<Music> artistMusicArrayList;
    Intent intent;
    RecyclerView artistMusicRecyclerView;
    TextView artistMusicArtist;
    ArtistMusicRecyclerViewAdapter artistMusicRecyclerViewAdapter;
    String artistName;
    View parallaxHeader;
    ImageView parallaxHeaderImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_music_activity);
        db = new DatabaseHandler(this, null, null, 0);
        intent = getIntent();

        artistName = intent.getStringExtra("titleOfArtist");
        if (savedInstanceState == null)
            artistMusicArrayList = db.getArtistMusic(artistName);
        else
            artistMusicArrayList = savedInstanceState.getParcelableArrayList("artistMusicArrayList");
        artistMusicRecyclerView = (RecyclerView) findViewById(R.id.artistMusicRecyclerView);

        artistMusicRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        parallaxHeader = LayoutInflater.from(this)
                .inflate(R.layout.parallax_image, artistMusicRecyclerView, false);
        parallaxHeaderImageView = (ImageView) parallaxHeader.findViewById(R.id.parallax_image);
        parallaxHeaderImageView.setImageBitmap(
                MusicRetrieval.getAlbumArt(this, artistName, artistMusicArrayList.get(0).getAlbumID()));

        artistMusicArtist = (TextView) parallaxHeader.findViewById(R.id.artistMusicArtist);
        artistMusicArtist.setText(artistName);
        artistMusicArtist.setSelected(true);

        artistMusicRecyclerViewAdapter = new ArtistMusicRecyclerViewAdapter(this, artistMusicArrayList);
        artistMusicRecyclerViewAdapter.setParallaxHeader(parallaxHeader, artistMusicRecyclerView);

        artistMusicRecyclerView.setAdapter(artistMusicRecyclerViewAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("artistMusicArrayList", artistMusicArrayList);
        super.onSaveInstanceState(outState);
    }
}
