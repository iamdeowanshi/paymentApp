package com.batua.android.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.batua.android.R;
import com.batua.android.data.model.CustomGallery;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Arnold Laishram.
 */
public class AddImagesAdapter extends RecyclerView.Adapter<AddImagesAdapter.AddImagesViewHolder>{

    private List<CustomGallery> customGalleryList;
    private Context context;

    public AddImagesAdapter(List<CustomGallery> customGalleries) {
        this.customGalleryList = customGalleries;
    }

    @Override
    public AddImagesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        this.context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_add_images, viewGroup, false);
        AddImagesViewHolder addImagesViewHolder = new AddImagesViewHolder(v);

        return addImagesViewHolder;
    }

    @Override
    public void onBindViewHolder(AddImagesViewHolder addImagesViewHolder, int position) {
        CustomGallery customGallery = customGalleryList.get(position);

        Glide.with(context)
                .load(customGallery.getImagePath())
                .fitCenter()
                .into(addImagesViewHolder.imgMerchantImages);

    }

    @Override
    public int getItemCount() {
        return customGalleryList.size();
    }


    public class AddImagesViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img_add_merchant_images) ImageView imgMerchantImages;

        public AddImagesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
