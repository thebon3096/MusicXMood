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
    private int albumArtID;
    Context context;
    //private Bitmap albumArtBitmap;

    public Music(Parcel parcel){
        this._ID = parcel.readLong();
        this.name = parcel.readString();
        this.artist = parcel.readString();
        this.mood = parcel.readString();
        this.duration = parcel.readLong();
        //this.albumArtBitmap = parcel.readParcelable(getClass().getClassLoader());
    }

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Music(Context context, long _ID, String name, String artist, long duration, Bitmap albumArt){
        this.context = context;
        this._ID=_ID;
        this.name = name;
        this.artist = artist;
        this.mood = "<Undefined>";
        this.duration = duration;
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
    

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_ID);
        dest.writeString(name);
        dest.writeString(artist);
        dest.writeString(mood);
        dest.writeLong(duration);
        //dest.writeParcelable(albumArtBitmap, flags);
    }
}