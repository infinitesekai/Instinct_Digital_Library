package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public Button btn_student_home, btn_parent_home;//student home button,parent home button
    private User currentUser;//current user
    private int lastfragment;

    //navigation bar
    private BottomNavigationView bottomNav;
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
                    selectedFragment = (Profile)new Profile();
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
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get intent for current user
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //navigation bar
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomNav.setSelectedItemId(bottomNav.getMenu().getItem(lastfragment).getItemId());
            }
        },100);

    }
}