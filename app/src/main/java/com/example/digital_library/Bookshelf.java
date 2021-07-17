package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Bookshelf extends AppCompatActivity {

    //declare variables
    private User currentUser;
    private int lastfragment;

    DatabaseAccess databaseAccess;
    GridView book_grid;

    private ArrayList<byte[]> cover_item;
    ArrayAdapter adapter;
    TextView grid_title;

    Button listbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);

        //get intent
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;
        String genre=getIntent().getStringExtra("genre");



        //bottom navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //find view
        grid_title=findViewById(R.id.bookgrid_title);
        listbutton=findViewById(R.id.listbutton);

        //set title
        grid_title.setText("Collections: " + genre);


        //reference to list view by id
        book_grid=findViewById(R.id.book_grid);



        //initiate database access
        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //get book list
        BookList.list_item=new ArrayList<String>();

        databaseAccess.getBookList(genre);


        //get book cover
        cover_item=new ArrayList<byte[]>();

        cover_item=databaseAccess.getAllCover(genre);

        //set image adapter
        book_grid.setAdapter(new ImageAdapter(this,cover_item));

        //book grid-on item click
        //explicit intent to BookDesc
        book_grid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){

                Intent i = new Intent(Bookshelf.this, BookDesc.class);
                i.putExtra("user",currentUser);
                i.putExtra("bookTitle", BookList.list_item.get(position));
                startActivity(i);
            }
        });

        //explicit intent-change to list view
        listbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Bookshelf.this, BookList.class);
                i.putExtra("user",currentUser);
                i.putExtra("genre",genre);
                startActivity(i);
            }
        });


    }

    //function for bottom navigation bar

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