package com.example.digital_library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_page extends AppCompatActivity {
    //declare variable
    private EditText useremail,password;
    private Button btnlogin,btnSignUp;
    private User currentUser;//current user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //reference to view by id
        useremail =findViewById(R.id.etx_signin_email);
        password =findViewById(R.id.etx_password);
        btnlogin =findViewById(R.id.btn_login);
        btnSignUp=findViewById(R.id.btn_sign_up);

        //initiate database access and open database
        DatabaseAccess DB = DatabaseAccess.getInstance(this);
        DB.open();



        //on click listener for login button
        //check user and password from database
        //explicit intent to main activity on successful login
       btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user input
                String email= useremail.getText().toString();
                String pass= password.getText().toString();

                //check empty or not field all the fields

                if (email.equals("")||pass.equals(""))
                    Toast.makeText(Login_page.this,"Please enter all credentials",Toast.LENGTH_SHORT).show();
                else{
                    //verify acc by email and password
                    User user =DB.verifyAcc(email,pass);

                    //successful login
                    //put user intent
                    //explicit intent to main activity
                    if(user.getEmail() != null){
                        Toast.makeText(Login_page.this,"Signed in successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        currentUser = user;
                        intent.putExtra("user", currentUser);
                        startActivity(intent);
                        finish();

                    }else{
                        //failed login
                        Toast.makeText(Login_page.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //signup button
        //explicit intent to sign up page
                btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Login_page.this, SignUp.class));
                    }
                });
    }
}