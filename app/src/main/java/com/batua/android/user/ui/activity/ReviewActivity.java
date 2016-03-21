package com.batua.android.user.ui.activity;

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
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.data.model.Review;
import com.batua.android.user.ui.adapter.ImageAdapter;
import com.batua.android.user.ui.adapter.ReviewAdapter;
import com.bumptech.glide.Glide;

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
        setContentView(R.layout.activity_review);

        LinearLayoutManager layoutManagerImage = new LinearLayoutManager(this);
        layoutManagerImage.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewImage.setLayoutManager(layoutManagerImage);

        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(this);
        layoutManagerReview.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewReview.setLayoutManager(layoutManagerReview);

        setToolBar();

        Review review1 = new Review(3, "Erik Chapman", "23/12/2015", "jshfu hwdfi hw fiw hfiw hfiwu hiuwf n cwf ebf yudwgfc cswbfiuwfb scuhfiw fwuhfs fb fhwbf bdfubc adiufbw fbsiudhfuwfwf  ffbiuwgfuig sfufbiuwf fiw hfiw hfiwu hiuwf n cwf ebf yudwgfc cswbfiuwfb scuhfiw fwuhfs fb fhwbf bdfubc fiw hfiw hfiwu hiuwf n cwf ebf yudwgfc cswbfiuwfb scuhfiw fwuhfs fb fhwbf bdfubc ");
        Review review2 = new Review(5, "John Galt", "23/12/2015", "jshfu hwdfi hw fiw hfiw hfiwu hiuwf n cwf ebf yudwgfc cswbfiuwfb scuhfiw fwuhfs fb fhwbf bdfubc adiufbw fbsiudhfuwfwf  ffbiuwgfuig sfufbiuwf hfiwu hiuwf n cwf ebf yudwgfc cswbfiuwfb scuhfiw fwuhfs fb fhwbf bdfubc hfiwu hiuwf n cwf ebf yudwgfc cswbfiuwfb scuhfiw fwuhfs fb fhwbf bdfubc hfiwu hiuwf n cwf ebf yudwgfc cswbfiuwfb scuhfiw fwuhfs fb fhwbf bdfubc hfiwu hiuwf n cwf ebf yudwgfc cswbfiuwfb scuhfiw fwuhfs fb fhwbf bdfubc");

        reviewList.add(review1);
        reviewList.add(review2);

        imagesList.add("https://www.planwallpaper.com/static/images/canberra_hero_image_JiMVvYU.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/images_1_05GM1zY.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/offset_WaterHouseMarineImages_62652-2-660x440.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/background-gmail-google-images_FG2XwaO.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/6775415-beautiful-images.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/434976-happy-valentines-day-timeline-cover.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/image5_170127819.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/orangutan_1600x1000_279157.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/Winter-Tiger-Wild-Cat-Images.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/434976-happy-valentines-day-timeline-cover.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/tree-247122.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/Winter-Tiger-Wild-Cat-Images.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/tree-247122.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/orangutan_1600x1000_279157.jpg");
        imagesList.add("https://www.planwallpaper.com/static/images/image5_170127819.jpg");

        imageAdapter = new ImageAdapter(imagesList, this);
        imageAdapter.setImageClickListener(this);
        recyclerViewImage.setAdapter(imageAdapter);

        reviewAdapter = new ReviewAdapter(reviewList, this);
        recyclerViewReview.setAdapter(reviewAdapter);
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
        imageDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout, null));
        ImageView image = (ImageView) imageDialog.findViewById(R.id.image_fullscreen);
        Glide.with(getApplicationContext()).load(imagesList.get(position)).into(image);
        imageDialog.show();
    }

}
