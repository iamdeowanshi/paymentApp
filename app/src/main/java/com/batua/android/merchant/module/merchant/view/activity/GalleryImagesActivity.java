package com.batua.android.merchant.module.merchant.view.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.GridView;
import android.widget.TextView;

import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.DisplayUtil;
import com.batua.android.merchant.module.merchant.view.adapter.GalleryGridViewAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;

public class GalleryImagesActivity extends BaseActivity {

    @Inject DisplayUtil displayUtil;

    @Bind(com.batua.android.merchant.R.id.grid_view) GridView gridView;
    @Bind(com.batua.android.merchant.R.id.toolbar_title) TextView title;
    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;

    private ArrayList<String> imagesList = new ArrayList<String>();
    private GalleryGridViewAdapter galleryGridViewAdapter;
    private int columnWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_gallery_images);
        Injector.component().inject(this);

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

        galleryGridViewAdapter = new GalleryGridViewAdapter(this, com.batua.android.merchant.R.layout.page_item_gallery, imagesList, columnWidth);
        gridView.setAdapter(galleryGridViewAdapter);

        gridView.setLongClickable(true);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        /*gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_gallery_full_screen, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = galleryGridViewAdapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                String selecteditem = galleryGridViewAdapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                galleryGridViewAdapter.remove(selecteditem);
                            }
                        }
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                galleryGridViewAdapter.removeSelection();
            }

            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = gridView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                galleryGridViewAdapter.toggleSelection(position);
            }
        });*/

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

        title = (TextView) toolbar.findViewById(com.batua.android.merchant.R.id.toolbar_title);
        title.setText("Gallery Images");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

