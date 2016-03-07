package com.batua.android.ui.custom;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.batua.android.data.model.CustomGallery;
import com.batua.android.ui.adapter.AddImagesAdapter;

import java.util.List;

/**
 * @author Arnold Laishram.
 */
public class PopulateImageAdapter {

    public static void populateAdapter(Context context, List<CustomGallery> customGalleryList, RecyclerView imageRecyclerView){

        AddImagesAdapter addImagesAdapter = new AddImagesAdapter(customGalleryList);
        LinearLayoutManager llayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(llayout);
        imageRecyclerView.setAdapter(addImagesAdapter);
    }

}
