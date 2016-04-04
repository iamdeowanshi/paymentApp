package com.batua.android.merchant.module.merchant.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.batua.android.merchant.module.merchant.view.listener.RemoveImageClickedListener;
import com.bumptech.glide.Glide;

import net.yazeed44.imagepicker.model.ImageEntry;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Arnold Laishram.
 */
public class AddImagesAdapter extends RecyclerView.Adapter<AddImagesAdapter.AddImagesViewHolder>{

    private List<ImageEntry> customGalleryList;
    private Context context;
    private RemoveImageClickedListener removeImageClickedListener;

    public AddImagesAdapter(List<ImageEntry> customGalleries, RemoveImageClickedListener removeImageClickedListener) {
        this.customGalleryList = customGalleries;
        this.removeImageClickedListener = removeImageClickedListener;
    }

    @Override
    public AddImagesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        this.context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(com.batua.android.merchant.R.layout.list_add_images, viewGroup, false);
        AddImagesViewHolder addImagesViewHolder = new AddImagesViewHolder(v);

        return addImagesViewHolder;
    }

    @Override
    public void onBindViewHolder(AddImagesViewHolder addImagesViewHolder, final int position) {
        Glide.with(context)
                .load(customGalleryList.get(position).path)
                .centerCrop()
                .into(addImagesViewHolder.imgMerchantImages);

        addImagesViewHolder.imgRevoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeImageClickedListener.removeClickedPosition(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (customGalleryList.size() == 0) {

        }
        return customGalleryList.size();
    }

    public class AddImagesViewHolder extends RecyclerView.ViewHolder {

        @Bind(com.batua.android.merchant.R.id.img_add_merchant_images) ImageView imgMerchantImages;
        @Bind(com.batua.android.merchant.R.id.img_remove) ImageView imgRevoveImage;

        public AddImagesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
