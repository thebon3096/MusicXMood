package bovin.project.musicxmood;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LongSparseArray;
import android.webkit.MimeTypeMap;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Bonny Haveliwala on 005 5 Apr 2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mxm.db";
    private static final String MUSIC_TABLE = "music";
    private static final String MOOD_TABLE = "mood";

    private static final String COLUMN_ID = "COLUMN_ID";
    private static final String MUSIC_ID = "MUSIC_ID";
    private static final String MUSIC_TITLE = "MUSIC_TITLE";
    private static final String MUSIC_ARTIST = "MUSIC_ARTIST";
    private static final String MUSIC_DURATION = "MUSIC_DURATION";
    private static final String MUSIC_MOOD = "MUSIC_MOOD";

    private SQLiteDatabase db;
    private Cursor cursor;
    private String artist, title, path, mood, album;
    private long duration, id, albumID;
    private ArrayList<Music> musicArrayList;
    private ArrayList<String> stringArrayList;
    private Context context;

    private String sortOrder, selection, mimeTypeFromExtension;
    private String[] selectionArgs, projection;
    private Uri externalMusicUri;
    private int titleColumn, artistColumn, idColumn, durationColumn, pathColumn, albumColumn, albumIDColumn;

    Music music;

    private static int rowsAffected = 0;
    private static ContentValues contentValues;

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        musicArrayList = new ArrayList<Music>();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("STACK!", "Insde OnCreate SQLITE");
        String createMoodTableQuery = "CREATE TABLE " + MOOD_TABLE + "(" +
                COLUMN_ID + " INTEGER AUTO INCREMENT, " +
                MUSIC_ID + " LONG PRIMARY KEY, " +
                MUSIC_MOOD + " TEXT " +
                ")";

        db.execSQL(createMoodTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<String> getAllArtists() {

        stringArrayList = new ArrayList<>();
        externalMusicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        projection = new String[]{"DISTINCT " + MediaStore.Audio.Media.ARTIST};
        selection = MediaStore.Audio.Media.IS_MUSIC + "!=0 AND " + MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        sortOrder = MediaStore.Audio.Media.ARTIST + " ASC";
        mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3");
        selectionArgs = new String[]{mimeTypeFromExtension};

        ContentResolver musicResolver = context.getContentResolver();
        Cursor externalMusicCursor = musicResolver.query(externalMusicUri, projection, selection, selectionArgs, sortOrder);

        if (externalMusicCursor != null && externalMusicCursor.moveToFirst()) {
            artistColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                artist = externalMusicCursor.getString(artistColumn);
                stringArrayList.add(artist);
            } while (externalMusicCursor.moveToNext());
        }
        if (externalMusicCursor != null) {
            externalMusicCursor.close();
        }
        return stringArrayList;
    }

    public ArrayList<Music> getAllMusic() {

        musicArrayList = new ArrayList<>();
        externalMusicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        projection = new String[]{MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA};
        selection = MediaStore.Audio.Media.IS_MUSIC + "!=0 AND "
                + MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3");
        selectionArgs = new String[]{mimeTypeFromExtension};

        ContentResolver musicResolver = context.getContentResolver();
        Cursor externalMusicCursor = musicResolver.query(externalMusicUri, projection, selection, selectionArgs, sortOrder);

        if (externalMusicCursor != null && externalMusicCursor.moveToFirst()) {
            titleColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            artistColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            idColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            albumColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            albumIDColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            durationColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            pathColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                id = externalMusicCursor.getLong(idColumn);
                title = externalMusicCursor.getString(titleColumn);
                artist = externalMusicCursor.getString(artistColumn);
                album = externalMusicCursor.getString(albumColumn);
                duration = externalMusicCursor.getLong(durationColumn);
                albumID = externalMusicCursor.getLong(albumIDColumn);
                path = externalMusicCursor.getString(pathColumn);

                music = new Music(context, id, title, artist, album, path, null, duration, albumID);
                musicArrayList.add(music);
            } while (externalMusicCursor.moveToNext());
        }
        if (externalMusicCursor != null) {
            externalMusicCursor.close();
        }
        return musicArrayList;
    }

    public ArrayList<Music> getArtistMusic(String artistName) {
        musicArrayList = new ArrayList<>();
        externalMusicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        projection = new String[]{MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA};
        selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0 AND "
                + MediaStore.Files.FileColumns.MIME_TYPE + "= ? AND "
                + MediaStore.Audio.Media.ARTIST + "= ?";
        sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3");
        selectionArgs = new String[]{mimeTypeFromExtension, artistName};

        ContentResolver musicResolver = context.getContentResolver();
        Cursor externalMusicCursor = musicResolver.query(externalMusicUri, projection, selection, selectionArgs, sortOrder);

        if (externalMusicCursor != null && externalMusicCursor.moveToFirst()) {
            titleColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            artistColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            idColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            albumColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            albumIDColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            durationColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            pathColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                id = externalMusicCursor.getLong(idColumn);
                title = externalMusicCursor.getString(titleColumn);
                artist = externalMusicCursor.getString(artistColumn);
                album = externalMusicCursor.getString(albumColumn);
                duration = externalMusicCursor.getLong(durationColumn);
                albumID = externalMusicCursor.getLong(albumIDColumn);
                path = externalMusicCursor.getString(pathColumn);

                music = new Music(context, id, title, artist, album, path, null, duration, albumID);
                musicArrayList.add(music);
            } while (externalMusicCursor.moveToNext());
        }
        if (externalMusicCursor != null) {
            externalMusicCursor.close();
        }
        return musicArrayList;
    }

    public static Bitmap getAlbumart(Context context, Long album_id) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd != null) {
                FileDescriptor fd = pfd.getFileDescriptor();
                bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
                pfd = null;
                fd = null;
            }
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }

    public void insertMoodIntoMoodTable(long musicID, String mood){
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MUSIC_ID, musicID);
        contentValues.put(MUSIC_MOOD, mood);
        db.insertWithOnConflict(MOOD_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public ArrayList<String> getMusicIDsOfMood(String mood){
        ArrayList<String> musicIDs = new ArrayList<>();
        db = getReadableDatabase();

        final String QUERY = "SELECT * FROM " +MOOD_TABLE+ " WHERE " +MUSIC_MOOD+ " = ?";
        final String[] args = {mood};

        Cursor cursor = db.rawQuery(QUERY, args);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do{
                musicIDs.add(Long.toString(cursor.getLong(cursor.getColumnIndex(MUSIC_ID))));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return musicIDs;
    }

    public ArrayList<Music> getMusicArrayListOfMood(String mood){
        ArrayList<String> musicIDsArrayList = getMusicIDsOfMood(mood);
        String[] musicIDs = new String[musicIDsArrayList.size()];
        musicIDs = musicIDsArrayList.toArray(musicIDs);

        musicArrayList = new ArrayList<>();

        externalMusicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        projection = new String[]{MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA};
        selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND "
                + MediaStore.Audio.Media._ID + " IN ( "+ makePlaceholders(musicIDs.length) +" )";
        sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        ContentResolver musicResolver = context.getContentResolver();
        Cursor externalMusicCursor = musicResolver.query(externalMusicUri, projection, selection, musicIDs, sortOrder);

        if (externalMusicCursor != null && externalMusicCursor.moveToFirst()) {
            titleColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            artistColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            idColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            albumColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            albumIDColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            durationColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            pathColumn = externalMusicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                id = externalMusicCursor.getLong(idColumn);
                title = externalMusicCursor.getString(titleColumn);
                artist = externalMusicCursor.getString(artistColumn);
                album = externalMusicCursor.getString(albumColumn);
                duration = externalMusicCursor.getLong(durationColumn);
                albumID = externalMusicCursor.getLong(albumIDColumn);
                path = externalMusicCursor.getString(pathColumn);

                music = new Music(context, id, title, artist, album, path, mood, duration, albumID);
                musicArrayList.add(music);
            } while (externalMusicCursor.moveToNext());
        }
        if (externalMusicCursor != null) {
            externalMusicCursor.close();
        }
        return musicArrayList;
    }

    String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }
}
