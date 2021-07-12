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
    public CardView academic_qualification, exam_check, personality_check,tertiary_info,check_in, exam_timetable;
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
        academic_qualification = (CardView)v.findViewById(R.id.academic_qualification);
        exam_check = (CardView)v.findViewById(R.id.exam_check);
        personality_check = (CardView)v.findViewById(R.id.personality_test);
        check_in = (CardView)v.findViewById(R.id.check_in);

        volume = (AppCompatImageView) v.findViewById(R.id.volume_std);

        //set the video announcement
        videoView = (VideoView)v.findViewById(R.id.news_std);
        String videoPath =
                "android.resource://" +
                        requireActivity().getPackageName() +
                        "/" +
                        R.raw.usaha_selenggara_encoded;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        videoView.start();

        videoView.setOnPreparedListener(this::PreparedListener);


        //on click listener on card view
        academic_qualification.setOnClickListener(this);
        exam_check.setOnClickListener(this);
        personality_check.setOnClickListener(this);
        check_in.setOnClickListener(this);




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
//            case R.id.academic_qualification:
//                i= new Intent(getActivity(), Academic_Qualification.class);
//                i.putExtra("user",currentUser);
//                i.putExtra("ICNo", currentUser.getICNo());
//                startActivity(i);
//                break;
//            case R.id.exam_check:
//                i = new Intent(getActivity(), ExamResultForm.class);
//                i.putExtra("user",currentUser);
//                i.putExtra("ICNo", currentUser.getICNo());
//                startActivity(i);
//                break;
//            case R.id.personality_test:
//                i=new Intent(getActivity(), PersonalityTestHome.class);
//                i.putExtra("user",currentUser);
//                i.putExtra("ICNo", currentUser.getICNo());
//                startActivity(i);
//                break;
//
//            case R.id.check_in:
//                i=new Intent(getActivity(), Check_in_scan.class);
//                i.putExtra("user",currentUser);
//                startActivity(i);
//                break;
//


        }
    }
}