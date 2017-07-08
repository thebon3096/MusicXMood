package bovin.project.musicxmood;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Bonny Haveliwala on 012 12 Mar 2016.
 */
public class Music implements Parcelable {

    private long _ID;
    private String name;
    private String artist;
    private String mood;
    private long duration;
    private long albumID;
    private String album;
    private String path;
    Context context;

    public Music(Parcel parcel) {
        this._ID = parcel.readLong();
        this.name = parcel.readString();
        this.artist = parcel.readString();
        this.album = parcel.readString();
        this.path = parcel.readString();
        this.mood = parcel.readString();
        this.duration = parcel.readLong();
        this.albumID = parcel.readLong();
    }

    public Music(Context context, long _ID, String name, String artist, String album, String path,
                 String mood, long duration, long albumID) {
        this.context = context;
        this._ID = _ID;
        this.name = (name != null) ? name:"<Undefined>";
        this.artist = (artist != null) ? artist:"<Unknown>";
        this.mood = (mood != null) ? mood:"<Undefined>";
        this.duration = duration;
        this.albumID = albumID;
        this.album = album;
        this.path = path;
        // this.albumArtBitmap = albumArt;
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

   /* public Bitmap getAlbumArtBitmap(){
        return albumArtBitmap;
    }*/

    public long get_ID() {
        return _ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getMood() {
        return mood;
    }

    public long getDuration() {
        return duration;
    }

    public long getAlbumID() {
        return albumID;
    }

    public String getAlbum() {
        return album;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_ID);
        dest.writeString(name);
        dest.writeString(artist);
        dest.writeString(album);
        dest.writeString(path);
        dest.writeString(mood);
        dest.writeLong(duration);
        dest.writeLong(albumID);
    }
}