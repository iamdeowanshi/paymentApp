package com.batua.android.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;
import com.batua.android.ui.adapter.GalleryGridViewAdapter;
import com.batua.android.util.DisplayUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryImagesActivity extends BaseActivity {

    @Inject DisplayUtil displayUtil;

    @Bind(R.id.grid_view) GridView gridView;
    @Bind(R.id.toolbar_title) TextView title;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private ArrayList<String> imagesList = new ArrayList<String>();
    private GalleryGridViewAdapter galleryGridViewAdapter;
    private int columnWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_images);
        injectDependencies();

        initializeGridView();
        setToolBar();

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

        galleryGridViewAdapter = new GalleryGridViewAdapter(this, imagesList, columnWidth);
        gridView.setAdapter(galleryGridViewAdapter);

    }

    private void initializeGridView() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());

        columnWidth = (int) ((displayUtil.getWidth() - 30  - ((4 + 1) * padding)) / 4);

        gridView.setNumColumns(4);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("Gallery Images");
        toolbar.setNavigationIcon(R.drawable.arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

