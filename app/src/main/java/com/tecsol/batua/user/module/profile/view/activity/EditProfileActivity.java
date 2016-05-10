package com.tecsol.batua.user.module.profile.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.batua.android.user.R;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.ImageUtil;
import com.tecsol.batua.user.module.common.util.InternetUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.onboard.presenter.ImageUploadPresenter;
import com.tecsol.batua.user.module.onboard.presenter.ImageUploadViewInteractor;
import com.tecsol.batua.user.module.profile.presenter.ProfilePresenter;
import com.tecsol.batua.user.module.profile.presenter.ProfileViewInteractor;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class EditProfileActivity extends BaseActivity implements ProfileViewInteractor, ImageUploadViewInteractor, ImageUtil.ImageUtilCallback{

    @Inject ImageUtil imageUtil;
    @Inject ProfilePresenter profilePresenter;
    @Inject ImageUploadPresenter imageUploadPresenter;
    @Inject Bakery bakery;
    @Inject PreferenceUtil preferenceUtil;
    @Inject ViewUtil viewUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.img_profile) CircularImageView imgProfile;
    @Bind(R.id.edt_display_name) EditText edtName;
    @Bind(R.id.edt_merchant_email_title) EditText edtMerchantEmail;
    @Bind(R.id.edt_user_phone_num) TextView txtNum;
    @Bind(R.id.progressBar4) ProgressBar profileProgress;
    @Bind(R.id.progressBar5) ProgressBar progressBar;

    private User user = new User();
    private User savedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.user.R.layout.activity_edit_profile);
        Injector.component().inject(this);
        profilePresenter.attachViewInteractor(this);
        imageUploadPresenter.attachViewInteractor(this);

        setToolBar();
        savedUser = (User) preferenceUtil.read(preferenceUtil.USER, User.class);
        loadProfileData(savedUser);

        imageUtil.setImageUtilCallback(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadProfileData(User user) {
        txtNum.setText(user.getPhone()+"");
        edtMerchantEmail.setText(user.getEmail());
        edtName.setText(user.getName());
        Picasso.with(this).load(user.getProfileImageUrl()).placeholder(R.drawable.profile_pic_container).fit().into(imgProfile);
        if(user.getEmail() == null || user.getEmail().isEmpty()){
            edtMerchantEmail.setFocusable(true);
            return;
        }
        edtMerchantEmail.setFocusable(false);
    }

    @OnClick({R.id.txt_upload, R.id.img_profile})
    void uploadPhoto(){
        if (!InternetUtil.hasInternetConnection(this)){
            showNoInternetTitleDialog(this);

            return;
        }
        imageUtil.getImage(this);
    }

    @OnClick(R.id.btn_save_profile)
    void saveProfile() {

        if (!InternetUtil.hasInternetConnection(this)) {
            showNoInternetTitleDialog(this);
            return;
        }

        if (!isValidEmail(edtMerchantEmail.getText().toString())) {
            bakery.snackShort(getContentView(), "Invalid email");
            return;
        }

        if (savedUser.getEmail().isEmpty()) {
            user.setEmail(edtMerchantEmail.getText().toString());
        }

        user.setName(edtName.getText().toString());
        user.setId(((User) preferenceUtil.read(preferenceUtil.USER, User.class)).getId());
        profilePresenter.updateProfile(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUtil.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // overidden methods od Profile Update
    @Override
    public void onProfileUpdated(User user) {
        savedUser = (User) preferenceUtil.read(preferenceUtil.USER, User.class);
        savedUser.setProfileImageUrl(user.getProfileImageUrl());
        savedUser.setPhone(user.getPhone());
        savedUser.setName(user.getName());
        preferenceUtil.save(preferenceUtil.USER, savedUser);
        finish();
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
        viewUtil.hideKeyboard(this);
        progressBar.setVisibility(View.GONE);

        if (e == null || e.getMessage() == null) {
            return;
        }

        if (e.getMessage().startsWith("failed to connect")) {
            bakery.snackShort(getContentView(), "Server error");
            return;
        }

        bakery.snackShort(getContentView(), e.getMessage());
    }

    // overidden methods of ImageUpload
    @Override
    public void showProfileUploadingProgress() {
        profileProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProfileUploadingProgress() {
        profileProgress.setVisibility(View.GONE);
    }

    @Override
    public void onProfileImageUploadSuccess(String profileImage) {
        user.setProfileImageUrl(profileImage);
        Picasso.with(this).load(profileImage).into(imgProfile);
    }

    // overidden methods of ImageUtil
    @Override
    public void onSuccess(Uri uri, File file) {
        if (!InternetUtil.hasInternetConnection(this)) {
            showNoInternetTitleDialog(this);
            return;
        }

        imageUploadPresenter.uploadImage(file);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private final static boolean isValidEmail(CharSequence target) {
        if (target == null || target.toString().isEmpty()) {
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
