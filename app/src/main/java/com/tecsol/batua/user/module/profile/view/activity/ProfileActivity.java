package com.tecsol.batua.user.module.profile.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.batua.android.user.R;
import com.squareup.picasso.Picasso;
import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.InternetUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.onboard.view.activity.ChangePasswordActivity;
import com.tecsol.batua.user.module.onboard.view.activity.ChangePinActivity;
import com.tecsol.batua.user.module.onboard.view.activity.OnBoardActivity;
import com.tecsol.batua.user.module.onboard.view.activity.SetPinActivity;
import com.tecsol.batua.user.module.profile.presenter.PinStatusPresenter;
import com.tecsol.batua.user.module.profile.presenter.PinStatusViewInteractor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class ProfileActivity extends BaseActivity implements PinStatusViewInteractor{

    @Inject PreferenceUtil preferenceUtil;
    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;
    @Inject PinStatusPresenter pinStatusPresenter;

    private final String ENABLE = "ENABLE";
    private final String DISABLE = "DISABLE";
    private final String SET_PIN = "Set PIN";
    private final String CHANGE_PIN = "Change PIN";

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.txt_enable_pin) TextView txtEnablePin;
    @Bind(R.id.txt_set_or_change_pin) TextView txtSetOrChangePin;
    @Bind(R.id.edt_display_name) TextView txtName;
    @Bind(R.id.txt_merchant_email) TextView txtMerchantEmail;
    @Bind(R.id.txt_merchant_phone) TextView txtPhone;
    @Bind(R.id.img_profile) ImageView imgProfile;
    @Bind(R.id.pin_status_progressBar) ProgressBar pinStatusProgressbar;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Injector.component().inject(this);
        pinStatusPresenter.attachViewInteractor(this);

        if(user == null){
            user = (User)preferenceUtil.read(preferenceUtil.USER, User.class);
        }

        setToolBar();
        initializeProfile();
        getPinStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBar();
        initializeProfile();
        getPinStatus();
    }

    @OnClick(R.id.txt_enable_pin)
    void enablePin(){
        String pinState = txtEnablePin.getText().toString();

        if (!InternetUtil.hasInternetConnection(this)) {
            showNoInternetTitleDialog(this);
            return;
        }

        if (pinState.equals(ENABLE)) {
            Pin pin = new Pin();
            pin.setUserId(user.getId());
            pin.setIsPinActivated(true);

            pinStatusPresenter.updatePinStatus(pin);

            return;
        }

        Pin pin = new Pin();
        pin.setUserId(user.getId());
        pin.setIsPinActivated(false);

        pinStatusPresenter.updatePinStatus(pin);
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

    @OnClick(R.id.img_edit_profile)
    void editProfile(){
        startActivity(EditProfileActivity.class, null);
    }

    @OnClick(R.id.btn_logout)
    void logout(){
        if (user!=null) {
            Pin pin = new Pin();
            pin.setUserId(user.getId());
            pin.setDeviceId(preferenceUtil.readString(preferenceUtil.DEVICE_ID, ""));
            pinStatusPresenter.logOut(pin);
            return;
        }
        startActivity(OnBoardActivity.class, null);
        finish();
    }

    @OnClick(R.id.txt_change_password)
    void changePassword(){
        startActivity(ChangePasswordActivity.class, null);
    }

    @Override
    public void onPinStatusRecieved(User user) {

        this.user.setIsPinActivated(user.isPinActivated());
        this.user.setIsPinSet(user.isPinSet());
        preferenceUtil.save(preferenceUtil.USER, this.user);

        if (user.isPinActivated() && !user.isPinSet()) {
            txtEnablePin.setText(DISABLE);
            txtSetOrChangePin.setText(SET_PIN);
            txtEnablePin.setTextColor(Color.parseColor("#FF4081")); // Pink Color
            showPinAction();
            return;
        }

        if (user.isPinActivated() && user.isPinSet()) {
            txtEnablePin.setText(DISABLE);
            txtSetOrChangePin.setText(CHANGE_PIN);
            txtEnablePin.setTextColor(Color.parseColor("#FF4081")); // Pink Color
            showPinAction();
            return;
        }

        txtEnablePin.setText(ENABLE);
        txtEnablePin.setTextColor(Color.parseColor("#2196f3")); // blue Color
        hidePinAction();
    }

    @Override
    public void onPinStatusChanged(User user) {
        this.user.setIsPinActivated(user.isPinActivated());
        preferenceUtil.save(preferenceUtil.USER, this.user);

        if (user.isPinActivated()) {
            txtEnablePin.setText(DISABLE);
            txtEnablePin.setTextColor(Color.parseColor("#FF4081")); // Pink Color
            showPinAction();
            if (!this.user.isPinSet()) {
                txtSetOrChangePin.setText(SET_PIN);
                return;
            }
            txtSetOrChangePin.setText(CHANGE_PIN);
            return;
        }
        txtEnablePin.setText(ENABLE);
        txtEnablePin.setTextColor(Color.parseColor("#2196f3")); // blue Color
        hidePinAction();

    }

    @Override
    public void onLoggedOutSuccess(String message) {
        preferenceUtil.remove(preferenceUtil.USER);
        preferenceUtil.remove(preferenceUtil.IS_LOGGED_IN);
        preferenceUtil.remove(preferenceUtil.LAST_LOCATION);
        startActivity(OnBoardActivity.class, null);
        finish();
    }

    @Override
    public void onNetworkCallProgress() {
        pinStatusProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        pinStatusProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        if (e.getMessage().equals("UserId, Token And DeviceId Required")) {
            startActivity(OnBoardActivity.class, null);
            finish();
        }
        viewUtil.hideKeyboard(this);
        pinStatusProgressbar.setVisibility(View.GONE);
        bakery.snackShort(getContentView(), e.getMessage());
    }

    private void getPinStatus() {
        user = (User)preferenceUtil.read(preferenceUtil.USER, User.class);
        if (!InternetUtil.hasInternetConnection(this)) {
            showNoInternetTitleDialog(this);
            return;
        }
        pinStatusPresenter.getPinStatus(user.getId());
    }

    private void initializeProfile() {
        User user = (User) preferenceUtil.read(preferenceUtil.USER, User.class);
        txtName.setText(user.getName());
        txtMerchantEmail.setText(user.getEmail());
        txtPhone.setText(user.getPhone() + "");
        Picasso.with(this).load(user.getProfileImageUrl()).placeholder(R.drawable.profile_pic_container).fit().into(imgProfile);
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

    private void hidePinAction(){
        txtSetOrChangePin.setVisibility(View.GONE);
    }

    private void showPinAction(){
        txtSetOrChangePin.setVisibility(View.VISIBLE);
    }

}
