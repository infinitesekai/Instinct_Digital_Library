package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public Button btn_student_home, btn_parent_home;
    private User currentUser;
    private int lastfragment;
    private BottomNavigationView bottomNav;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    if(lastfragment!=R.id.nav_home) {
                        selectedFragment = new HomePage();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",currentUser);
                        selectedFragment.setArguments(bundle);
                        lastfragment = R.id.nav_home;
                    }
                    break;


                case R.id.nav_profile:
                    if(lastfragment!=R.id.nav_profile) {
                        selectedFragment = (Profile)new Profile();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",currentUser);
                        selectedFragment.setArguments(bundle);
                        lastfragment = R.id.nav_profile;
                    }
                    break;
                case R.id.nav_search:
                    if(lastfragment!=R.id.nav_search) {
                        selectedFragment = new SearchPage();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",currentUser);
                        selectedFragment.setArguments(bundle);
                        lastfragment = R.id.nav_search;
                    }
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
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomNav.setSelectedItemId(bottomNav.getMenu().getItem(lastfragment).getItemId());
            }
        },100);
//        if (currentUser.getRole() == 0) {
//            Fragment fragment = new HomePage();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_container, fragment);
//            transaction.commit();
//        } else {
//            Fragment fragment = new HomePage_Student();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_container, fragment);
//            transaction.commit();
//        }
        Fragment fragment = new HomePage();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();



    }
}