package com.example.digital_library;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class BookGenre extends AppCompatActivity implements View.OnClickListener {

    CardView novel, nonfiction,detective,adventure,fantasy,poetry;
    private User currentUser;
    private int lastfragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_genre);



        //get the value pass from the previous activity
        currentUser = (User) getIntent().getSerializableExtra("user");

        lastfragment = 0;
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        novel = findViewById(R.id.novel);
        nonfiction = findViewById(R.id.nonfic);
        detective = findViewById(R.id.detective);
        adventure = findViewById(R.id.adventure);
        fantasy = findViewById(R.id.fantasy);
        poetry = findViewById(R.id.poetry);


        novel.setOnClickListener(this);
        nonfiction.setOnClickListener(this);
        detective.setOnClickListener(this);
        adventure.setOnClickListener(this);
        fantasy.setOnClickListener(this);
        poetry.setOnClickListener(this);




    }

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

    @Override
    public void onClick(View v) {
        Intent i;


        String novelgenre = "Novel";
        String nonficgenre = "Non-Fiction";
        String detectivegenre = "Detective";
        String fantasygenre = "Fantasy";
        String adventuregenre = "Adventure";
        String poetrygenre = "Poetry";
        switch (v.getId()) {


            case R.id.novel:
                i = new Intent(this, BookList.class);
                i.putExtra("user", currentUser);
                i.putExtra("genre",novelgenre);
                startActivity(i);
                break;

            case R.id.nonfic:
                i = new Intent(this, BookList.class);
                i.putExtra("user",currentUser);
                i.putExtra("genre",nonficgenre);
                startActivity(i);
                break;

            case R.id.detective:
                i = new Intent(this, BookList.class);
                i.putExtra("user", currentUser);
                i.putExtra("genre",detectivegenre);
                startActivity(i);
                break;

            case R.id.adventure:
                i = new Intent(this, BookList.class);
                i.putExtra("user",currentUser);
                i.putExtra("genre",adventuregenre);
                startActivity(i);
                break;

            case R.id.fantasy:
                i = new Intent(this, BookList.class);
                i.putExtra("user", currentUser);
                i.putExtra("genre",fantasygenre);
                startActivity(i);
                break;

            case R.id.poetry:
                i = new Intent(this, BookList.class);
                i.putExtra("user",currentUser);
                i.putExtra("genre",poetrygenre);
                startActivity(i);
                break;


        }
    }
}