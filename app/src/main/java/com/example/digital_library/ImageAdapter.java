package com.example.digital_library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

import static com.example.digital_library.util.Utils.getImage;


//image adapter to dispaly book cover
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<byte[]> cover_item;//array list for byte[] to store cover photo of books



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

    // create a new ImageView for each item referenced by the adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        // image view
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(10, 10, 10, 10);

        }
        else
        {
            imageView = (ImageView) convertView;
        }

        //set image bit map
        //get image according to the book positioned
        imageView.setImageBitmap(getImage(cover_item.get(position)));
        return imageView;
    }

}

