package com.example.digital_library;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.Objects;


public class HomePage extends Fragment implements View.OnClickListener {

    private static final String TAG = HomePage.class.getSimpleName();
    public CardView booklist, reel, physical,audio;
    private User currentUser;
    VideoView videoView;
    private boolean mVolumePlaying = false;
    AppCompatImageView volume;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_homepage, container, false);
        TextView nameText = v.findViewById(R.id.student_home_name);
        Bundle bundle = getArguments();

        //get bundle for current user
        currentUser = (User) bundle.getSerializable("user");
        nameText.setText("Hello, " + currentUser.getFirstname());

        //reference to view by id
        booklist = (CardView)v.findViewById(R.id.bookcollection);
        reel = (CardView)v.findViewById(R.id.reel);
        physical = (CardView)v.findViewById(R.id.physical);
        audio = (CardView)v.findViewById(R.id.audio);

        volume = (AppCompatImageView) v.findViewById(R.id.volume_std);

        //set the video announcement
        videoView = (VideoView)v.findViewById(R.id.news_std);
        String videoPath =
                "android.resource://" +
                        requireActivity().getPackageName() +
                        "/" +
                        R.raw.hamlet;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        videoView.start();

        videoView.setOnPreparedListener(this::PreparedListener);


        //on click listener on card view
        booklist.setOnClickListener(this);
        reel.setOnClickListener(this);
        physical.setOnClickListener(this);
        audio.setOnClickListener(this);




        return v;
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


    //start intent to navigate to respective page
    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.bookcollection:
                i= new Intent(getActivity(), BookGenre.class);
                i.putExtra("user",currentUser);
                startActivity(i);
                break;
            case R.id.reel:
                i = new Intent(getActivity(), Reels.class);
                i.putExtra("user",currentUser);
                startActivity(i);
                break;
            case R.id.physical:
                i=new Intent(getActivity(), PhysicalReq.class);
                i.putExtra("user",currentUser);
                startActivity(i);
                break;

            case R.id.audio:
                i=new Intent(getActivity(), AudioBook.class);
                i.putExtra("user",currentUser);
                startActivity(i);
                break;



        }
    }
}