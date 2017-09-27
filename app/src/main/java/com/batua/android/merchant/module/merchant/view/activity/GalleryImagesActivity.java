package com.batua.android.merchant.module.merchant.view.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.GridView;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Gallery;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.DisplayUtil;
import com.batua.android.merchant.module.merchant.view.adapter.GalleryGridViewAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryImagesActivity extends BaseActivity {

    @Inject DisplayUtil displayUtil;

    @Bind(R.id.grid_view) GridView gridView;
    @Bind(R.id.toolbar_title) TextView title;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.text_name) TextView textName;
    @Bind(R.id.text_address) TextView textAddress;

    private ArrayList<Gallery> imagesList = new ArrayList<Gallery>();
    private GalleryGridViewAdapter galleryGridViewAdapter;
    private Merchant merchant;
    private int columnWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_images);
        ButterKnife.bind(this);
        Injector.component().inject(this);

        initializeGridView();
        setToolBar();

        merchant = Parcels.unwrap(getIntent().getParcelableExtra("Merchant"));
        imagesList = (ArrayList<Gallery>) merchant.getGalleries();

        textName.setText(merchant.getName());
        if (merchant.getAddress() != null) {
            textAddress.setText(merchant.getAddress());
        }

        galleryGridViewAdapter = new GalleryGridViewAdapter(this, R.layout.page_item_gallery, imagesList, columnWidth);
        gridView.setAdapter(galleryGridViewAdapter);

        gridView.setLongClickable(true);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
    }

    private void initializeGridView() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());

        columnWidth = (int) ((displayUtil.getWidth() - 30 - ((4 + 1) * padding)) / 4);

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

