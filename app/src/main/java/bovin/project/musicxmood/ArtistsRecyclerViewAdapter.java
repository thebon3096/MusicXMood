package bovin.project.musicxmood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Bonny Haveliwala on 030 30 Mar 2016.
 */
public class ArtistsRecyclerViewAdapter extends RecyclerView.Adapter<ArtistsRecyclerViewAdapter.ArtistsViewHolder> {

    private static Context context;
    private static ArrayList<String> artistArrayList;
    private static Music music;
    private static View itemView;
    private static DatabaseHandler db;

    static class ArtistsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView artistImage;
        TextView titleOfArtist;
        Intent intent;

        public ArtistsViewHolder(View itemView) {
            super(itemView);
            artistImage = (ImageView) itemView.findViewById(R.id.artistImage);
            titleOfArtist = (TextView) itemView.findViewById(R.id.titleOfArtist);
            titleOfArtist.setSelected(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            intent = new Intent(context, ArtistMusicActivity.class);
            intent.putExtra("titleOfArtist", titleOfArtist.getText());
            context.startActivity(intent);
        }
    }

    public ArtistsRecyclerViewAdapter(Context context, ArrayList<String> artistArrayList) {
        Log.i("STACK!", "Entered ArtistsRecyclerViewAdapter");
        this.db = new DatabaseHandler(context, null, null, 0);
        this.context = context;
        this.artistArrayList = artistArrayList;
    }

    @Override
    public ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artists_row, parent, false);
        return new ArtistsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArtistsViewHolder holder, int position) {
        holder.titleOfArtist.setText(artistArrayList.get(position));
        /*Picasso.with(context)
                .load(music.getAlbumArt(context, artistArrayList.get(position), null))
                .into(holder.artistImage);*/
        holder.artistImage.setImageBitmap(
                Bitmap.createScaledBitmap(
                        MusicRetrieval.getAlbumArt(context, artistArrayList.get(position), 0),
                        DrawableBitmaps.dpToPx(50), DrawableBitmaps.dpToPx(50),
                        false));
    }

    @Override
    public int getItemCount() {
        return artistArrayList.size();
    }

    @Override
    public void onViewDetachedFromWindow(ArtistsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }
}
