package bovin.project.musicxmood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Bonny Haveliwala on 002 2 Apr 2016.
 */
public class MusicPlayer extends AppCompatActivity{

    Intent intent;
    TextView titleOfMusic, titleOfArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_interface);

        titleOfMusic = (TextView)findViewById(R.id.player_titleOfMusic);
        titleOfArtist = (TextView)findViewById(R.id.player_titleOfArtist);

        intent = getIntent();

        titleOfMusic.setText(intent.getStringExtra("titleOfMusic"));
        titleOfArtist.setText(intent.getStringExtra("titleOfArtist"));

        titleOfMusic.setSelected(true);
        titleOfArtist.setSelected(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
