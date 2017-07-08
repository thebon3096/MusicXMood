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
public class AllMusicFragment extends Fragment {

    private Context context;
    private AllMusicRecyclerViewAdapter allMusicAdapter;
    private ArrayList<Music> allMusicArrayList;
    private VerticalRecyclerViewFastScroller fastScroller;
    private View allSongsFragmentView;

    @Override
    public void onAttach(Context context) {
        Log.i("STACK!", "Entered OnAttach AllMusicFragment");
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("STACK!", "Entered OnCreate AllMusicFragment");
        allMusicAdapter = new AllMusicRecyclerViewAdapter(context, allMusicArrayList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("STACK!", "Entered OnCreateView AllMusicFragment");
        allSongsFragmentView = inflater.inflate(R.layout.all_music_fragment, container, false);

        RecyclerView allMusicRecyclerView = (RecyclerView) allSongsFragmentView.findViewById(R.id.allMusicRecyclerView);
        fastScroller = (VerticalRecyclerViewFastScroller) allSongsFragmentView.findViewById(R.id.fastScroller);

        allMusicRecyclerView.setAdapter(allMusicAdapter);
        allMusicRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fastScroller.setRecyclerView(allMusicRecyclerView);
        allMusicRecyclerView.addOnScrollListener(fastScroller.getOnScrollListener());
        return allSongsFragmentView;
    }

    public void setAllMusicArrayList(ArrayList<Music> allMusicArrayList) {
        Log.i("STACK!", "Entered setAllMusicArrayList AllMusicFragment");
        this.allMusicArrayList = allMusicArrayList;
    }

    @Override
    public void onDestroyView() {
        Log.i("STACK!", "Entered OnDestroy AllMusicFragment");
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("MusicArrayList", allMusicArrayList);
        super.onSaveInstanceState(outState);
    }
}
