package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.digital_library.util.Utils.getImage;

public class Profile extends Fragment {
    private Button editBtn,btnLogout;//edit child button,edit profile button,logout button
    private User currentUser;//current user
    private TextView emailText;
    private TextView firstnameText;
    private TextView lastnameText;
    private TextView genderText;
    private TextView phoneText;
    private TextView birthdateText ;
    private DatabaseAccess DB;//database
    private byte[] photoImage;
    private ImageView photo;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");
        //initiate database access and open database
        DB = DatabaseAccess.getInstance(getContext());
        DB.open();
        initViews(view);
        photoImage= DB.getPic(currentUser.getEmail());
        if(photoImage!=null){
            photo.setImageBitmap(getImage(photoImage));
        }
        return view;
    }

    private void initViews(View view) {

        //get intent for current user
        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        emailText = view.findViewById(R.id.tvemail);
        firstnameText = view.findViewById(R.id.tvFirstName);
        lastnameText = view.findViewById(R.id.tvLastName);
        genderText = view.findViewById(R.id.tvgender);
        phoneText = view.findViewById(R.id.tvphoneno);
        birthdateText = view.findViewById(R.id.tvbirthdate);
        btnLogout = view.findViewById(R.id.btn_logout);
        photo=view.findViewById(R.id.photo);

        emailText.setText(currentUser.getEmail());
        firstnameText.setText(currentUser.getFirstname());
        lastnameText.setText(currentUser.getLastname());
        genderText.setText(currentUser.getGender());
        phoneText.setText(currentUser.getPhoneNo());
        birthdateText.setText(currentUser.getBdate());

        photoImage= currentUser.getPhoto();
        if(photoImage!=null){
        photo.setImageBitmap(getImage(photoImage));
        }

        //on click listener for logout button
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),Login_page.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        editBtn = view.findViewById(R.id.btneditprofile);

        //on click listener for edit button
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Edit_Profile.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    }




