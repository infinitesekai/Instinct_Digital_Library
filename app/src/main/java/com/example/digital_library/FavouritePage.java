package com.example.digital_library;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

public class FavouritePage extends Fragment implements SearchView.OnQueryTextListener{

    SearchView searchView;
    ListView listView;
    ListViewAdapter adapter;
    String [] functionList;
    ArrayList<FunctionName> arraylist= new ArrayList<FunctionName>();

    private User currentUser;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fav_page, container, false);

        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        searchView= (SearchView) v.findViewById(R.id.searchView);
        listView= (ListView) v.findViewById(R.id.listview);

        functionList= new String[] {"Exam Result", "Attendance", "Personality Test Result", "Discipline", "Application for Government School", "Application Status"};

        for(int i=0; i< functionList.length; i++){
            FunctionName functionName= new FunctionName(functionList[i]);
            arraylist.add(functionName);
        }


        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(getContext(), arraylist);

        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(this);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FunctionName function= adapter.getItem(position);
                String functionName= function.getFunctions();

                Log.d("function", functionName);
                Intent i;
                String examMessage = "exam";
                String attendanceMessage = "attendance";
                String disciplineMessage = "discipline";
                String personalityTestMessage = "personality";

//                switch (functionName) {
//                    case ("Exam Result"):
//                        i = new Intent(getActivity(), att_select_child.class);
//                        i.putExtra("user", currentUser);
//                        i.putExtra("message", examMessage);
//                        startActivity(i);
//                        break;
//
//                    case ("Attendance"):
//                        i = new Intent(getActivity(), att_select_child.class);
//                        i.putExtra("user", currentUser);
//                        i.putExtra("message", attendanceMessage);
//                        startActivity(i);
//                        break;
//
//                    case ("Discipline"):
//                        i = new Intent(getActivity(), att_select_child.class);
//                        i.putExtra("user", currentUser);
//                        i.putExtra("message", disciplineMessage);
//                        startActivity(i);
//                        break;
//
//                    case ("Application for Government School"):
//                        i = new Intent(getActivity(), SchoolEnroll.class);
//                        // i.putExtra("user", currentUser);
//                        startActivity(i);
//                        break;
//
//                    case ("Application Status"):
//                        i = new Intent(getActivity(), Apply_List.class);
//                        i.putExtra("user", currentUser);
//                        startActivity(i);
//                        break;
//
//                    case ("Personality Test Result"):
//                        i = new Intent(getActivity(),att_select_child.class);
//                        i.putExtra("user", currentUser);
//                        i.putExtra("message", personalityTestMessage);
//                        startActivity(i);
//                        break;
//
//
//                }

            }
        });

        return v;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }


}