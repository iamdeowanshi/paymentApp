package com.batua.android.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.batua.android.ui.activity.GalleryFullScreenActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * @author Aaditya Deowanshi.
 */
public class GalleryGridViewAdapter extends BaseAdapter {

    private ArrayList<String> imageList;
    private Context context ;
    private int width;

    public GalleryGridViewAdapter(Context context, ArrayList<String> imageList, int width) {
        this.context = context;
        this.imageList = imageList;
        this.width = width;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(width, width));

        Glide.with(context).load(imageList.get(position)).fitCenter().into(imageView);
        imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }

    class OnImageClickListener implements View.OnClickListener {

        int position;

        // constructor
        public OnImageClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(context, GalleryFullScreenActivity.class);
            i.putExtra("position", position);
            context.startActivity(i);
        }

    }
}
