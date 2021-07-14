package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.digital_library.util.Utils.getImage;


//academic qualification
public class BookDesc extends AppCompatActivity {
    //text view
    private TextView title,author,genre,synopsis,country,publisher;
    private byte[] coverImage;
    String bookTitle;
    Button btnread,btndownload,btnfav,btnshare;//download button
    private User currentUser;//current user
    private int lastfragment;//indicate last fragment for navigation bar
    private ImageView cover;
    private boolean check;
    DatabaseAccess databaseAccess;
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


        btnread=findViewById(R.id.btnread);
        btndownload=findViewById(R.id.btndownload);
        btnfav=findViewById(R.id.favbutton);
        btnshare=findViewById(R.id.sharebutton);



        //initiate database access and open database
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        boolean checkfav=databaseAccess.checkFav(currentUser.getEmail(),bookTitle);
        if(checkfav){
            btnfav.setBackgroundResource(R.drawable.ic_red_favorite_24);
            btnfav.setSelected(true);
            check=true;
        }
        else{
            btnfav.setBackgroundResource(R.drawable.ic_shadow_favorite_24);
            btnfav.setSelected(false);
            check=false;
        }

        btnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favclick(currentUser.getEmail(),bookTitle,btnfav,check);
            }
        });



        //call database access method to display qualification information
        //BookInfo class object to store book record
        BookInfo book_record=databaseAccess.DisplayBook(bookTitle);



        //book details information display

        title.setText(bookTitle);
        author.setText(book_record.getAuthor());
        genre.setText(book_record.getGenre());
        synopsis.setText(book_record.getSynopsis());
        country.setText(book_record.getCountry());
        publisher.setText(book_record.getPublisher());

        coverImage= databaseAccess.getCover(bookTitle);
        if(coverImage!=null){
            cover.setImageBitmap(getImage(coverImage));
        }

        //on click listener for download button

        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String readlink=databaseAccess.getLink(bookTitle);
                if( readlink==""){
                    Toast.makeText(BookDesc.this,"No resources.",Toast.LENGTH_SHORT).show();
                }else{


                    Intent i= new Intent(BookDesc.this, BookRead.class);
                    i.putExtra("user",currentUser);
                    i.putExtra("bookTitle",bookTitle);
                    i.putExtra("readlink",readlink);
                    startActivity(i);}

            }
        });

        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String downloadlink=databaseAccess.getDownloadLink(bookTitle);
                if( downloadlink==""){
                    Toast.makeText(BookDesc.this,"No resources.",Toast.LENGTH_SHORT).show();
                }else{

                Intent i= new Intent(BookDesc.this, BookDownload.class);
                i.putExtra("user",currentUser);
                i.putExtra("bookTitle",bookTitle);
                startActivity(i);
                }

            }
        });


        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text="Hey, I find this book interesting. Here's " + bookTitle +" by " + book_record.getAuthor() + ". About the book: "
                        + book_record.getSynopsis() +" Let's read together: " + databaseAccess.getLink(bookTitle) ;
                Intent i=new Intent();
                i.setAction(i.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(i.EXTRA_TEXT,text);
                startActivity(Intent.createChooser(i,"Share this book!"));
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

    private void favclick(String useremail,String bookTitle,Button btnfav,boolean thischeck){
        if(!thischeck){

            boolean insert=databaseAccess.insertFav(currentUser.getEmail(),bookTitle);
            if(insert){
                Toast.makeText(BookDesc.this,"Added to Favourite.",Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(BookDesc.this,"Failed.",Toast.LENGTH_SHORT).show();

            }
            btnfav.setBackgroundResource(R.drawable.ic_red_favorite_24);
            btnfav.setSelected(true);
            check=true;
        }
        else{
            boolean remove=databaseAccess.removeFav(currentUser.getEmail(),bookTitle);
            if(remove){
                Toast.makeText(BookDesc.this,"Removed from Favourite.",Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(BookDesc.this,"Failed.",Toast.LENGTH_SHORT).show();

            }
            btnfav.setBackgroundResource(R.drawable.ic_shadow_favorite_24);
            btnfav.setSelected(false);
            check=false;
        }
    }




}