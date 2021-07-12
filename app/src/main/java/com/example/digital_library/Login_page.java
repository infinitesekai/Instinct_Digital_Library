package com.example.digital_library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digital_library.util.MyApplication;

public class Login_page extends AppCompatActivity {
    private EditText useremail,password;//ic number,password text field
    private Button btnlogin,btnSignUp;//login button,signup button
   // private DatabaseHelper DB;
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


        //temp link to main page
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(Login_page.this,MainActivity.class);
               startActivity(i);
            }
        });

        //on click listener for login button
       btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= useremail.getText().toString();
                String pass= password.getText().toString();
                if (email.isEmpty()) return;
                if (pass.isEmpty()) return;
                if (email.equals("")||pass.equals(""))
                    Toast.makeText(Login_page.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else{
                    User user =DB.verifyAcc(email,pass);
                    if(user.getEmail() != null){
                        Toast.makeText(Login_page.this,"Signed in successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                        currentUser = user;
                        intent.putExtra("user", currentUser);
                        startActivity(intent);
                        finish();

                    }else{
                        //go to
                        Toast.makeText(Login_page.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //on click listener for signup button
                btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Login_page.this, Sign_Up.class));
                    }
                });
    }
}