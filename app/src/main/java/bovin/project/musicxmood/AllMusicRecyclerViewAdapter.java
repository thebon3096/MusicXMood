package bovin.project.musicxmood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bonny Haveliwala on 012 12 Mar 2016.
 */
public class AllMusicRecyclerViewAdapter extends
        RecyclerView.Adapter<AllMusicRecyclerViewAdapter.AllMusicViewHolder> {

    static Context context;
    static Music music;
    static View itemView;
    static ArrayList<Music> musicArrayList;
    static Bitmap albumArt;

    static class AllMusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView rowImage;
        TextView titleOfMusic;
        TextView nameOfArtist;
        TextView moodOfMusic;
        Intent intent;

        public AllMusicViewHolder(View itemView) {
            super(itemView);
            rowImage = (ImageView) itemView.findViewById(R.id.rowImage);
            titleOfMusic = (TextView) itemView.findViewById(R.id.titleOfMusic);
            nameOfArtist = (TextView) itemView.findViewById(R.id.nameOfArtist);
            moodOfMusic = (TextView) itemView.findViewById(R.id.moodOfMusic);
            titleOfMusic.setSelected(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            intent = new Intent(context, MusicPlayer.class);
            intent.putExtra("musicPosition", this.getLayoutPosition());
            intent.putParcelableArrayListExtra("musicArrayList", musicArrayList);
            context.startActivity(intent);
        }
    }

    public AllMusicRecyclerViewAdapter(Context context, ArrayList<Music> musicArrayList) {
        Log.i("STACK!", "Entered AllMusicRecyclerViewAdapter AllMusicRecyclerViewAdapter");
        this.context = context;
        this.musicArrayList = musicArrayList;
    }

    @Override
    public AllMusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_music_row, parent, false);
        return new AllMusicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllMusicViewHolder holder, int position) {
        music = musicArrayList.get(position);
        holder.titleOfMusic.setText(music.getName());
        holder.nameOfArtist.setText(music.getArtist());
        holder.moodOfMusic.setText(music.getMood());

        holder.rowImage.setImageBitmap(
                Bitmap.createScaledBitmap(
                        MusicRetrieval.getAlbumArt(context, music.getName(), music.getAlbumID()),
                        DrawableBitmaps.dpToPx(50), DrawableBitmaps.dpToPx(50),
                        false));
    }

    @Override
    public int getItemCount() {
        return musicArrayList.size();
    }
}
