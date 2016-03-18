package com.batua.android.user.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;

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

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.txt_enable_pin) TextView txtEnablePin;
    @Bind(R.id.txt_set_or_change_pin) TextView txtSetOrChangePin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setToolBar();
    }

    @OnClick(R.id.txt_enable_pin)
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

    @OnClick(R.id.txt_set_or_change_pin)
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

    @OnClick(R.id.img_edit_profile)
    void editProfile(){
        startActivity(EditProfileActivity.class, null);
    }

    @OnClick(R.id.btn_logout)
    void logout(){
        startActivity(LoginActivity.class, null);
        finish();
    }

    @OnClick(R.id.txt_change_password)
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
