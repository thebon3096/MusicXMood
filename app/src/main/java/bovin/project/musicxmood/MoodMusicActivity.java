package bovin.project.musicxmood;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Bonny Haveliwala on 006 6 May 2016.
 */
public class MoodMusicActivity extends AppCompatActivity {

    private RecyclerView moodMusicRecyclerView;
    private String moodSelected;
    private MoodMusicRecyclerViewAdapter moodMusicRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood_layout);

        Intent intent = getIntent();
        moodSelected = intent.getStringExtra("mood");

        moodMusicRecyclerView = (RecyclerView)findViewById(R.id.moodMusicRecyclerView);
        moodMusicRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        moodMusicRecyclerViewAdapter = new MoodMusicRecyclerViewAdapter(this, moodSelected);
        moodMusicRecyclerView.setAdapter(moodMusicRecyclerViewAdapter);
    }
}
