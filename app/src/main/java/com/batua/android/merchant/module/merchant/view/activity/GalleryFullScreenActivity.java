package com.batua.android.merchant.module.merchant.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.batua.android.merchant.data.model.Merchant.Gallery;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.merchant.view.adapter.GalleryViewPagerAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;

public class GalleryFullScreenActivity extends BaseActivity {

    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;
    @Bind(com.batua.android.merchant.R.id.toolbar_title) TextView title;
    @Bind(com.batua.android.merchant.R.id.view_pager) ViewPager viewPager;

    private ArrayList<Gallery> imagesList = new ArrayList<>();
    private int position;
    private GalleryViewPagerAdapter galleryViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_gallery_full_screen);

        setToolBar();

        position = getIntent().getIntExtra("position", 0);
        imagesList = Parcels.unwrap(getIntent().getParcelableExtra("imageList"));

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
