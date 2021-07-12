package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


//academic qualification
public class BookDesc extends AppCompatActivity {
    //text view
    private TextView title,author,genre,synopsis,country,publisher;
    private byte[] coverImage;
    String bookTitle;
    Button btndownload;//download button
    private User currentUser;//current user
    private int lastfragment;//indicate last fragment for navigation bar
    private ImageView cover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_desc);

        currentUser = (User) getIntent().getSerializableExtra("user");//get intent for current user
        bookTitle=getIntent().getStringExtra("bookTitle");
        lastfragment = 0;

        //navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //reference to view by id
        title=findViewById(R.id.tvtitle);
        cover=findViewById(R.id.cover);
        author=findViewById(R.id.tvauthor);
        genre=findViewById(R.id.tvGenre);
        synopsis=findViewById(R.id.tvSynopsis);
        country=findViewById(R.id.tvCountry);
        publisher=findViewById(R.id.tvpublisher);


        btndownload=findViewById(R.id.btndownload);

        //initiate database access and open database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();


        //call database access method to display qualification information
        //BookInfo class object to store book record
        BookInfo book_record=databaseAccess.DisplayBook(bookTitle);

        //book details information display
        cover.findViewById(R.id.cover);
        author=findViewById(R.id.tvauthor);
        genre=findViewById(R.id.tvGenre);
        synopsis=findViewById(R.id.tvSynopsis);
        country=findViewById(R.id.tvCountry);
        publisher=findViewById(R.id.tvpublisher);

        title.setText(bookTitle);

        author.setText(book_record.getAuthor());
        genre.setText(book_record.getGenre());
        synopsis.setText(book_record.getSynopsis());
        country.setText(book_record.getCountry());
        publisher.setText(book_record.getPublisher());

        databaseAccess.close();//close database access

        //on click listener for download button

        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i= new Intent(BookDesc.this, DownloadBook.class);
               // i.putExtra("user",currentUser);

                //startActivity(i);
            }
        });




    }

    //function for bottom navigation bar
    //back to  Home Page
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
                case R.id.nav_search:
                    selectedFragment = new SearchPage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };



}