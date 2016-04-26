package com.example.anna.flickr;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Anna on 4/25/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String[] list;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public ImageAdapter(Context c, String[] list) {
        mContext = c;
        this.list = list;
    }

    public int getCount() {
        return list.length;
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
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setLayoutParams(new GridView.LayoutParams(
                    (int) mContext.getResources().getDimension(R.dimen.grid_width),
                    (int) mContext.getResources().getDimension(R.dimen.grid_height)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Glide.with(mContext).load(list[position]).into(imageView);
        return imageView;
    }

}
