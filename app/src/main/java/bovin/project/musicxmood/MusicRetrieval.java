package bovin.project.musicxmood;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bonny Haveliwala on 012 12 Mar 2016.
 */
public class MusicRetrieval {

    ArrayList<Music> musicArrayList;
    Context context;
    String sortOrder, selection, mimeTypeFromExtension;
    String[] selectionArgs, projection;
    Bitmap albumArt;
    Long id, duration;
    String title, artist, path;
    Uri externalMusicUri;
    int titleColumn, artistColumn, idColumn, durationColumn, pathColumn;
    private static DrawableBitmaps drawableBitmaps;

    public MusicRetrieval(Context context){
        this.context = context;
        musicArrayList = new ArrayList<>();
        externalMusicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        projection = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATA};
        selection = MediaStore.Audio.Media.IS_MUSIC + "!=0 AND " +MediaStore.Files.FileColumns.MIME_TYPE+ "=?" ;
        sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3");
        selectionArgs = new String[]{mimeTypeFromExtension};
        drawableBitmaps = new DrawableBitmaps(context.getApplicationContext(), 50, 50);
    }

    public ArrayList<Music> getAllMusic(){

        ContentResolver musicResolver = context.getContentResolver();
        Cursor externalMusicCursor = musicResolver.query(externalMusicUri, projection, selection, selectionArgs, sortOrder);
        //MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

        if(externalMusicCursor != null && externalMusicCursor.moveToFirst()){
            titleColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            artistColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            idColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            durationColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            pathColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            //int albumArtPathColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            do {
                id = externalMusicCursor.getLong(idColumn);
                title = externalMusicCursor.getString(titleColumn);
                artist = externalMusicCursor.getString(artistColumn);
                duration = externalMusicCursor.getLong(durationColumn);
                path = externalMusicCursor.getString(pathColumn);
                //Log.i("MediaResolver", path);
                /*ediaMetadataRetriever.setDataSource(path);
                albumArtArray = mediaMetadataRetriever.getEmbeddedPicture();
                if(albumArtArray!=null)
                    albumArt = scaledAlbumArt(albumArtArray);
                else*/
                //albumArt = null;
                musicArrayList.add(new Music(context, id, title, artist, duration, null));
               // albumArt.recycle();
            }while(externalMusicCursor.moveToNext());
        }
        if (externalMusicCursor != null) {
            externalMusicCursor.close();
        }
        return musicArrayList;
    }

    class MusicRetrievalASyncTask extends AsyncTask<Void, Void, ArrayList<Music>> {

        Activity activity = (Activity) context;

        @Override
        protected ArrayList<Music> doInBackground(Void... params) {
            musicArrayList = getAllMusic();
            return musicArrayList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Music> arrayList) {
            Intent i = new Intent(activity, MainActivity.class);
            i.putParcelableArrayListExtra("MusicArrayList", musicArrayList);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(i);
            activity.finish();
            super.onPostExecute(arrayList);
        }
    }

    public static Bitmap getAlbumArt(Context context, String name, Bitmap albumArtBitmap){
        if(albumArtBitmap != null) {
            // this.albumArt = new BitmapDrawable(context.getResources(), albumArtBitmap);
        }else{
            albumArtBitmap = MusicRetrieval.getAlbumArtLetter(name);
        }
        return albumArtBitmap;
    }

    private static Bitmap getAlbumArtLetter(String name){
        int i=0;
        char firstLetter = name.charAt(i);
        while(!(Character.isLetter(firstLetter)) && i < name.length()-1) {
            firstLetter = name.charAt(++i);
        }
        if(!(Character.isLetter(firstLetter))){
            return drawableBitmaps.getA();
        }

        switch(firstLetter){
            case 'a':
            case 'A':
                return drawableBitmaps.getA();
            case 'b':
            case 'B':
                return drawableBitmaps.getB();
            case 'c':
            case 'C':
                return drawableBitmaps.getC();
            case 'd':
            case 'D':
                return drawableBitmaps.getD();
            case 'e':
            case 'E':
                return drawableBitmaps.getE();
            case 'f':
            case 'F':
                return drawableBitmaps.getF();
            case 'g':
            case 'G':
                return drawableBitmaps.getG();
            case 'h':
            case 'H':
                return drawableBitmaps.getH();
            case 'i':
            case 'I':
                return drawableBitmaps.getI();
            case 'j':
            case 'J':
                return drawableBitmaps.getJ();
            case 'k':
            case 'K':
                return drawableBitmaps.getK();
            case 'l':
            case 'L':
                return drawableBitmaps.getL();
            case 'm':
            case 'M':
                return drawableBitmaps.getM();
            case 'n':
            case 'N':
                return drawableBitmaps.getN();
            case 'o':
            case 'O':
                return drawableBitmaps.getO();
            case 'p':
            case 'P':
                return drawableBitmaps.getP();
            case 'q':
            case 'Q':
                return drawableBitmaps.getQ();
            case 'r':
            case 'R':
                return drawableBitmaps.getR();
            case 's':
            case 'S':
                return drawableBitmaps.getS();
            case 't':
            case 'T':
                return drawableBitmaps.getT();
            case 'u':
            case 'U':
                return drawableBitmaps.getU();
            case 'v':
            case 'V':
                return drawableBitmaps.getV();
            case 'w':
            case 'W':
                return drawableBitmaps.getW();
            case 'x':
            case 'X':
                return drawableBitmaps.getX();
            case 'y':
            case 'Y':
                return drawableBitmaps.getY();
            case 'z':
            case 'Z':
                return drawableBitmaps.getZ();
        }
        return drawableBitmaps.getA();
        /*while(!(Character.isAlphabetic(firstLetter)) && i < name.length()-1) {
            firstLetter = name.charAt(++i);
        }
        if(!(Character.isLetter(firstLetter))){
            return R.drawable.splash_screen_1_small;
        }*/
        /*switch(firstLetter){
            case 'a':
            case 'A':
                return R.drawable.a;
            case 'b':
            case 'B':
                return R.drawable.b;
            case 'c':
            case 'C':
                return R.drawable.c;
            case 'd':
            case 'D':
                return R.drawable.d;
            case 'e':
            case 'E':
                return R.drawable.e;
            case 'f':
            case 'F':
                return R.drawable.f;
            case 'g':
            case 'G':
                return R.drawable.g;
            case 'h':
            case 'H':
                return R.drawable.h;
            case 'i':
            case 'I':
                return R.drawable.i;
            case 'j':
            case 'J':
                return R.drawable.j;
            case 'k':
            case 'K':
                return R.drawable.k;
            case 'l':
            case 'L':
                return R.drawable.l;
            case 'm':
            case 'M':
                return R.drawable.m;
            case 'n':
            case 'N':
                return R.drawable.n;
            case 'o':
            case 'O':
                return R.drawable.o;
            case 'p':
            case 'P':
                return R.drawable.p;
            case 'q':
            case 'Q':
                return R.drawable.q;
            case 'r':
            case 'R':
                return R.drawable.r;
            case 's':
            case 'S':
                return R.drawable.s;
            case 't':
            case 'T':
                return R.drawable.t;
            case 'u':
            case 'U':
                return R.drawable.u;
            case 'v':
            case 'V':
                return R.drawable.v;
            case 'w':
            case 'W':
                return R.drawable.w;
            case 'x':
            case 'X':
                return R.drawable.x;
            case 'y':
            case 'Y':
                return R.drawable.y;
            case 'z':
            case 'Z':
                return R.drawable.z;
        }
        return R.drawable.splash_screen_1_small;*/
    }

}
