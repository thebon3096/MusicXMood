package bovin.project.musicxmood;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

/**
 * Created by Bonny Haveliwala on 010 10 Mar 2016.
 */
public class ArtistsFragment extends Fragment {

    Context context;
    RecyclerView artistRecyclerView;
    ArtistsRecyclerViewAdapter artistsRecyclerViewAdapter;
    ArrayList<Music> musicArrayList;
    VerticalRecyclerViewFastScroller artistFastScroller;
    View artistFragmentView;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("STACK!", "Entered OnSavedInstanceState ArtistFragment");
        outState.putParcelableArrayList("MusicArrayList", musicArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i("STACK!", "Entered OnCreateView ArtistFragment");

        artistFragmentView = (View)inflater.inflate(R.layout.artists_fragment, container, false);
        artistRecyclerView = (RecyclerView) artistFragmentView.findViewById(R.id.artistsRecyclerView);
        artistRecyclerView.setAdapter(artistsRecyclerViewAdapter);
        artistRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        artistFastScroller = (VerticalRecyclerViewFastScroller)artistFragmentView.findViewById(R.id.artistsFastScroller);
        artistFastScroller.setRecyclerView(artistRecyclerView);
        artistRecyclerView.addOnScrollListener(artistFastScroller.getOnScrollListener());

        return artistFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        Log.i("STACK!", "Entered OnAttach ArtistsFragment");
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("STACK!", "Entered OnCreate ArtistsFragment");
        if(savedInstanceState != null)
            musicArrayList = savedInstanceState.getParcelableArrayList("MusicArrayList");
        artistsRecyclerViewAdapter = new ArtistsRecyclerViewAdapter(context, musicArrayList);
        super.onCreate(savedInstanceState);
    }

    public void setAllMusicArrayList(ArrayList<Music> allMusicArrayList){
        this.musicArrayList = allMusicArrayList;
    }

    @Override
    public void onDestroyView() {
        Log.i("STACK!", "Entered OnDestroyView ArtistFragment");
        super.onDestroyView();
    }
}
