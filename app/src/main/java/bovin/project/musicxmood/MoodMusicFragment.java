package bovin.project.musicxmood;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Bonny Haveliwala on 027 27 Apr 2016.
 */
public class MoodMusicFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private View view;
    private ImageButton sadMusicButton, happyMusicButton, energeticMusicButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mood_music_fragment, container, false);
        sadMusicButton = (ImageButton)view.findViewById(R.id.sadMusicButton);
        happyMusicButton = (ImageButton)view.findViewById(R.id.happyMusicButton);
        energeticMusicButton = (ImageButton)view.findViewById(R.id.energeticMusicButton);
        view.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sadMusicButton:
                ((MainActivity)context).onSadButtonClicked(v);
                break;
            case R.id.happyMusicButton:
                ((MainActivity)context).onHappyButtonClicked(v);
                break;
            case R.id.energeticMusicButton:
                ((MainActivity)context).onEnergeticButtonClicked(v);
                break;
        }
    }
}
