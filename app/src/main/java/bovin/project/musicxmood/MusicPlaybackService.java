package bovin.project.musicxmood;

import android.app.Activity;
import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;

/**
 * Created by Bonny Haveliwala on 003 3 Apr 2016.
 */
public class MusicPlaybackService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,
        MediaController.MediaPlayerControl,
        SeekBar.OnSeekBarChangeListener,
        MediaPlayer.OnSeekCompleteListener,
        AudioManager.OnAudioFocusChangeListener {

    private ArrayList<Music> musicArrayList;
    private MediaPlayer mediaPlayer;
    private final IBinder musicBinder = new MusicBinder();
    private Music music;
    private int musicPosition;
    private long _ID;
    private Uri currentMusicUri;
    private AudioManager am;
    private int result;
    static Context context;
    public static MusicPlaybackService musicPlaybackService;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        musicPlaybackService = this;

        mediaPlayer = new MediaPlayer();

        am = (AudioManager) getApplicationContext().getSystemService(getApplicationContext().AUDIO_SERVICE);

        result = am.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            initMusicPlayer();
        } else {
            Toast.makeText(this, "Couldn't get Focus!", Toast.LENGTH_SHORT);
        }
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNextMusic();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    public void initMusicPlayer() {
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    public void setMusicArrayList(ArrayList<Music> musicArrayList) {
        this.musicArrayList = musicArrayList;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser)
            mediaPlayer.seekTo(progress);
        else {
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
            mediaPlayer.pause();
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            if(isPlaying()){
                mediaPlayer.start();
            }else{
                mediaPlayer.pause();
            }
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            am.abandonAudioFocus(this);
        }
    }

    public class MusicBinder extends Binder {
        MusicPlaybackService getService() {
            return MusicPlaybackService.this;
        }
    }

    public void setUpMusic() {
        if (musicArrayList.size() != 0) {
            music = musicArrayList.get(musicPosition);
            mediaPlayer.reset();
            _ID = music.get_ID();
            currentMusicUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, _ID);
            try {
                Log.i("temp",musicArrayList.get(musicPosition).getPath());
                mediaPlayer.setDataSource(context, currentMusicUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.prepareAsync();
            startForeground(1, MusicNotification.notification(this, musicArrayList.get(musicPosition)));
        } else {
            Toast.makeText(context, "Sorry, No Music found!", Toast.LENGTH_LONG).show();
        }
    }

    public void setMusicPosition(int musicPos) {
        musicPosition = musicPos;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    long getCurrentMusicID() {
        return musicArrayList.get(musicPosition).get_ID();
    }

    public void playNextMusic() {
        setMusicPosition((musicPosition == musicArrayList.size() - 1) ? musicPosition = 0 : ++musicPosition);
        if (getMusicPosition() == 0) {
            mediaPlayer.stop();
        }
        setUpMusic();
    }

    public void playPreviousMusic() {
        setMusicPosition((musicPosition == 0) ? (musicPosition = musicArrayList.size() - 1) : --musicPosition);
        if (getMusicPosition() == musicArrayList.size() - 1) {
            mediaPlayer.stop();
        }
        setUpMusic();
    }

    public int getMusicPosition() {
        return musicPosition;
    }

    public static String millisToMinute(int currentPosition) {
        int totalSeconds = currentPosition / 1000;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return minutes + ":" + seconds;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
