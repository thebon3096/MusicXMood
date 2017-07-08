package bovin.project.musicxmood;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

/**
 * Created by Bonny Haveliwala on 009 9 Mar 2016.
 */
public class MainActivity extends AppCompatActivity {

    ViewPagerAdapter vpAdapter;
    CharSequence[] Titles = {"All Music", "Artists", "Mood"};
    FragmentManager fm;
    ViewPager vp;
    SmartTabLayout tabs;
    ArrayList<Music> musicArrayList;
    MusicRetrieval musicRetrieval;
    DatabaseHandler databaseHandler;
    RelativeLayout linearLayout;
    MusicPlayerControllerFragment musicPlayerControllerFragment;
    static int count = 0;
    RelativeLayout relativeLayout;
    ImageView floatingControlImage, musicOverviewerImage;
    LinearLayout musicOverviewerLayout;
    TextView musicOverviewerTitle, musicOverviewerArtist;
    NotificationManager notificationManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onRestart() {
        super.onRestart();
        setUpControllerFragmentDisplay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("STACK!", "Entered OnCreate MainActivity");
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        sharedPreferences = getSharedPreferences("bovin.project.musicxmood", MODE_PRIVATE);

        databaseHandler = new DatabaseHandler(this, null, null, 0);

        Intent intent = getIntent();
        if (savedInstanceState == null)
            musicArrayList = intent.getParcelableArrayListExtra("MusicArrayList");
        else {
            musicArrayList = savedInstanceState.getParcelableArrayList("MusicArrayList");
            musicRetrieval = new MusicRetrieval(getApplicationContext());
        }
        vpAdapter = new ViewPagerAdapter(this, fm, Titles, Titles.length, musicArrayList);
        vp = (ViewPager) findViewById(R.id.mainViewPager);
        vp.setAdapter(vpAdapter);

        relativeLayout = (RelativeLayout) findViewById(R.id.mainActivityRelativeLayout);
        linearLayout = (RelativeLayout) findViewById(R.id.floatingControlFragmentLayout);
        linearLayout.setVisibility(View.GONE);

        musicOverviewerLayout = (LinearLayout) findViewById(R.id.musicOverviewerLayout);
        musicOverviewerImage = (ImageView) findViewById(R.id.overviewerImage);
        musicOverviewerTitle = (TextView) findViewById(R.id.musicOverviewerTitle);
        musicOverviewerArtist = (TextView) findViewById(R.id.musicOverviewerArtist);
        musicOverviewerTitle.setSelected(true);
        musicOverviewerArtist.setSelected(true);

        musicPlayerControllerFragment = (MusicPlayerControllerFragment) fm.findFragmentById(R.id.floatingControlFragment);
        musicPlayerControllerFragment.setMusicArrayList(musicArrayList);
        musicPlayerControllerFragment.setMusicPosition(0);

        floatingControlImage = (ImageView) findViewById(R.id.floatingControlImage);

        if (musicPlayerControllerFragment.isServiceBound()) {
            setUpControllerFragmentDisplay();
        }

        TextView titleOfArtist = (TextView) musicPlayerControllerFragment.getView().findViewById(R.id.player_titleOfArtist);
        titleOfArtist.setTextColor(Color.parseColor("#ffffff"));

        TextView titleOfMusic = (TextView) musicPlayerControllerFragment.getView().findViewById(R.id.player_titleOfMusic);
        titleOfMusic.setTextColor(Color.parseColor("#ffffff"));

        tabs = (SmartTabLayout) findViewById(R.id.mainViewPagerTabs);
        tabs.setDistributeEvenly(false);
        tabs.setViewPager(vp);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("STACK!", "OnSaveInstanceState MainActivity");
        outState.putParcelableArrayList("MusicArrayList", musicArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sharedPreferences.getBoolean("firstrun", true)){
            Intent i = new Intent(this, MoodClassifierService.class);
            startService(i);
            sharedPreferences.edit().putBoolean("firstrun", false).apply();
        }
    }

    public void toggleController(View view) {
        setUpControllerFragmentDisplay();
        if ((count >> 1) << 1 == count) {
            Animation slideInController = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
            Animation slideOutLayout = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
            linearLayout.setVisibility(View.VISIBLE);

            musicOverviewerLayout.startAnimation(slideOutLayout);
            linearLayout.startAnimation(slideInController);
            musicOverviewerLayout.setVisibility(View.GONE);
        } else {
            Animation slideOutController = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
            Animation slideInLayout = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);

            musicOverviewerLayout.startAnimation(slideInLayout);
            linearLayout.startAnimation(slideOutController);
            linearLayout.setVisibility(View.GONE);
            musicOverviewerLayout.setVisibility(View.VISIBLE);
        }
        ++count;
    }

    private void setUpControllerFragmentDisplay() {
        if (musicArrayList.size() != 0) {
            musicPlayerControllerFragment.setUpDisplay();
            Music music = MusicPlayerControllerFragment.getCurrentMusicObject();

            musicOverviewerTitle.setText(music.getName());
            musicOverviewerArtist.setText(music.getArtist());

            floatingControlImage.setImageBitmap(
                    MusicRetrieval.getAlbumArt(this,
                            music.getName(),
                            music.getAlbumID()));
            musicOverviewerImage.setImageBitmap(
                    Bitmap.createScaledBitmap(
                            MusicRetrieval.getAlbumArt(this,
                                    music.getName(),
                                    music.getAlbumID()),
                            DrawableBitmaps.dpToPx(70),
                            DrawableBitmaps.dpToPx(70),
                            false)
            );
        } else {
            musicOverviewerTitle.setText("-- NO MUSIC --");
            musicOverviewerArtist.setText("-- THUS NO MUSIC ARTIST --");
        }
    }

    public void togglePlay(View view) {
        musicPlayerControllerFragment.togglePlay();
    }

    public void nextMusic(View view) {
        musicPlayerControllerFragment.nextMusic();
        setUpControllerFragmentDisplay();
    }

    public void previousMusic(View view) {
        musicPlayerControllerFragment.previousMusic();
        setUpControllerFragmentDisplay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*notificationManager.notify(7,
                MusicNotification.notification(this, musicPlayerControllerFragment.getCurrentMusicObject()));*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("STACK!", "Entered OnDestroy MainActivity");
        vpAdapter = null;
        Titles = null;
        fm = null;
        vp = null;
        tabs = null;
        musicArrayList = null;
        musicRetrieval = null;
        databaseHandler = null;
        linearLayout = null;
        floatingControlImage = null;
        //controlPanelTrigger = null;
        musicPlayerControllerFragment = null;
        relativeLayout = null;
        System.gc();

    }

    public void onHappyButtonClicked(View view) {
        Intent intent = new Intent(this, MoodMusicActivity.class);
        intent.putExtra("mood", "HAPPY");
        startActivity(intent);
    }

    public void onSadButtonClicked(View view) {
        Intent intent = new Intent(this, MoodMusicActivity.class);
        intent.putExtra("mood", "SAD");
        startActivity(intent);
    }

    public void onEnergeticButtonClicked(View view) {
        Intent intent = new Intent(this, MoodMusicActivity.class);
        intent.putExtra("mood", "ENERGETIC");
        startActivity(intent);
    }
}
