package com.tecsol.batua.user.module.profile.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.onboard.view.activity.ChangePasswordActivity;
import com.tecsol.batua.user.module.onboard.view.activity.ChangePinActivity;
import com.tecsol.batua.user.module.onboard.view.activity.OnBoardActivity;
import com.tecsol.batua.user.module.onboard.view.activity.SetPinActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class ProfileActivity extends BaseActivity {

    private String ENABLE = "ENABLE";
    private String DISABLE = "DISABLE";
    private String SET_PIN = "Set PIN";
    private String CHANGE_PIN = "Change PIN";

    @Bind(com.batua.android.user.R.id.toolbar)
    Toolbar toolbar;
    @Bind(com.batua.android.user.R.id.txt_enable_pin) TextView txtEnablePin;
    @Bind(com.batua.android.user.R.id.txt_set_or_change_pin) TextView txtSetOrChangePin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.user.R.layout.activity_profile);

        setToolBar();
    }

    @OnClick(com.batua.android.user.R.id.txt_enable_pin)
    void enablePin(){
        String pinState = txtEnablePin.getText().toString();
        if (pinState.equals(ENABLE)) {
            txtEnablePin.setText(DISABLE);
            txtEnablePin.setTextColor(Color.parseColor("#FF4081")); // Pink Color
            txtSetOrChangePin.setVisibility(View.VISIBLE);

            return;
        }
        txtEnablePin.setText(ENABLE);
        txtEnablePin.setTextColor(Color.parseColor("#2196f3")); // Blue Color
        txtSetOrChangePin.setVisibility(View.GONE);
    }

    @OnClick(com.batua.android.user.R.id.txt_set_or_change_pin)
    void setOrChangePin() {
        String pinSetOrChange = txtSetOrChangePin.getText().toString();
        if (pinSetOrChange.equals(SET_PIN)) {
            startActivity(SetPinActivity.class, null);
            txtSetOrChangePin.setText(CHANGE_PIN);

            return;
        }
        startActivity(ChangePinActivity.class, null);
        txtSetOrChangePin.setText(SET_PIN);
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

}
