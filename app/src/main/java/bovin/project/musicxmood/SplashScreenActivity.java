package bovin.project.musicxmood;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreenActivity extends Activity {

    private static int SPLASH_TIME_OUT = 4000;
    static MusicRetrieval musicRetrieval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("STACK!", "Entered onCreate SplashScreen");
        setContentView(R.layout.splash_screen);
        musicRetrieval = new MusicRetrieval(this);
        musicRetrieval.new MusicRetrievalASyncTask().execute();
    }

    @Override
    protected void onDestroy() {
        Log.i("STACK!", "Entered OnDestroy Splash");
        super.onDestroy();
    }
}
