package bovin.project.musicxmood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by Bonny Haveliwala on 012 12 Mar 2016.
 */
public class MusicRetrieval {

    ArrayList<Music> musicArrayList;
    Context context;
    private static DrawableBitmaps drawableBitmaps;
    DatabaseHandler databaseHandler;

    public MusicRetrieval(Context context) {
        this.context = context;
        drawableBitmaps = new DrawableBitmaps(context.getApplicationContext(), 50, 50);
        databaseHandler = new DatabaseHandler(context, null, null, 1);
    }

    class MusicRetrievalASyncTask extends AsyncTask<Void, Void, Void> {

        Activity activity = (Activity) context;

        @Override
        protected Void doInBackground(Void... params) {
            musicArrayList = databaseHandler.getAllMusic();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void params) {
            Intent i = new Intent(activity, MainActivity.class);
            i.putParcelableArrayListExtra("MusicArrayList", musicArrayList);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(i);
            activity.finish();
            super.onPostExecute(params);
        }
    }

    public static Bitmap getAlbumArt(Context context, String name, long albumId) {
        Bitmap albumArtBitmap = null;

        albumArtBitmap = DatabaseHandler.getAlbumart(context, albumId);
        if (albumArtBitmap == null) {
            albumArtBitmap = DrawableBitmaps.getLetterAlbumArt(name);
        }
        return albumArtBitmap;
    }

}
