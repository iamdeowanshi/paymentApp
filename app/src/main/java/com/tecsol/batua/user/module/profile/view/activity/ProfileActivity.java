package com.tecsol.batua.user.module.profile.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.onboard.view.activity.ChangePasswordActivity;
import com.tecsol.batua.user.module.onboard.view.activity.ChangePinActivity;
import com.tecsol.batua.user.module.onboard.view.activity.OnBoardActivity;
import com.tecsol.batua.user.module.onboard.view.activity.SetPinActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class ProfileActivity extends BaseActivity {

    @Inject PreferenceUtil preferenceUtil;

    private String ENABLE = "ENABLE";
    private String DISABLE = "DISABLE";
    private String SET_PIN = "Set PIN";
    private String CHANGE_PIN = "Change PIN";

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.txt_enable_pin) TextView txtEnablePin;
    @Bind(R.id.txt_set_or_change_pin) TextView txtSetOrChangePin;
    @Bind(R.id.edt_display_name) TextView txtName;
    @Bind(R.id.txt_merchant_email) TextView txtMerchantEmail;
    @Bind(R.id.txt_merchant_phone) TextView txtPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.user.R.layout.activity_profile);

        Injector.component().inject(this);

        setToolBar();
        initializeProfile();
        initializePinSettings();
    }

    private void initializeProfile() {
        User user = (User) preferenceUtil.read(preferenceUtil.USER, User.class);
        txtName.setText(user.getName());
        txtMerchantEmail.setText(user.getEmail());
        txtPhone.setText(user.getPhone()+"");
    }

    @OnClick(com.batua.android.user.R.id.txt_enable_pin)
    void enablePin(){
        String pinState = txtEnablePin.getText().toString();
        if (pinState.equals(ENABLE)) {
            txtEnablePin.setText(DISABLE);
            txtEnablePin.setTextColor(Color.parseColor("#FF4081")); // Pink Color
            showPinAction();

            return;
        }
        txtEnablePin.setText(ENABLE);
        txtEnablePin.setTextColor(Color.parseColor("#2196f3")); // Blue Color
        hidePinAction();
    }

    @OnClick(R.id.txt_set_or_change_pin)
    void setOrChangePin() {
        String pinSetOrChange = txtSetOrChangePin.getText().toString();
        if (pinSetOrChange.equals(SET_PIN)) {
            startActivity(SetPinActivity.class, null);

            return;
        }
        startActivity(ChangePinActivity.class, null);
    }

    @OnClick(com.batua.android.user.R.id.img_edit_profile)
    void editProfile(){
        startActivity(EditProfileActivity.class, null);
    }

    @OnClick(com.batua.android.user.R.id.btn_logout)
    void logout(){
        startActivity(OnBoardActivity.class, null);
        finish();
    }

    @OnClick(com.batua.android.user.R.id.txt_change_password)
    void changePassword(){
        startActivity(ChangePasswordActivity.class, null);
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

    private void initializePinSettings() {
        User user = (User)preferenceUtil.read(preferenceUtil.USER, User.class);
        if (user.isPinActivated() && user.isPinSet()) {
            txtEnablePin.setText(ENABLE);
            hidePinAction();
            return;
        }

        showPinAction();
        txtSetOrChangePin.setText(CHANGE_PIN);
    }

    private void hidePinAction(){
        txtSetOrChangePin.setVisibility(View.GONE);
    }

    private void showPinAction(){
        txtSetOrChangePin.setVisibility(View.VISIBLE);
    }

}
