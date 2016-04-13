package com.batua.android.merchant.module.merchant.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.batua.android.merchant.data.model.Merchant.Gallery;
import com.batua.android.merchant.module.merchant.view.activity.GalleryFullScreenActivity;
import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aaditya Deowanshi.
 */
public class GalleryGridViewAdapter extends ArrayAdapter<String> {

    private ArrayList<Gallery> imageList;
    private Context context ;
    private int width;
    private SparseBooleanArray selectedItemsIds;

    public GalleryGridViewAdapter(Context context,int resourceId, ArrayList<Gallery> imageList, int width) {
        super(context, resourceId);
        this.context = context;
        this.imageList = imageList;
        this.width = width;

        selectedItemsIds = new SparseBooleanArray();
    }

    public GalleryGridViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public String getItem(int position) {
        return imageList.get(position).getUrl();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(width, width));

        Glide.with(context).load(imageList.get(position).getUrl()).fitCenter().into(imageView);

        imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }

    @Override
    public void remove(String object) {
        imageList.remove(object);
        notifyDataSetChanged();
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
            Bundle bundle = new Bundle();
            bundle.putParcelable("imageList", Parcels.wrap(imageList));
            bundle.putInt("position", position);
            i.putExtras(bundle);
            context.startActivity(i);
        }

    }
}
