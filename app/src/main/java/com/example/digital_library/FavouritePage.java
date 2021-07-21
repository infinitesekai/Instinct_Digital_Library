package com.example.digital_library;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class FavouritePage extends Fragment {
    //declare variable
    DatabaseAccess databaseAccess;
    private User currentUser;
    private int lastfragment;
    GridView fav_grid;
    public static ArrayList<String> favlist_item;
    private ArrayList<byte[]> favcover_item;
    Button favlistbutton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fav_shelf, container, false);

        //get current user
        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        //initiate database access
        databaseAccess= DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        //reference to list view by id
        fav_grid= (GridView) v.findViewById(R.id.favbook_grid);
        favlistbutton=(Button) v.findViewById(R.id.favlistbutton);

        //get favourite list
        favlist_item=new ArrayList<String>();

        databaseAccess.getFavList(currentUser.getEmail());

        //check if the list is empty
        if(favlist_item.isEmpty()){
            Toast.makeText(getActivity(), "No Books.", Toast.LENGTH_SHORT).show();

        }


        //get cover photo for books in favourite
        favcover_item=new ArrayList<byte[]>();

        favcover_item=databaseAccess.getFavCover(currentUser.getEmail());

        //favourite-grid view-set image adapter
        fav_grid.setAdapter(new ImageAdapter(getContext(),favcover_item));

        //grid-item listener
        //explicit intent to BookDesc
        fav_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){

                Intent i = new Intent(getActivity(), BookDesc.class);
                i.putExtra("user",currentUser);
                i.putExtra("bookTitle", favlist_item.get(position));
                startActivity(i);
            }
        });

        //list button
        //change to fragment in list view
        favlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment=new FavList();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",currentUser);//pass the value
                selectedFragment.setArguments(bundle);
                lastfragment = R.id.nav_fav;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
        });



        return v;

    }

}