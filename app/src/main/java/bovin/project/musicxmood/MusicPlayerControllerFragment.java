package bovin.project.musicxmood;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import android.os.Handler;
import android.widget.Toast;

public class MusicPlayerControllerFragment extends Fragment {

    View view;
    private int musicPosition;
    private TextView titleOfMusic;
    private TextView titleOfArtist;
    private TextView totalDuration;
    private TextView elapsedDuration;
    private ImageButton playButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private SeekBar seekBar;
    private static MusicPlaybackService musicPlaybackService;
    private Intent serviceIntent;
    private boolean isServiceBound = false;
    private ServiceConnection serviceConnection;
    private static ArrayList<Music> musicArrayList;
    private static int count = 0;
    private Context context;
    private Handler seekUpdationHandler;
    private Runnable updateProgress;

    @Override
    public void onAttach(Context context) {
        Log.i("Controller", "inside OnAttach Controller");
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("Controller", "inside OnCreate Controller");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicPlaybackService.MusicBinder musicBinder = (MusicPlaybackService.MusicBinder) service;
                musicPlaybackService = musicBinder.getService();
                if (musicArrayList != null) {
                    musicPlaybackService.setMusicArrayList(musicArrayList);
                    musicPlaybackService.setMusicPosition(musicPosition);
                    musicPlaybackService.setUpMusic();
                    seekBar.setOnSeekBarChangeListener(musicPlaybackService);
                    playButton.setImageResource(R.drawable.pausebtn);
                    seekBar.setMax(musicPlaybackService.getDuration());
                    seekUpdation();
                }
                isServiceBound = true;
                if (context instanceof MainActivity) {
                    musicPlaybackService.pause();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isServiceBound = false;
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Controller", "inside OnCreateView Contoller");
        view = inflater.inflate(R.layout.control_panel, container, false);

        titleOfMusic = (TextView) view.findViewById(R.id.player_titleOfMusic);
        titleOfArtist = (TextView) view.findViewById(R.id.player_titleOfArtist);
        totalDuration = (TextView) view.findViewById(R.id.totalDuration);
        elapsedDuration = (TextView) view.findViewById(R.id.elapsedDuration);

        playButton = (ImageButton) view.findViewById(R.id.playButton);
        prevButton = (ImageButton) view.findViewById(R.id.prevButton);
        nextButton = (ImageButton) view.findViewById(R.id.nextButton);

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        titleOfMusic.setSelected(true);
        titleOfArtist.setSelected(true);

        seekUpdationHandler = new Handler();
        updateProgress = new Runnable() {
            @Override
            public void run() {
                seekUpdation();
            }
        };
        return view;
    }

    @Override
    public void onStart() {
        Log.i("Controller", "inside OnStart Controller");
        super.onStart();
        if (serviceIntent == null) {
            serviceIntent = new Intent(context, MusicPlaybackService.class);
            context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            context.getApplicationContext().startService(serviceIntent);
        }
    }

    @Override
    public void onResume() {
        Log.i("Controller", "inside OnResume Contoller");
        if (musicPlaybackService != null && musicArrayList.size() != 0)
            setUpDisplay();
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.i("Controller", "inside onStop Controller " + context.getClass());
        if (isServiceBound) {
            context.unbindService(serviceConnection);
            isServiceBound = false;
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("Controller", "inside OnDestroyView Controller");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("Controller", "inside OnDestroy Controller");
        super.onDestroy();
    }

    public void togglePlay() {
        ++count;
        if ((count >> 1) << 1 != count) {
            musicPlaybackService.pause();
        } else {
            musicPlaybackService.start();
        }
        setUpDisplay();
    }

    public void nextMusic() {
        musicPlaybackService.playNextMusic();
        ++count;
        seekBar.setMax(musicPlaybackService.getDuration());
        setUpDisplay();
    }

    public void previousMusic() {
        musicPlaybackService.playPreviousMusic();
        ++count;
        seekBar.setMax(musicPlaybackService.getDuration());
        setUpDisplay();
    }

    public void setUpDisplay() {
        if (musicArrayList.size() != 0) {

            titleOfMusic.setText(getCurrentMusicObject().getName());
            titleOfArtist.setText(getCurrentMusicObject().getArtist());
            seekBar.setMax((int) getCurrentMusicObject().getDuration());
            totalDuration.setText(MusicPlaybackService.millisToMinute((int) getCurrentMusicObject().getDuration()));
            if (musicPlaybackService.isPlaying())
                playButton.setImageResource(R.drawable.pausebtn);
            else
                playButton.setImageResource(R.drawable.playbtn);
        } else {
            titleOfMusic.setText("<!-- NO MUSIC -->");
            titleOfArtist.setText("<!-- THUS NO MUSIC ARTIST -->");
        }
    }

    public void setTitleOfMusic(String stringTitleOfMusic) {
        titleOfMusic.setText(stringTitleOfMusic);
    }

    public void setTitleOfArtist(String stringTitleOfArtist) {
        titleOfArtist.setText(stringTitleOfArtist);
    }

    public void setMusicPosition(int musicPosition) {
        Log.i("temp", "CONTROLLER: " + musicPosition);
        this.musicPosition = musicPosition;
        if (musicPlaybackService != null)
            musicPlaybackService.setMusicPosition(musicPosition);
    }

    public void setMusicArrayList(ArrayList<Music> mArrayList) {
        musicArrayList = mArrayList;
    }

    public static Music getCurrentMusicObject() {
        if (musicArrayList.size() == 0)
            return null;
        return musicArrayList.get(musicPlaybackService.getMusicPosition());
    }

    public boolean isServiceBound() {
        return isServiceBound;
    }

    public void seekUpdation() {
        seekBar.setProgress(musicPlaybackService.getCurrentPosition());
        elapsedDuration.setText(MusicPlaybackService.millisToMinute(musicPlaybackService.getCurrentPosition()));
        //Toast.makeText(context, "pos: "+musicPlaybackService.getCurrentPosition(), Toast.LENGTH_SHORT).show();
        seekUpdationHandler.postDelayed(updateProgress, 1000);
    }
}
