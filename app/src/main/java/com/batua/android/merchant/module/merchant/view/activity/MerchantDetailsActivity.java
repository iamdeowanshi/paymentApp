package com.batua.android.merchant.module.merchant.view.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.module.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.OnClick;

public class MerchantDetailsActivity extends BaseActivity {

    private static final String ACTIVE = "Active";
    private static final String DRAFTED = "Drafted";

    @Bind(R.id.toolbar_title) TextView title;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.merchant_detail_scroll_view) ScrollView scrollView;
    @Bind(R.id.txt_short_code) TextView txtShortCode;
    @Bind(R.id.text_address) TextView txtAddress;
    @Bind(R.id.text_status) TextView txtStatus;
    @Bind(R.id.fees) TextView txtFees;
    @Bind(R.id.email) TextView txtEmail;
    @Bind(R.id.call) TextView txtCall;
    @Bind(R.id.account_no) TextView txtAccountNumber;
    @Bind(R.id.account_name) TextView txtAccountName;
    @Bind(R.id.bank) TextView txtBank;
    @Bind(R.id.branch) TextView txtBranch;
    @Bind(R.id.ifsc) TextView txtIfsc;
    @Bind(R.id.gallery_layout) RelativeLayout galleryLayout;
    @Bind(R.id.first_image) ImageView firstGalleyImage;
    @Bind(R.id.second_image) ImageView secondGalleyImage;
    @Bind(R.id.third_image) ImageView thirdGalleyImage;
    @Bind(R.id.fourth_image) ImageView fourthGalleyImage;
    @Bind(R.id.merchant_dp) ImageView merchantDp;

    private Merchant merchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_merchant_details);

        merchant = Parcels.unwrap(getIntent().getParcelableExtra("MerchantDetail"));

        scrollView.setVisibility(View.GONE);
        showMerchantDetail(merchant);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.batua.android.merchant.R.menu.menu_merchant_details, menu);

        if (merchant.getStatus().toString().equalsIgnoreCase(ACTIVE)) {
            menu.findItem(com.batua.android.merchant.R.id.action_edit).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case com.batua.android.merchant.R.id.action_edit:
                Bundle bundle = new Bundle();
                bundle.putParcelable("Merchant", Parcels.wrap(merchant));
                startActivity(EditMerchantActivity.class, bundle);
                finish();
                break;

            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.view_images)
    void onViewImagesClick() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("GalleryImages", Parcels.wrap(merchant.getGalleries()));
        startActivity(GalleryImagesActivity.class, bundle);
        finish();
    }

    public void showMerchantDetail(Merchant merchant) {
        scrollView.setVisibility(View.VISIBLE);
        setToolBar(merchant.getName());
        txtShortCode.setText(merchant.getShortCode());
        if (merchant.getAddress() != null) {
            txtAddress.setText(merchant.getAddress());
        }

        txtFees.setText(merchant.getFees() + "%");

        if (merchant.getEmail() != null ) {
            txtEmail.setText(merchant.getEmail());
        }

        txtCall.setText(merchant.getPhone().toString());

        if (merchant.getAccountNumber() != null) {
            txtAccountNumber.setText(merchant.getAccountNumber().toString());
        }

        if (merchant.getAccountHolder() != null) {
            txtAccountName.setText(merchant.getAccountHolder());
        }

        if (merchant.getBankName() != null) {
            txtBank.setText(merchant.getBankName());
        }

        if (merchant.getBranchName() != null) {
            txtBranch.setText(merchant.getBranchName());
        }

        if (merchant.getIfscCode() != null) {
            txtIfsc.setText(merchant.getIfscCode());
        }

        String status = merchant.getStatus();
        txtStatus.setText(status);

        if (status.equals(ACTIVE)) {
            txtStatus.setTextColor(ContextCompat.getColor(this, R.color.green));
        } else if (status.equals(DRAFTED)) {
            txtStatus.setTextColor(ContextCompat.getColor(this, R.color.red_selected));
        } else {
            txtStatus.setTextColor(ContextCompat.getColor(this, R.color.yellow_dark));
        }

        if (merchant.getProfileImageUrl() != null) {
            Glide.with(this).load(merchant.getProfileImageUrl()).placeholder(R.drawable.profile_pic_container).centerCrop().into(merchantDp);
        }

        if (merchant.getGalleries().size() == 0) {
            galleryLayout.setVisibility(View.GONE);
            return;
        }

        galleryLayout.setVisibility(View.VISIBLE);

        if (merchant.getGalleries().size() == 1) {
            Glide.with(this).load(merchant.getGalleries().get(0).getUrl()).centerCrop().into(firstGalleyImage);
            return;
        }
        if (merchant.getGalleries().size() == 2) {
            Glide.with(this).load(merchant.getGalleries().get(0).getUrl()).centerCrop().into(firstGalleyImage);
            Glide.with(this).load(merchant.getGalleries().get(1).getUrl()).centerCrop().into(secondGalleyImage);
            return;
        }
        if (merchant.getGalleries().size() == 3) {
            Glide.with(this).load(merchant.getGalleries().get(0).getUrl()).centerCrop().into(firstGalleyImage);
            Glide.with(this).load(merchant.getGalleries().get(1).getUrl()).centerCrop().into(secondGalleyImage);
            Glide.with(this).load(merchant.getGalleries().get(2).getUrl()).centerCrop().into(thirdGalleyImage);
            return;
        }
        if (merchant.getGalleries().size() == 4) {
            Glide.with(this).load(merchant.getGalleries().get(0).getUrl()).centerCrop().into(firstGalleyImage);
            Glide.with(this).load(merchant.getGalleries().get(1).getUrl()).centerCrop().into(secondGalleyImage);
            Glide.with(this).load(merchant.getGalleries().get(2).getUrl()).centerCrop().into(thirdGalleyImage);
            Glide.with(this).load(merchant.getGalleries().get(3).getUrl()).centerCrop().into(fourthGalleyImage);
            return;
        }
    }

    private void setToolBar(String name) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
