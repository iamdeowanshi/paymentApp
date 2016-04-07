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
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.merchant.presenter.MerchantDetailPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantDetailViewInteractor;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MerchantDetailsActivity extends BaseActivity implements MerchantDetailViewInteractor{

    private static final String ACTIVE = "Active";
    private static final String DRAFTED = "Drafted";

    @Inject MerchantDetailPresenter presenter;

    @Bind(com.batua.android.merchant.R.id.toolbar_title) TextView title;
    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;
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
    @Bind(R.id.fifth_image) ImageView fifthGalleyImage;

    private Merchant merchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_merchant_details);

        Injector.component().inject(this);
        presenter.attachViewInteractor(this);
        merchant = Parcels.unwrap(getIntent().getParcelableExtra("MerchantDetail"));

        hideMerchantDetail();
        merchantDetail(merchant);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.batua.android.merchant.R.menu.menu_merchant_details, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case com.batua.android.merchant.R.id.action_edit:
                startActivity(EditMerchantActivity.class, null);
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

    @OnClick(com.batua.android.merchant.R.id.view_images)
    void onViewImagesClick() {
        startActivity(GalleryImagesActivity.class, null);
        finish();
    }

    public void showMerchantDetail() {
        scrollView.setVisibility(View.VISIBLE);
    }

    public void hideMerchantDetail() {
        scrollView.setVisibility(View.GONE);
    }

    public void merchantDetail(Merchant merchant) {

        showMerchantDetail();
        setToolBar(merchant.getName());
        txtShortCode.setText(merchant.getShortCode());
        txtAddress.setText(merchant.getAddress());
        txtFees.setText(merchant.getFees() + "%");
        txtEmail.setText(merchant.getEmail());
        txtCall.setText(merchant.getPhone().toString());
        txtAccountNumber.setText(merchant.getAccountNumber().toString());
        txtAccountName.setText(merchant.getAccountHolder());
        txtBank.setText(merchant.getBankName());
        txtBranch.setText(merchant.getBranchName());
        txtIfsc.setText(merchant.getIfscCode());
        String status = merchant.getStatus();
        txtStatus.setText(status);

        if (status.equals(ACTIVE)) {
            txtStatus.setTextColor(ContextCompat.getColor(this, R.color.green));
        } else if (status.equals(DRAFTED)) {
            txtStatus.setTextColor(ContextCompat.getColor(this, R.color.red_selected));
        } else {
            txtStatus.setTextColor(ContextCompat.getColor(this, R.color.yellow_dark));
        }

        if (merchant.getGalleries().size() == 0) {
            galleryLayout.setVisibility(View.GONE);
            return;
        }

        galleryLayout.setVisibility(View.VISIBLE);
    }

    private void setToolBar(String name) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(com.batua.android.merchant.R.id.toolbar_title);
        title.setText(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
