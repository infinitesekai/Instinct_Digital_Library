package com.example.digital_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

//public class GridViewApdater extends ArrayAdapter <Dependency>{
//
//
//
//    public GridViewApdater(Context context, ArrayList<Dependency> childlist )
//    {
//        super(context, 0 , childlist);
//
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View v = convertView;
//        if(v ==null)
//        {
//            v= LayoutInflater.from(getContext()).inflate(R.layout.select_child_view,parent,false);
//        }
//
//        Dependency dependency= getItem(position);
//        TextView textView = (TextView) v.findViewById(R.id.tvChild);
//        textView.setText(dependency.getChildName());
//        return v;
//
//    }
//}
