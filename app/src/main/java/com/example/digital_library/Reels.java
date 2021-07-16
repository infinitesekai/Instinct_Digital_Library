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
    Button shufflebtn,notebtn,morebtn;
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
        notebtn=findViewById(R.id.notebutton);
        morebtn=findViewById(R.id.btnmore);


        int shufflenum=getRandom(1,10);
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


        notebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text="Note down this reel. Here's " + reeltitle.getText().toString() +". Learn more here: "+ switchlink(shufflenum);
                Intent i=new Intent();
                i.setAction(i.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(i.EXTRA_TEXT,text);
                startActivity(Intent.createChooser(i,"Note down this reel!"));

            }

        });

        morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=switchlink(shufflenum);
                Intent i=new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(Intent.createChooser(i,"Learn more."));

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
            case 6:
                reeltitle.setText("The Little Prince");
                path = "android.resource://" + this.getPackageName() + "/" + R.raw.lilprince;
                break;
            case 7:
                reeltitle.setText("Metamorphosis");
                path = "android.resource://" + this.getPackageName() + "/" + R.raw.morph;
                break;
            case 8:
                reeltitle.setText("The Prince and the Pauper");
                path = "android.resource://" + this.getPackageName() + "/" + R.raw.princepauper;
                break;
            case 9:
                reeltitle.setText("Robinson Crusoe");
                path = "android.resource://" + this.getPackageName() + "/" + R.raw.robinson;
                break;
            case 10:
                reeltitle.setText("The Wizard of Oz");
                path = "android.resource://" + this.getPackageName() + "/" + R.raw.wizardoz;
                break;

        }

        return path;
    }

    private String switchlink(int shufflenum){
        String link="";
        switch(shufflenum){
            case 1:
                link = "https://www.shakespeare.org.uk/explore-shakespeare/shakespedia/shakespeares-plays/hamlet/";
                break;
            case 2:
                link = "https://www.shakespeare.org.uk/explore-shakespeare/shakespedia/shakespeares-plays/midsummer-nights-dream/";
                break;
            case 3:
                link = "https://www.litcharts.com/poetry/lord-byron/she-walks-in-beauty";
                break;
            case 4:
                link = "https://www.penguinrandomhouse.com/books/326583/les-miserables-by-victor-hugo/9781598876994/readers-guide/";
                break;
            case 5:
                link = "https://www.britannica.com/topic/Jane-Eyre-novel-by-Bronte";
                break;
            case 6:
                link = "https://www.britannica.com/topic/The-Little-Prince";
                break;
            case 7:
                link = "https://interestingliterature.com/2021/05/franz-kafka-the-metamorphosis-summary-analysis/";
                break;
            case 8:
                link = "https://www.litcharts.com/lit/the-prince-and-the-pauper/summary";
                break;
            case 9:
                link = "https://interestingliterature.com/2021/02/defoe-robinson-crusoe-summary-analysis/";
                break;
            case 10:
                link = "https://www.sparknotes.com/lit/wonderful-wizard-of-oz/summary/";
                break;

        }

        return link;
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
                case R.id.nav_fav:
                    selectedFragment = new FavouritePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_fav;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };
}