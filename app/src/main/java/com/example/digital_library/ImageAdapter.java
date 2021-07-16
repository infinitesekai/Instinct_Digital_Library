package com.example.digital_library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.example.digital_library.util.Utils.getImage;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<byte[]> cover_item;



    // Constructor
    public ImageAdapter(Context c,ArrayList<byte[]> cover_item) {
        mContext = c;
        this.cover_item=cover_item;

    }


    public int getCount() {
        return cover_item.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(10, 40, 10, 40);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(getImage(cover_item.get(position)));
        return imageView;
    }

}

