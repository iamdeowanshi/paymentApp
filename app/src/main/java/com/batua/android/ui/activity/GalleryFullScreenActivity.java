package com.batua.android.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;
import com.batua.android.ui.adapter.GalleryViewPagerAdapter;

import java.util.ArrayList;

import butterknife.Bind;

public class GalleryFullScreenActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.toolbar_title) TextView title;
    @Bind(R.id.view_pager) ViewPager viewPager;

    private ArrayList<String> imagesList = new ArrayList<>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_full_screen);

        setToolBar();

        position = getIntent().getIntExtra("position", 0);

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

        GalleryViewPagerAdapter galleryViewPagerAdapter = new GalleryViewPagerAdapter(imagesList, position);
        viewPager.setAdapter(galleryViewPagerAdapter);
        viewPager.setCurrentItem(position, true);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("Gallery Image");
        toolbar.setNavigationIcon(R.drawable.arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
