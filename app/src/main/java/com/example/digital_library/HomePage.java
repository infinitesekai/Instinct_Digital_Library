package com.example.digital_library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class HomePage extends Fragment implements View.OnClickListener {

    public CardView academic_qualification, exam_check, personality_check,tertiary_info,check_in;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_homepage, container, false);

        academic_qualification = (CardView)v.findViewById(R.id.academic_qualification);
        exam_check = (CardView)v.findViewById(R.id.exam_check);
        personality_check = (CardView)v.findViewById(R.id.personality_test);
        check_in = (CardView)v.findViewById(R.id.check_in);
        tertiary_info = (CardView)v.findViewById(R.id.tertiary_info);


        academic_qualification.setOnClickListener(this);
        exam_check.setOnClickListener(this);
        personality_check.setOnClickListener(this);
        check_in.setOnClickListener(this);
        tertiary_info.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Intent i;

//        switch (v.getId()){
//            case R.id.academic_qualification:
//                i= new Intent(getActivity(), Academic_Qualification.class);
//                startActivity(i);
//                break;
//            case R.id.exam_check:
//                i = new Intent(getActivity(), ExamResultTable.class);
//                startActivity(i);
//                break;
//            case R.id.personality_test:
//                i=new Intent(getActivity(), PersonalityTestHome.class);
//                startActivity(i);
//                break;
//            case R.id.tertiary_info:
//                i=new Intent(getActivity(), Tertiary_Info.class);
//                startActivity(i);
//                break;
//            case R.id.check_in:
//                i=new Intent(getActivity(), Check_in_scan.class);
//                startActivity(i);
//                break;
//        }
    }
}