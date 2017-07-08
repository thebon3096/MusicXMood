package bovin.project.musicxmood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bonny Haveliwala on 006 6 May 2016.
 */
public class MoodMusicRecyclerViewAdapter extends RecyclerView.Adapter<MoodMusicRecyclerViewAdapter.MoodMusicViewHolder> {

    private static ArrayList<Music> musicArrayList;
    private String moodSelected;
    private static Context context;
    private DatabaseHandler db;

    public MoodMusicRecyclerViewAdapter(Context context, String mood) {
        this.context = context;
        this.moodSelected = mood;
        db = new DatabaseHandler(context, null, null, 0);
        musicArrayList = db.getMusicArrayListOfMood(mood);
    }

    @Override
    public MoodMusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_music_row, parent, false);
        return new MoodMusicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoodMusicViewHolder holder, int position) {
        Music music = musicArrayList.get(position);
        holder.titleOfMusic.setText(music.getName());
        holder.nameOfArtist.setText(music.getArtist());
        holder.moodOfMusic.setText(music.getMood());
        holder.rowImage.setImageBitmap(Bitmap.createScaledBitmap(
                MusicRetrieval.getAlbumArt(context, music.getName(), music.getAlbumID()),
                DrawableBitmaps.dpToPx(50), DrawableBitmaps.dpToPx(50),
                false));
    }

    @Override
    public int getItemCount() {
        return musicArrayList.size();
    }

    static class MoodMusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView titleOfMusic, nameOfArtist, moodOfMusic;
        private ImageView rowImage;
        private Intent intent;

        public MoodMusicViewHolder(View itemView) {
            super(itemView);
            titleOfMusic = (TextView)itemView.findViewById(R.id.titleOfMusic);
            nameOfArtist = (TextView)itemView.findViewById(R.id.nameOfArtist);
            moodOfMusic = (TextView)itemView.findViewById(R.id.moodOfMusic);
            rowImage = (ImageView)itemView.findViewById(R.id.rowImage);
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
}
