package com.batua.android.merchant.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.app.base.BaseActivity;
import com.batua.android.merchant.util.ImageUtil;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class EditProfileActivity extends BaseActivity implements ImageUtil.ImageUtilCallback {

    @Inject ImageUtil imageUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.img_profile) CircularImageView imgProfile;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        injectDependencies();

        imageUtil.setImageUtilCallback(this);

        setToolBar();
    }

    @OnClick(R.id.img_profile)
    void onImageClick() {
        imageUtil.getImage(this);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUtil.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(Uri uri, File file) {
        Glide.with(this).load(uri).into(imgProfile);
    }

}
