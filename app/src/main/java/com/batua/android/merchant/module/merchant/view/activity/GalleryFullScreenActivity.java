package com.batua.android.merchant.module.merchant.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.merchant.view.adapter.GalleryViewPagerAdapter;

import java.util.ArrayList;

import butterknife.Bind;

public class GalleryFullScreenActivity extends BaseActivity {

    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;
    @Bind(com.batua.android.merchant.R.id.toolbar_title) TextView title;
    @Bind(com.batua.android.merchant.R.id.view_pager) ViewPager viewPager;

    private ArrayList<String> imagesList = new ArrayList<>();
    private int position;
    private GalleryViewPagerAdapter galleryViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_gallery_full_screen);

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

        galleryViewPagerAdapter = new GalleryViewPagerAdapter(imagesList, position);
        viewPager.setAdapter(galleryViewPagerAdapter);
        viewPager.setCurrentItem(position, true);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(com.batua.android.merchant.R.id.toolbar_title);
        title.setText("Gallery Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.batua.android.merchant.R.menu.menu_gallery_full_screen, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( item.getItemId() == com.batua.android.merchant.R.id.action_delete) {
            finish();

            imagesList.remove(position);
            galleryViewPagerAdapter.notifyDataSetChanged();

        }
        return super.onOptionsItemSelected(item);
    }
}
