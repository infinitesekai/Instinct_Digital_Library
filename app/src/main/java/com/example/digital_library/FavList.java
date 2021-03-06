package com.example.digital_library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FavList extends Fragment {

    ListView fav_list;

    ArrayAdapter adapter;

    DatabaseAccess databaseAccess;
    private User currentUser;

    private int lastfragment;

    Button favshelfbutton;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fav_page, container, false);
        //get intent
        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        //initiate database access
        databaseAccess= DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        //find view
        fav_list= (ListView) v.findViewById(R.id.fav_list);
        favshelfbutton= (Button) v.findViewById(R.id.favshelfbutton);

        //check if favourite list is empty
        if(FavouritePage.favlist_item.isEmpty()){
            Toast.makeText(getActivity(), "No Books.", Toast.LENGTH_SHORT).show();

        }

        //array adapter for ListView display-insert item into ListView from list_item
        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,FavouritePage.favlist_item);

        fav_list.setAdapter(adapter);//conjoin array adapter with list view

        //click on list item
        fav_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get book title according to position
                String bookTitle=fav_list.getItemAtPosition(position).toString();


                Intent i;

                //start intent to navigate to respective book page
                i= new Intent(getActivity(), BookDesc.class);
                i.putExtra("user",currentUser);
                i.putExtra("bookTitle",bookTitle);
                startActivity(i);


            }
        });

        //explicit intent-change to favourite shelf view fragment
        favshelfbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment=new FavouritePage();
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