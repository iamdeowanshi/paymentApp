package com.batua.android.merchant.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.CustomGallery;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Arnold Laishram.
 */
public class AddImagesAdapter extends RecyclerView.Adapter<AddImagesAdapter.AddImagesViewHolder>{

    private List<CustomGallery> customGalleryList;

    public AddImagesAdapter(List<CustomGallery> customGalleries) {
        this.customGalleryList = customGalleries;
    }

    @Override
    public AddImagesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_add_images, viewGroup, false);
        AddImagesViewHolder addImagesViewHolder = new AddImagesViewHolder(v);

        return addImagesViewHolder;

    }

    @Override
    public void onBindViewHolder(AddImagesViewHolder addImagesViewHolder, int position) {
        CustomGallery customGallery = customGalleryList.get(position);

        addImagesViewHolder.imgMerchantImages.setImageURI(Uri.fromFile(new File(customGallery.getImagePath())));
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
