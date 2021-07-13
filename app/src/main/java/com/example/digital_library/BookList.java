package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class BookList extends AppCompatActivity {

    private User currentUser;
    private int lastfragment;

    DatabaseAccess databaseAccess;

    //list view, list item and adapter to display list of application
    ListView book_list;
    public static ArrayList<String> list_item;
    ArrayAdapter adapter;
    TextView list_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;
        String genre=getIntent().getStringExtra("genre");

        //bottom navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        list_title=findViewById(R.id.list_title);

        list_title.setText("Collection of " + genre);


        //initiate database access
        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //reference to list view by id
        book_list=findViewById(R.id.book_list);

        //array list to store list item
        list_item=new ArrayList<String>();

        //call database method to get application list
        //child added into list_item
        //list_item is the list of application for children
        databaseAccess.getBookList(genre);

        if(list_item.isEmpty()){
            Toast.makeText(BookList.this, "No Books.", Toast.LENGTH_SHORT).show();

        }

        //array adapter for ListView display-insert item into ListView from list_item
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_item);

        book_list.setAdapter(adapter);//conjoin array adapter with list view

        //click on list item
        book_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get selected child name from list according to the position
                String bookTitle=book_list.getItemAtPosition(position).toString();


                Intent i;

                //start intent to navigate to respective page according to application status
                i= new Intent(BookList.this, BookDesc.class);
                i.putExtra("user",currentUser);
                i.putExtra("bookTitle",bookTitle);
                startActivity(i);


            }
        });

    }



    //function for bottom navigation bar
    //back to Parent Home Page
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
                    selectedFragment = new FavouritePage();
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