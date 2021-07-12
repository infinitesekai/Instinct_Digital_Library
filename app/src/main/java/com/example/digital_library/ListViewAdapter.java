package com.example.digital_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter  extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<FunctionName> functionList = null;
    private ArrayList<FunctionName> arraylist;

    public ListViewAdapter(Context context, List<FunctionName> functionList) {
        mContext = context;
        this.functionList = functionList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<FunctionName>();
        this.arraylist.addAll(functionList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return functionList.size();
    }

    @Override
    public FunctionName getItem(int position) {
        return functionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.functions_list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.functionNames);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(functionList.get(position).getFunctions());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        functionList.clear();
        if (charText.length() == 0) {
            functionList.addAll(arraylist);
        } else {
            for (FunctionName wp : arraylist) {
                if (wp.getFunctions().toLowerCase(Locale.getDefault()).contains(charText)) {
                    functionList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
