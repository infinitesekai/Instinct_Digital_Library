package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.digital_library.util.Utils.getByte;
import static com.example.digital_library.util.Utils.getImage;

public class Edit_Profile extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
   //declare variables
    private TextView emailText;//ic text
    private EditText firstnameEdit;//first name text
    private EditText lastnameEdit;//last name text

    private DatePicker datePicker;//date picker from calendar
    private Button dateBtn;//date button

    private EditText phoneEdit;
    private Button cancelBtn;
    private Button saveBtn;
    private Button uploadBtn;
    private Button updateBtn;
    private int lastfragment;
    private byte[] photoImage;
    private ImageView photo;
    private User currentUser;
    public static final int SELECT_PHOTO=7777;

    String[] gender={"Male","Female","Preferred not to say"};
    String selectedGender="";





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        lastfragment=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //get intent
        currentUser = (User) getIntent().getSerializableExtra("user");

        //navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //init views
        initViews();
    }
    //  init views
    private void initViews() {

        //find view
        emailText = findViewById(R.id.tv_email);
        firstnameEdit = findViewById(R.id.et_firstname);
        lastnameEdit = findViewById(R.id.et_lastname);
        Spinner gender_spin = (Spinner) findViewById(R.id.gender_spinner);
        dateBtn = findViewById(R.id.et_bDate);

        phoneEdit = findViewById(R.id.et_phoneno);
        cancelBtn = findViewById(R.id.btncancel);
        saveBtn = findViewById(R.id.btnsave);
        uploadBtn = findViewById(R.id.btnupload);
        updateBtn = findViewById(R.id.btnupdate);
        photo= findViewById(R.id.photo);

        //get intent
        currentUser = (User) getIntent().getSerializableExtra("user");


        //set text
        emailText.setText(currentUser.getEmail());
        firstnameEdit.setText(currentUser.getFirstname());
        lastnameEdit.setText(currentUser.getLastname());
        phoneEdit.setText(currentUser.getPhoneNo());
        dateBtn.setText(currentUser.getBdate());
        currentUser.setBdate(currentUser.getBdate());





        //initiate database access and open database
        DatabaseAccess DB = DatabaseAccess.getInstance(this);
        DB.open();

        //display profile picture if exists
        photoImage= DB.getPic(currentUser.getEmail());

        if(photoImage!=null){
            photo.setImageBitmap(getImage(photoImage));
        }


        //gender spinner-array adapter
        ArrayAdapter genderaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,gender);
        genderaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spin.setAdapter(genderaa);

        //gender spinner-selected gender
        gender_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender=gender_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGender=currentUser.getGender();

            }
        });


        //upload button
        // implicit intent-action pick to select photo
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
               startActivityForResult(intent,SELECT_PHOTO);
            }
        });

        //update button
        //update database for profile photo uploaded
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //convert source of image view to bitmap
                Bitmap bitmap=((BitmapDrawable)photo.getDrawable()).getBitmap();
                boolean update;

               update=DB.addPhoto(currentUser.getEmail(),getByte(bitmap));
                if (update) {
                    Toast.makeText(Edit_Profile.this,"Updated. Saved.",Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(Edit_Profile.this,"Save failed",Toast.LENGTH_SHORT).show();
                }



            }
        });



        //click date and show calendar
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //Gets an instance of a calendar that contains the current year, month and day
                Calendar calendar=Calendar.getInstance();
                //Build a date dialog that has a date picker integrated
                //The second construction parameter of DatePickerDialog specifies the date listener
                DatePickerDialog dialog=new DatePickerDialog(Edit_Profile.this,Edit_Profile.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                //Displays the date dialog on the screen
                dialog.show();
            }
        });

        //cancel button-finish
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //save button
        //update database for user information
        //then redirect to profile fragment
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String firstname = firstnameEdit.getText().toString();
                String lastname = lastnameEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                currentUser.setPhoneNo(phone == null ? "":phone);
                currentUser.setFirstname(firstname == null ? "":firstname);
                currentUser.setLastname(lastname == null ? "":lastname);
                currentUser.setGender(selectedGender == null ? "":selectedGender);
                Boolean result = DB.updateUser(currentUser);

                if (result) {

                    Toast.makeText(Edit_Profile.this,"Updated. Saved.",Toast.LENGTH_SHORT).show();
                    Fragment selectedFragment = null;
                    selectedFragment = new Profile();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


                } else {
                    Toast.makeText(Edit_Profile.this,"Save failed",Toast.LENGTH_SHORT).show();
                }

            }
        });


    };



    //set image to the picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SELECT_PHOTO && resultCode==RESULT_OK && data !=null){
            Uri pickedImage=data.getData();
            photo.setImageURI(pickedImage);

        }
    }

    //Implement dateset listener
    //Get date from the date dialog
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String bdate = String.format("%d-%d-%d",i,i1+1,i2);
        dateBtn.setText(bdate);
        currentUser.setBdate(bdate);
    }

    //get action and check for hide keyboard
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // get action when the user clicks
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();     // Gets the focus of the current page

            // Determine if the user clicked outside of the input field
            if (isShouldHideKeyboard(v, me)) {
                hideKeyboard(v.getWindowToken());   //hide the keyboard
            }
        }
        return super.dispatchTouchEvent(me);
    }


    //check for hide keyboard
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        // Check if the resulting focus contains EditText
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);

            // Gets the position of the input field on the screen
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // Click the location if it is the EditText area, ignore it and do not close the keyboard.
                return false;
            } else {
                return true;
            }
        }
        // If the focus is not EditText, ignore it
        return false;
    }


    //function for bottom navigation ba
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    selectedFragment = new HomePage();
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new Profile();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;

                case R.id.nav_fav:
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    selectedFragment = new FavouritePage();
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_fav;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
