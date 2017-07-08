package bovin.project.musicxmood;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import bovin.project.musicxmood.beatIt.BPMCalculator;

/**
 * Created by Bonny Haveliwala on 023 23 Apr 2016.
 */
public class MoodClassifierService extends Service implements Runnable {

    private DatabaseHandler databaseHandler;
    private Thread bpmCalculatorThread;
    private ArrayList<Music> musicArrayList;
    private Music music;
    private Handler handler;
    private Runnable toastRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        bpmCalculatorThread = new Thread(this);
        databaseHandler = new DatabaseHandler(this, null, null, 0);
        musicArrayList = databaseHandler.getAllMusic();
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "MoodClassifierService Started", Toast.LENGTH_SHORT).show();
        bpmCalculatorThread.setPriority(Thread.MAX_PRIORITY);
        bpmCalculatorThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void run() {
        //Toast.makeText(this, "Inside Run", Toast.LENGTH_SHORT);
        Notification notification = createNotification();
        startForeground(101, notification);
        String mood;
        for (int i = 0; i < musicArrayList.size(); i++) {
            try {
                music = musicArrayList.get(i);
                mood = getMood(BPMCalculator.calculateBPM(music.getPath()));
                Log.i("CLASSIFIER", music.getName()+ " | " +mood);
                databaseHandler.insertMoodIntoMoodTable(music.get_ID(), mood);
                toastNotification(music.getName(), mood);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stopForeground(true);
    }

    public String getMood(int bpm) {
        if (0<bpm && bpm<80){
            return "SAD";
        }else if(bpm < 120){
            return "HAPPY";
        }else{
            return "ENERGETIC";
        }
    }

    public Notification createNotification(){
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Music is being classified.")
                .setSubText("It may take long!")
                .setAutoCancel(true)
                .build();
        return notification;
    }

    private void toastNotification(final String name, final String mood){
        toastRunnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MoodClassifierService.this, name +" "+ mood, Toast.LENGTH_SHORT).show();
            }
        };
        handler.post(toastRunnable);
        toastRunnable = null;
    }
}
