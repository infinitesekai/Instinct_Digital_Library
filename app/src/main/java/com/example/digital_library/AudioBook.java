 package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.TimeUnit;

public class AudioBook extends AppCompatActivity {
    //declare variales
    TextView playerposition,playerduration;
    SeekBar seekBar;
    ImageView btnrew,btnplay,btnpause,btnff;
    Button notebtn,shufflebtn,morebtn;
    private User currentUser;
    private int lastfragment;
    MediaPlayer mediaPlayer;
    Handler handler=new Handler();
    Runnable runnable;
    TextView audiotitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_book);

        currentUser = (User) getIntent().getSerializableExtra("user");//get intent for current user
        lastfragment = 0;

        //bottom avigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //find view
        playerposition=findViewById(R.id.playerposition);
        playerduration=findViewById(R.id.playerduration);
        seekBar=findViewById(R.id.seekbar);
        btnplay=findViewById(R.id.btnplay);
        btnrew=findViewById(R.id.btnrew);
        btnpause=findViewById(R.id.btnpause);
        btnff=findViewById(R.id.btnff);
        notebtn=findViewById(R.id.note);
        audiotitle=findViewById(R.id.audio_title);

        shufflebtn=findViewById(R.id.shuffleau);
        morebtn=findViewById(R.id.moreau);

        //new instance
        mediaPlayer=new MediaPlayer();

        //generate random number for shuffle
        int shufflenum=getRandom(1,7);

        //create media player with different path according to the random number shuffled
        mediaPlayer=shuffleaudio(shufflenum);


        //note button
        //implicit intent-action send
        notebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text="Note down this audible. Here's " + audiotitle.getText().toString() +". Learn more here: "+ switchlink(shufflenum);
                Intent i=new Intent();
                i.setAction(i.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(i.EXTRA_TEXT,text);
                startActivity(Intent.createChooser(i,"Note down this audible!"));

            }

        });

        //initialize runnable
        runnable=new Runnable() {
            @Override
            public void run() {

                if(mediaPlayer!=null) {
                    //set progress on seekbar
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());

                    //handler post delay for 0.5 sec
                    handler.postDelayed(this, 500);
                }
            }
        };

        //get duration of media player
        int duration = mediaPlayer.getDuration();
        //convert ms to min and sec
        String strduration=convertFormat(duration);
        //set duration on text view
        playerduration.setText(strduration);


        //play button
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide play btn
                btnplay.setVisibility(View.GONE);
                //show pause btn
                btnpause.setVisibility(View.VISIBLE);
                //start media player
                mediaPlayer.start();
                //set max on seekbar
                seekBar.setMax(mediaPlayer.getDuration());
                //start handler
                handler.postDelayed(runnable,0);

            }
        });

        //pause button
        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide pause button
                btnpause.setVisibility(View.GONE);
                //show play button
                btnplay.setVisibility(View.VISIBLE);
                //pause media player
                mediaPlayer.pause();
                //stop handler
                handler.removeCallbacks(runnable);

            }
        });


        //fast forward button
        btnff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current position of media player
                int currentposition=mediaPlayer.getCurrentPosition();

                //get duration
                int duration=mediaPlayer.getDuration();

                //check condition
                if(mediaPlayer.isPlaying() && duration!= currentposition){
                    //fast forward 5 sec
                    currentposition=currentposition+5000;
                    //set current position on text view
                    playerposition.setText(convertFormat(currentposition));

                    //set progress on seekbar
                    mediaPlayer.seekTo(currentposition);
                }
            }
        });


        //rewind button
        btnrew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current position of media player
                int currentposition=mediaPlayer.getCurrentPosition();

                //get duration
                int duration=mediaPlayer.getDuration();

                //check condition
                if(mediaPlayer.isPlaying() && currentposition>5000){
                    //rewind 5 sec
                    currentposition=currentposition-5000;
                    //set current position on text view
                    playerposition.setText(convertFormat(currentposition));

                    //set progress on seekbar
                    mediaPlayer.seekTo(currentposition);
                }
            }
        });

        //seek bar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    //when drag seek bar
                    //set progress on seek bar
                    mediaPlayer.seekTo(progress);
                }

                //set current position on text view
                playerposition.setText(convertFormat(mediaPlayer.getCurrentPosition()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //on completion
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //hide pause button
                btnpause.setVisibility(View.GONE);

                //show play button
                btnplay.setVisibility(View.VISIBLE);

                //set media player to initial position
                mediaPlayer.seekTo(0);
            }
        });

        //shuffle button
        shufflebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMP(mediaPlayer);//clear media player

                //intent to the same page-shuffle with random number
                Intent i= new Intent(AudioBook.this, AudioBook.class);
                i.putExtra("user",currentUser);
                startActivity(i);
            }
        });


        //more button
        //implicit intent-action view to open a a link in web browser
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

    //function to generate random number
    private int getRandom(int min,int max) {
        return min + (int)(Math.random()*((max-min)+1));
    }

    //shuffle-set text according to the title
    //create media player with different file in raw folder according to shuffled number
    private MediaPlayer shuffleaudio(int shufflenum){
        MediaPlayer mp=MediaPlayer.create(this,R.raw.forgetme);;
        switch(shufflenum){
            case 1:
                audiotitle.setText("If You Forget Me");
                mp = MediaPlayer.create(this,R.raw.forgetme);
                break;
            case 2:
                audiotitle.setText("Romeo and Juliet");
                mp = MediaPlayer.create(this,R.raw.roju);
                break;
            case 3:
                audiotitle.setText("Prometheus");
                mp =MediaPlayer.create(this,R.raw.prome);
                break;
            case 4:
                audiotitle.setText("To Kill a Mockingbird");
                mp = MediaPlayer.create(this,R.raw.bird);
                break;
            case 5:
                audiotitle.setText("100 Love Sonnet: XVII");
                mp = MediaPlayer.create(this,R.raw.lovesonnet);
                break;
            case 6:
                audiotitle.setText("When You Are Old");
                mp = MediaPlayer.create(this,R.raw.old);
                break;
            case 7:
                audiotitle.setText("When We Two Parted");
                mp = MediaPlayer.create(this,R.raw.parted);
                break;
        }

        return mp;
    }

    //switch link-shuffle number to get the link for different video
    private String switchlink(int shufflenum){
        String link="";
        switch(shufflenum){
            case 1:
                link = "https://poemanalysis.com/pablo-neruda/if-you-forget-me/";
                break;
            case 2:
                link = "https://interestingliterature.com/2020/06/shakespeare-romeo-juliet-summary-analysis/";
                break;
            case 3:
                link = "https://www.litcharts.com/poetry/lord-byron/prometheus";
                break;
            case 4:
                link = "https://www.sparknotes.com/lit/mocking/plot-analysis/";
                break;
            case 5:
                link = "https://poemanalysis.com/pablo-neruda/one-hundred-love-sonnets-xvii/";
                break;
            case 6:
                link = "https://www.litcharts.com/poetry/william-butler-yeats/when-you-are-old";
                break;
            case 7:
                link = "https://www.litcharts.com/poetry/lord-byron/when-we-two-parted";
                break;


        }

        return link;
    }


    //comvert the format of duration to min:sec
    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration){
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration)-
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    private void clearMP(MediaPlayer mp){
        if(mp!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    //bottom navigation-clear mediaplayer when clicked
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    clearMP(mediaPlayer);
                    selectedFragment = new HomePage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    clearMP(mediaPlayer);
                    selectedFragment = new Profile();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_fav:
                    clearMP(mediaPlayer);
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