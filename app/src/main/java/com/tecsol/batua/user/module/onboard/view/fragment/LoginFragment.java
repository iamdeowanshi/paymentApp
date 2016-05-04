package com.tecsol.batua.user.module.onboard.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.batua.android.user.R;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseFragment;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.common.util.social.SocialAuth;
import com.tecsol.batua.user.module.onboard.view.activity.MobileNumberActivity;
import com.tecsol.batua.user.module.onboard.view.activity.OnBoardActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Arnold.
 */
public class LoginFragment extends BaseFragment {

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.edt_email) EditText edtEmail;
    @Bind(R.id.input_layout_email) TextInputLayout inputLayoutEmail;
    @Bind(R.id.edt_password) EditText edtPassword;
    @Bind(R.id.input_layout_password) TextInputLayout inputLayoutPassword;
    @Bind(R.id.btn_login) Button btnLogin;
    @Bind(R.id.layout_social_buttons) LinearLayout layoutSocialButtons;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.component().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.txt_forgot_password, R.id.btn_login, R.id.btn_fb, R.id.btn_gplus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_forgot_password:
                startActivity(MobileNumberActivity.class, null);
                break;

            case R.id.btn_login:
                performLogin();
                break;

            case R.id.btn_fb:
                ((OnBoardActivity)getActivity()).socialLogin(SocialAuth.SocialType.FACEBOOK_LOGIN);
                break;

            case R.id.btn_gplus:
                ((OnBoardActivity)getActivity()).socialLogin(SocialAuth.SocialType.GOOGLE_LOGIN);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void performLogin() {
        viewUtil.hideKeyboard(getActivity());
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String deviceId = preferenceUtil.readString(preferenceUtil.DEVICE_ID, "");
        boolean isValid = isValidEmail(edtEmail.getText()) || isValidNumber(edtEmail.getText());

        if (isValid) {
            ((OnBoardActivity)getActivity()).normalLogin(email, password, deviceId);
            inputLayoutEmail.setErrorEnabled(false);

            return;
        }

        inputLayoutEmail.setErrorEnabled(true);
        inputLayoutEmail.setError("Invalid email or mobile number");
    }

    private final static boolean isValidEmail(CharSequence target) {
        if (target == null || target.toString().isEmpty()) {
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private final static boolean isValidNumber(CharSequence target) {
        if (target == null || target.toString().isEmpty() || target.length() != 10) {
            return false;
        }

        try {
            Long.valueOf(target.toString());
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }

}
