package bovin.project.musicxmood;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Bonny Haveliwala on 010 10 Mar 2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    AllMusicFragment allMusicFragment;
    ArtistsFragment artistsFragment;
    CharSequence Titles[];
    int numOfTabs;
    Context context;
    ArrayList<Music> musicArrayList;

    public ViewPagerAdapter(Context context, FragmentManager fm, CharSequence Titles[], int numOfTabs, ArrayList<Music> musicArrayList){
        super(fm);
        this.Titles = Titles;
        this.numOfTabs = numOfTabs;
        this.context = context;
        this.musicArrayList = musicArrayList;

        Log.i("STACK!", "Entered ViewPagerAdapter");

        if(allMusicFragment == null)
            allMusicFragment = new AllMusicFragment();
        allMusicFragment.setAllMusicArrayList(musicArrayList);

        if(artistsFragment == null)
            artistsFragment = new ArtistsFragment();
        artistsFragment.setAllMusicArrayList(musicArrayList);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return allMusicFragment;
        }else {
            return artistsFragment;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

}



