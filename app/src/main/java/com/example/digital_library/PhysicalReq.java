package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class PhysicalReq extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    //declare variable
    private User currentUser;
    private int lastfragment;

    private EditText nameEdit;
    private EditText addressEdit;
    private EditText contactEdit;
    private EditText remarkEdit;

    private DatePicker datePicker;
    private Button dateBtn;


    private Button cancelBtn;
    private Button submitBtn;

    String selectedBook="";
    String returnDate="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_req);

        //get intent
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

    //bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //find view
        nameEdit = findViewById(R.id.et_recname);
        addressEdit = findViewById(R.id.et_address);
        contactEdit = findViewById(R.id.et_contact);
        remarkEdit = findViewById(R.id.et_remark);
        Spinner book_spin = (Spinner) findViewById(R.id.book_spinner);

        dateBtn = findViewById(R.id.et_returndate);


        cancelBtn = findViewById(R.id.cancel);
        submitBtn = findViewById(R.id.submit);

        //initiate database access and open database
        DatabaseAccess DB = DatabaseAccess.getInstance(this);
        DB.open();

        //get all book list
        List<String> AllBook=DB.getAllBook();

        //spinner to display all the book available
        ArrayAdapter bookaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,AllBook);
        bookaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        book_spin.setAdapter(bookaa);

        //get selected book from spinner
        book_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBook=book_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //date button
        //display date picker
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //Gets an instance of a calendar that contains the current year, month and day
                Calendar calendar=Calendar.getInstance();
                //Build a date dialog that has a date picker integrated
                //The second construction parameter of DatePickerDialog specifies the date listener
                DatePickerDialog dialog=new DatePickerDialog(PhysicalReq.this, PhysicalReq.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                //Displays the date dialog on the screen
                dialog.show();
            }
        });

        //ocancel button
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //submit button
        //insert book request information into database
        //explicit intent to SubmitTransition for successful data insert
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = nameEdit.getText().toString();
                String address = addressEdit.getText().toString();
                String contact = contactEdit.getText().toString();
                String remark= remarkEdit.getText().toString();

                Boolean result = DB.insertPhyReq(currentUser.getEmail(),name,address,contact,selectedBook,returnDate,remark);

                if (result) {

                    Toast.makeText(PhysicalReq.this,"Submitted.",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(PhysicalReq.this, SubmitTransition.class);
                    i.putExtra("user",currentUser);
                    startActivity(i);



                } else {
                    Toast.makeText(PhysicalReq.this,"Submit failed",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    //date set listener
    //set date format
    //set text for date button
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        returnDate = String.format("%d-%d-%d",i,i1+1,i2);
        dateBtn.setText(returnDate);

    }


    //bottom navigation
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