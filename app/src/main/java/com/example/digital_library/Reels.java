package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Reels extends AppCompatActivity {
    private static final String TAG = Reels.class.getSimpleName();
    private User currentUser;//current user
    private int lastfragment;//indicate last fragment for navigation bar
    VideoView reel;
    private boolean mVolumePlaying = false;
    AppCompatImageView volume;
    Button shufflebtn;
    TextView reeltitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reels);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        reeltitle= findViewById(R.id.reel_title);
        volume = findViewById(R.id.volume_std);
        shufflebtn=findViewById(R.id.shuffle);


        int shufflenum=getRandom(1,5);
        String path=shufflereel(shufflenum);



        //set the video announcement
        reel = findViewById(R.id.reel);

        Uri uri = Uri.parse(path);
        reel.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        reel.setMediaController(mediaController);
        reel.start();

        reel.setOnPreparedListener(this::PreparedListener);

        shufflebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Reels.this, Reels.class);
                i.putExtra("user",currentUser);
                startActivity(i);
//               String reelpath=shufflereel(getRandom(1,5));
//                Uri uri = Uri.parse(reelpath);
//                reel.setVideoURI(uri);

            }
        });

    }

    private int getRandom(int min,int max) {
        return min + (int)(Math.random()*((max-min)+1));
    }

    private String shufflereel(int shufflenum){
        String path="";
        switch(shufflenum){
            case 1:
                reeltitle.setText("Hamlet");
                path = "android.resource://" + this.getPackageName() + "/" + R.raw.hamlet;
                break;
            case 2:
                reeltitle.setText("A Midsummer Night's Dream");
                path = "android.resource://" + this.getPackageName() + "/" + R.raw.midsummer;
                break;
            case 3:
                reeltitle.setText("She Walks in Beauty");
                path = "android.resource://" + this.getPackageName() + "/" + R.raw.byron;
                break;
            case 4:
                reeltitle.setText("Les Misérables");
                path = "android.resource://" + this.getPackageName() + "/" + R.raw.lesmiz;
                break;
            case 5:
                reeltitle.setText("Jane Eyre");
                path = "android.resource://" + this.getPackageName() + "/" + R.raw.jane;
                break;

        }

        return path;
    }

    private void PreparedListener(MediaPlayer mp){
        try {
            mp.setVolume(0f, 0f);
            if (!mVolumePlaying){
                volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
            }
            mp.setLooping(true);
            mp.start();
            volume.setOnClickListener(v -> {
                if(mVolumePlaying) {
                    Log.d(TAG, "setVolume OFF");
                    volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    mp.setVolume(0F, 0F);
                } else {
                    Log.d(TAG, "setVolume ON");
                    volume.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    mp.setVolume(1F, 1F);
                }
                mVolumePlaying = !mVolumePlaying;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new Profile();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new FavouritePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };
}