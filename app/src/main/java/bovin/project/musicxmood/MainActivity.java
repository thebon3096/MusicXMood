package bovin.project.musicxmood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Bonny Haveliwala on 009 9 Mar 2016.
 */
public class MainActivity extends AppCompatActivity{

    ViewPagerAdapter vpAdapter;
    CharSequence[] Titles={"All Music", "Artists"};
    FragmentManager fm;
    ViewPager vp;
    SmartTabLayout tabs;
    ArrayList<Music> musicArrayList;
    MusicRetrieval musicRetrieval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("STACK!", "Entered OnCreate MainActivity");
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        Intent intent = getIntent();
        if(savedInstanceState == null)
            musicArrayList =  intent.getParcelableArrayListExtra("MusicArrayList");
        else{
            musicArrayList = savedInstanceState.getParcelableArrayList("MusicArrayList");
            musicRetrieval = new MusicRetrieval(getApplicationContext());
        }


        vpAdapter = new ViewPagerAdapter(getApplicationContext(), fm, Titles, Titles.length, musicArrayList);
        vp = (ViewPager)findViewById(R.id.mainViewPager);
        vp.setAdapter(vpAdapter);

        tabs = (SmartTabLayout) findViewById(R.id.mainViewPagerTabs);
        tabs.setDistributeEvenly(false);
        /*tabs.setCustomTabColorizer(new SmartTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });
*/
        tabs.setViewPager(vp);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("STACK!", "OnSaveInstanceState MainACtivity");
        outState.putParcelableArrayList("MusicArrayList", musicArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        Log.i("STACK!", "Entered OnDestroy MainActivity");
        super.onDestroy();
    }
}
