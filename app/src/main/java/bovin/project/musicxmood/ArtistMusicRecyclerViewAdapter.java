package bovin.project.musicxmood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by Bonny Haveliwala on 006 6 Apr 2016.
 */
public class ArtistMusicRecyclerViewAdapter
        extends ParallaxRecyclerAdapter<Music> {

    private static View view;
    private static ArrayList<Music> musicArrayList;
    private static Context context;
    private static Music music;

    public ArtistMusicRecyclerViewAdapter(Context context, ArrayList<Music> musicArrayList) {
        super(musicArrayList);
        this.context = context;
        this.musicArrayList = musicArrayList;
    }

    static class ArtistMusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView artistMusicFirstLetter, artistMusicName, artistMusicMood;
        ImageView artistMusicImageView;
        Intent intent;
        Music music;
        String artistMusicArtist;
        LinearLayout artistMusicBorderLayout;

        public ArtistMusicViewHolder(View itemView) {
            super(itemView);
            artistMusicFirstLetter = (TextView) itemView.findViewById(R.id.artistMusicFirstLetter);
            artistMusicName = (TextView) itemView.findViewById(R.id.artistMusicName);
            artistMusicMood = (TextView) itemView.findViewById(R.id.artistMusicMood);
            artistMusicImageView = (ImageView) itemView.findViewById(R.id.artistMusicImageView);
            artistMusicBorderLayout = (LinearLayout) itemView.findViewById(R.id.artistMusicBorderLayout);
            artistMusicArtist = musicArrayList.get(0).getArtist();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            intent = new Intent(context, MusicPlayer.class);
            intent.putExtra("titleOfMusic", artistMusicName.getText());
            intent.putExtra("nameOfArtist", artistMusicArtist);
            intent.putExtra("moodOfMusic", artistMusicMood.getText());
            intent.putExtra("musicPosition", this.getLayoutPosition() - 1);
            Log.i("temp", "MusicClickedPos: " + (this.getLayoutPosition() - 1));
            intent.putParcelableArrayListExtra("musicArrayList", musicArrayList);
            context.startActivity(intent);
        }
    }

    @Override
    public void onBindViewHolderImpl(RecyclerView.ViewHolder holder, ParallaxRecyclerAdapter<Music> parallaxRecyclerAdapter, int position) {
        music = musicArrayList.get(position);
        ((ArtistMusicViewHolder) holder).artistMusicName.setText(music.getName());
        ((ArtistMusicViewHolder) holder).artistMusicMood.setText("<Undefined>");
        ((ArtistMusicViewHolder) holder).artistMusicFirstLetter.setText(getFirstLetter(music.getName()));
        ((ArtistMusicViewHolder) holder).artistMusicImageView.setBackgroundColor(getRandomColor(position));
        ((ArtistMusicViewHolder) holder).artistMusicBorderLayout.setBackgroundColor(getRandomColor(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup parent, ParallaxRecyclerAdapter<Music> parallaxRecyclerAdapter, int i) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_music_row, parent, false);
        return new ArtistMusicViewHolder(view);
    }

    @Override
    public int getItemCountImpl(ParallaxRecyclerAdapter<Music> parallaxRecyclerAdapter) {
        return musicArrayList.size();
    }


    String getFirstLetter(String name) {
        return String.valueOf(name.charAt(0));
    }

    int getRandomColor(int pos) {
        String[] colors = {"#B71C1C", "#880E4F", "#4A148C", "#311B92", "#1A237E", "#0D47A1",
                "#01579B", "#006064", "#004D40", "#1B5E20", "#33691E", "#827717", "#F57F17",
                "#FF6F00", "#E65100", "#BF360C", "#3E2723", "#212121", "#263238"};
        return Color.parseColor(colors[pos % colors.length]);
    }

}
