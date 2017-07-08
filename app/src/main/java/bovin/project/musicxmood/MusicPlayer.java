package bovin.project.musicxmood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Bonny Haveliwala on 002 2 Apr 2016.
 */

public class MusicPlayer extends FragmentActivity {

    private Intent intent;
    private ArrayList<Music> musicArrayList;
    private int musicPosition;
    private static int count = 0;
    private MusicPlayerControllerFragment controllerFragment;
    private ImageView albumArtCoverView;
    private long albumID;
    private String titleOfMusic, nameOfArtist;
    private Music music;

    public MusicPlayer() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("STACK!", "inside onCreate MusicPlayer");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_interface);

        controllerFragment = (MusicPlayerControllerFragment) getSupportFragmentManager().findFragmentById(R.id.controlFragment);

        albumArtCoverView = (ImageView) findViewById(R.id.albumArtCoverView);

        intent = getIntent();

        musicArrayList = intent.getParcelableArrayListExtra("musicArrayList");
        musicPosition = intent.getIntExtra("musicPosition", 0);

        music = musicArrayList.get(musicPosition);

        albumID = music.getAlbumID();
        titleOfMusic = music.getName();
        nameOfArtist = music.getArtist();

        albumArtCoverView.setImageBitmap(MusicRetrieval.getAlbumArt(this, titleOfMusic, albumID));

        //if(music.get_ID() != MusicPlaybackService.musicPlaybackService.getCurrentMusicID()){
        controllerFragment.setTitleOfMusic(titleOfMusic);
        controllerFragment.setTitleOfArtist(nameOfArtist);
        controllerFragment.setMusicPosition(musicPosition);
        controllerFragment.setMusicArrayList(musicArrayList);
        //}

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
        controllerFragment.getView().startAnimation(animation);

        Log.i("MusicPlayer", "outside onCreate Musicplayer");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void togglePlay(View view) {
        controllerFragment.togglePlay();
    }

    public void nextMusic(View view) {
        controllerFragment.nextMusic();
        albumArtCoverView.setImageBitmap(MusicRetrieval.getAlbumArt(this,
                MusicPlayerControllerFragment.getCurrentMusicObject().getName(),
                MusicPlayerControllerFragment.getCurrentMusicObject().getAlbumID()));
    }

    public void previousMusic(View view) {
        controllerFragment.previousMusic();
        albumArtCoverView.setImageBitmap(MusicRetrieval.getAlbumArt(this,
                MusicPlayerControllerFragment.getCurrentMusicObject().getName(),
                MusicPlayerControllerFragment.getCurrentMusicObject().getAlbumID()));
    }

    @Override
    protected void onDestroy() {
        Log.i("MusicPlayer", "Inside onDestroy");
        super.onDestroy();
        intent = null;
        musicArrayList = null;
        controllerFragment = null;
        albumArtCoverView = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        Log.i("MusicPlayer", "Inside onStop");
        super.onStop();
    }
}
