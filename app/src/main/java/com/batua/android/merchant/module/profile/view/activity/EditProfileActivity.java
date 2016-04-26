package com.batua.android.merchant.module.profile.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.ImageUtil;
import com.batua.android.merchant.module.common.util.PreferenceUtil;
import com.batua.android.merchant.module.merchant.presenter.ImageUploadPresenter;
import com.batua.android.merchant.module.merchant.presenter.ImageUploadViewInteractor;
import com.batua.android.merchant.module.profile.presenter.ProfilePresenter;
import com.batua.android.merchant.module.profile.presenter.ProfileViewInteractor;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class EditProfileActivity extends BaseActivity implements ImageUtil.ImageUtilCallback, ActivityCompat.OnRequestPermissionsResultCallback, ProfileViewInteractor, ImageUploadViewInteractor {

    private static final int PROFILE_FLAG = 1;

    @Inject ImageUtil imageUtil;
    @Inject Bakery bakery;
    @Inject ProfilePresenter presenter;
    @Inject ImageUploadPresenter imageUploadPresenter;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.img_profile) CircularImageView imgProfile;
    @Bind(R.id.txt_display_name) EditText txtDisplayName;
    @Bind(R.id.txt_merchant_email) TextView txtEmailTitle;
    @Bind(R.id.edt_merchant_current_password) EditText edtMerchantCurrentPassword;
    @Bind(R.id.edt_merchant_new_password) EditText edtMerchantNewPassword;
    @Bind(R.id.edt_merchant_confirm_password) EditText edtMerchantConfirmPassword;
    @Bind(R.id.progress) ProgressBar progressBar;
    @Bind(R.id.input_layout_merchant_confirm_password) TextInputLayout inputLayoutMerchantConfirmPassword;

    private TextView title;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        Injector.component().inject(this);
        presenter.attachViewInteractor(this);
        imageUploadPresenter.attachViewInteractor(this);

        user = Parcels.unwrap(getIntent().getParcelableExtra("User"));

        loadUser(user);

        imageUtil.setImageUtilCallback(this);

        setToolBar();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUtil.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(Uri uri, File file) {
        Picasso.with(this).load(uri).into(imgProfile);
        imageUploadPresenter.uploadImage(file, PROFILE_FLAG);
    }

    @OnClick({R.id.txt_upload, R.id.txt_save_basic_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_upload:
                imageUtil.getImage(this);
                break;
            case R.id.txt_save_basic_info:
                updateProfile();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(ProfileActivity.class, null);
    }

    @Override
    public void showProfile(User user) {
        preferenceUtil.save(preferenceUtil.USER, user);
        startActivity(ProfileActivity.class, null);
        finish();
    }

    @Override
    public void incorrectPassword() {
        bakery.snackShort(getContentView(), "Invalid password entered");
    }

    @Override
    public void onNetworkCallProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        bakery.snackShort(getContentView(), "Network Error !");
    }

    private void loadUser(User user) {
        Picasso.with(this).load(user.getProfileImageUrl()).into(imgProfile);
        txtDisplayName.setText(user.getName());
        txtEmailTitle.setText(user.getEmail());
    }

    private void updateProfile() {
        if (isPasswordValid()) {
            inputLayoutMerchantConfirmPassword.setErrorEnabled(false);
            user.setName(txtDisplayName.getText().toString());
            user.setCurrentPassword(edtMerchantCurrentPassword.getText().toString());
            user.setNewPassword(edtMerchantNewPassword.getText().toString());
            presenter.updateProfile(user);
            return;
        }

        inputLayoutMerchantConfirmPassword.setError("Password do not match");
        inputLayoutMerchantConfirmPassword.setErrorEnabled(true);
    }

    @Override
    public void showUploadingProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUploadingProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMerchantImageUploadSuccess(String merchantImage) {

    }

    @Override
    public void onProfileImageUploadSuccess(String imageUrl) {
        user.setProfileImageUrl(imageUrl);
        Picasso.with(this).load(imageUrl).into(imgProfile);
    }

    private boolean isPasswordValid() {
        return edtMerchantNewPassword.getText().toString().equals(edtMerchantConfirmPassword.getText().toString());
    }

}
