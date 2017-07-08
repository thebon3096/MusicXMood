package bovin.project.musicxmood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MoodClassifierBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent moodClassifierIntent = new Intent(context, MoodClassifierService.class);
        context.startService(moodClassifierIntent);
    }

}
