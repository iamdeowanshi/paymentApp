package com.tecsol.batua.user.module.review.view.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.batua.android.user.R;
import com.bumptech.glide.Glide;
import com.tecsol.batua.user.data.model.Merchant.Gallery;
import com.tecsol.batua.user.data.model.Merchant.Review;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.review.view.adapter.ImageAdapter;
import com.tecsol.batua.user.module.review.view.adapter.ReviewAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ReviewActivity extends BaseActivity implements ImageAdapter.ImageClickListener {

    @Bind(R.id.toolbar_title) TextView title;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.recycler_view) RecyclerView recyclerViewImage;
    @Bind(R.id.review_list) RecyclerView recyclerViewReview;

    private List<Review> reviewList = new ArrayList<>();
    private List<String> imagesList = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.user.R.layout.activity_review);

        LinearLayoutManager layoutManagerImage = new LinearLayoutManager(this);
        layoutManagerImage.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewImage.setLayoutManager(layoutManagerImage);

        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(this);
        layoutManagerReview.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewReview.setLayoutManager(layoutManagerReview);

        setToolBar();

        int merchantId = getIntent().getExtras().getInt("MerchantId");
        List<Gallery> galleries = Parcels.unwrap(getIntent().getExtras().getParcelable("Galleries"));


        /*Review review1 = new Review(3, "Erik Chapman", "23/12/2015", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        Review review2 = new Review(5, "John Galt", "23/12/2015", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        reviewList.add(review1);
        reviewList.add(review2);

        reviewAdapter = new ReviewAdapter(reviewList, this);
        recyclerViewReview.setAdapter(reviewAdapter);*/

        for (Gallery gallery : galleries){
            imagesList.add(gallery.getUrl());
        }

        imageAdapter = new ImageAdapter(imagesList, this);
        imageAdapter.setImageClickListener(this);
        recyclerViewImage.setAdapter(imageAdapter);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText("Reviews");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onImageClick(final int position) {
        final Dialog imageDialog = new Dialog(this);
        imageDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        imageDialog.setContentView(getLayoutInflater().inflate(com.batua.android.user.R.layout.image_layout, null));
        ImageView image = (ImageView) imageDialog.findViewById(com.batua.android.user.R.id.image_fullscreen);
        Glide.with(getApplicationContext()).load(imagesList.get(position)).into(image);
        imageDialog.show();
    }

}
