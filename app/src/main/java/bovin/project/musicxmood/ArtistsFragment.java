package bovin.project.musicxmood;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

/**
 * Created by Bonny Haveliwala on 010 10 Mar 2016.
 */
public class ArtistsFragment extends Fragment {

    private Context context;
    private RecyclerView artistRecyclerView;
    private ArtistsRecyclerViewAdapter artistsRecyclerViewAdapter;
    private VerticalRecyclerViewFastScroller artistFastScroller;
    private View artistFragmentView;
    private ArrayList<String> artistArrayList;
    private DatabaseHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("STACK!", "Entered OnCreate ArtistsFragment");
        db = new DatabaseHandler(context, null, null, 0);

        if(savedInstanceState != null)
            artistArrayList = savedInstanceState.getStringArrayList("artistArrayList");
        else
            artistArrayList = db.getAllArtists();
        artistsRecyclerViewAdapter = new ArtistsRecyclerViewAdapter(context, artistArrayList);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.i("STACK!", "Entered OnAttach ArtistsFragment");
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("STACK!", "Entered OnCreateView ArtistFragment");

        artistFragmentView = inflater.inflate(R.layout.artists_fragment, container, false);
        artistRecyclerView = (RecyclerView) artistFragmentView.findViewById(R.id.artistsRecyclerView);
        artistRecyclerView.setAdapter(artistsRecyclerViewAdapter);
        artistRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        artistFastScroller = (VerticalRecyclerViewFastScroller) artistFragmentView.findViewById(R.id.artistsFastScroller);
        artistFastScroller.setRecyclerView(artistRecyclerView);
        artistRecyclerView.addOnScrollListener(artistFastScroller.getOnScrollListener());

        return artistFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("STACK!", "Entered OnSavedInstanceState ArtistFragment");
        outState.putStringArrayList("artistArrayList", artistArrayList);
        super.onSaveInstanceState(outState);
    }
}
