package bovin.project.musicxmood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Bonny Haveliwala on 020 20 Apr 2016.
 */
public class MusicControlBroadcastReceiver extends BroadcastReceiver {

    public static final String NOTIFY_PREV_MUSIC = "bovin.project.musicxmood.previous";
    public static final String NOTIFY_PLAY_MUSIC = "bovin.project.musicxmood.play";
    public static final String NOTIFY_NEXT_MUSIC = "bovin.project.musicxmood.next";
    public static final String NOTIFY_EXIT_MUSIC = "bovin.project.musicxmood.exit";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(NOTIFY_PREV_MUSIC)){
            MusicPlaybackService.musicPlaybackService.playPreviousMusic();
        }else if(intent.getAction().equals(NOTIFY_PLAY_MUSIC)){
            togglePlay();
        }else if (intent.getAction().equals(NOTIFY_NEXT_MUSIC)){
            MusicPlaybackService.musicPlaybackService.playNextMusic();
        }else if (intent.getAction().equals(NOTIFY_EXIT_MUSIC)){
            MusicPlaybackService.musicPlaybackService.stopForeground(true);
            System.exit(0);
        }
    }

    public void togglePlay() {
        if (MusicPlaybackService.musicPlaybackService.isPlaying()) {
            MusicPlaybackService.musicPlaybackService.pause();
        } else {
            MusicPlaybackService.musicPlaybackService.start();
        }
    }
}
