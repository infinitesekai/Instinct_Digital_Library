package com.example.digital_library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    //declare variable
    private EditText useremail,password,repassword,firstname,lastname;
    private Button signup,signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //find view
        useremail =findViewById(R.id.etx_signin_email);
        firstname =findViewById(R.id.etx_signin_firstname);
        lastname =findViewById(R.id.etx_signin_lastname);
        password =findViewById(R.id.etx_signup_password);
        repassword =findViewById(R.id.etx_signup_repassword);
        signup =findViewById(R.id.btn_signup);
        signin =findViewById(R.id.btn_signin);

        //initiate database access and open database
        DatabaseAccess DB = DatabaseAccess.getInstance(this);
        DB.open();

        //sign up button
        //save all the information and create an account
        //insert into database
        //explicit intent to login page if registered successfullly
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user input
                String email = useremail.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String firstnameStr = firstname.getText().toString().toUpperCase();
                String lastnameStr = lastname.getText().toString().toUpperCase();

                //check if empty or not fill all fields
                if (email.equals("")||pass.equals("")||repass.equals("")) {
                    Toast.makeText(SignUp.this,"Please enter all credentials",Toast.LENGTH_SHORT).show();
                } else{
                    if(pass.equals(repass)){
                        //check user with email
                        User checkuser =DB.checkUser(email);

                        //can sign up if user not yet exist
                        if(checkuser.getEmail() == null ){
                            //insert data into user table
                            Boolean insert = DB.insertUser(email,pass, firstnameStr, lastnameStr);
                            if(insert){
                                if(ContextCompat.checkSelfPermission(SignUp.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(SignUp.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
                                }
                                Toast.makeText(SignUp.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(getApplicationContext(), Login_page.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SignUp.this, "Registration failed ", Toast.LENGTH_SHORT).show();
                            }

                        } else{
                            Toast.makeText(SignUp.this, "User already exist. Please enter again.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(SignUp.this,"Password does not match. Please enter again.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //sign in button
        //explicit intent to login page
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login_page.class);
                startActivity(intent);
            }
        });


    }
}