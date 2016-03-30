package com.batua.android.merchant.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.ui.activity.GalleryFullScreenActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * @author Aaditya Deowanshi.
 */
public class GalleryGridViewAdapter extends ArrayAdapter<String> {

    private ArrayList<String> imageList;
    private Context context ;
    private int width;
    private SparseBooleanArray selectedItemsIds;

    public GalleryGridViewAdapter(Context context,int resourceId, ArrayList<String> imageList, int width) {
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
        return imageList.get(position);
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

        Glide.with(context).load(imageList.get(position)).fitCenter().into(imageView);
      /*  imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Timber.d("OnLongClick");

                return true;
            }
        });*/
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("OnClick");

            *//*    final Dialog imageDialog = new Dialog(context);
                imageDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                imageDialog.setContentView(context.getActivity().getLayoutInflater().inflate(R.layout.page_item_gallery, null));
                ImageView image = (ImageView) imageDialog.findViewById(R.id.image_view);
                Glide.with(context).load(imageList.get(position)).into(image);
                imageDialog.show();

                return;*//*
            }
        });*/

        imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }

    @Override
    public void remove(String object) {
        imageList.remove(object);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !selectedItemsIds.get(position));
    }

    public void selectView(int position, boolean value) {
        if (value)
            selectedItemsIds.put(position, value);
        else
            selectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public void removeSelection() {
        selectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }
    public List<String> getWorldPopulation() {
        return imageList;
    }

    public int getSelectedCount() {
        return imageList.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return selectedItemsIds;
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
