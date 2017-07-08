package bovin.project.musicxmood;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created by Bonny Haveliwala on 020 20 Apr 2016.
 */
public class MusicNotification {

    public static Context context;
    public static int count=0;

    public static final String NOTIFY_PREV_MUSIC = "bovin.project.musicxmood.previous";
    public static final String NOTIFY_PLAY_MUSIC = "bovin.project.musicxmood.play";
    public static final String NOTIFY_NEXT_MUSIC = "bovin.project.musicxmood.next";
    public static final String NOTIFY_EXIT_MUSIC = "bovin.project.musicxmood.exit";

    public static Notification notification(Context cntxt, Music music){

        context = cntxt;

        RemoteViews smallContentView = new RemoteViews(context.getPackageName(), R.layout.music_notification);
        RemoteViews bigContentView = new RemoteViews(context.getPackageName(), R.layout.music_expanded_notification);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle(music.getName()).build();

        setListeners(smallContentView);
        setListeners(bigContentView);

        notification.contentView = smallContentView;
        if(currentVersionSupportBigNotification()){
            notification.bigContentView = bigContentView;
        }

        notification.contentView.setTextViewText(R.id.notifTitle, music.getName());
        notification.contentView.setTextViewText(R.id.notifArtist, music.getArtist());
        notification.contentView.setImageViewBitmap(R.id.notifAlbumArt,
                MusicRetrieval.getAlbumArt(
                        context, music.getName(), music.getAlbumID()));
        if(currentVersionSupportBigNotification()){
            notification.bigContentView.setTextViewText(R.id.notifTitle, music.getName());
            notification.bigContentView.setTextViewText(R.id.notifArtist, music.getArtist());
            notification.bigContentView.setTextViewText(R.id.notifMood, music.getMood());
            notification.bigContentView.setImageViewBitmap(R.id.notifAlbumArt,
                    MusicRetrieval.getAlbumArt(
                            context, music.getName(), music.getAlbumID()));
        }

        return notification;
    }

    private static void setListeners(RemoteViews view) {

        Intent previous = new Intent(NOTIFY_PREV_MUSIC);
        Intent play = new Intent(NOTIFY_PLAY_MUSIC);
        play.putExtra("count", count);
        Intent next = new Intent(NOTIFY_NEXT_MUSIC);
        Intent exit = new Intent(NOTIFY_EXIT_MUSIC);
        Intent playerActivity = new Intent(context, MusicPlayer.class);

        PendingIntent pPlayerActivity = PendingIntent.getActivity(context, 0, playerActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pExit = PendingIntent.getBroadcast(context, 0, exit, PendingIntent.FLAG_UPDATE_CURRENT);

        view.setOnClickPendingIntent(R.id.notifPrevious, pPrevious);
        view.setOnClickPendingIntent(R.id.notifPlay, pPlay);
        view.setOnClickPendingIntent(R.id.notifNext, pNext);
        view.setOnClickPendingIntent(R.id.notifExit, pExit);
    }

    public static boolean currentVersionSupportBigNotification() {
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        if(sdkVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
            return true;
        }
        return false;
    }

}
