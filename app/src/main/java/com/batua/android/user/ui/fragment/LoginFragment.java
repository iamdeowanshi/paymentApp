package com.batua.android.user.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseFragment;
import com.batua.android.user.ui.activity.HomeActivity;
import com.batua.android.user.ui.activity.MobileNumberActivity;
import com.batua.android.user.ui.activity.PaymentActivity;
import com.batua.android.user.ui.activity.ReviewActivity;
import com.batua.android.user.util.Bakery;
import com.batua.android.user.util.ViewUtil;
import com.batua.android.user.util.social.AuthResult;
import com.batua.android.user.util.social.SocialAuth;
import com.batua.android.user.util.social.SocialAuthCallback;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * @author Aaditya Deowanshi.
 */
public class LoginFragment extends BaseFragment implements SocialAuthCallback {

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;

    @Bind(R.id.edt_email) EditText edtEmail;
    @Bind(R.id.input_layout_email) TextInputLayout inputLayoutEmail;
    @Bind(R.id.input_password) EditText inputPassword;
    @Bind(R.id.input_layout_password) TextInputLayout inputLayoutPassword;

    private SocialAuth socialAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        socialAuth = new SocialAuth(getActivity());
        socialAuth.setCallback(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        socialAuth.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.txt_forgot_password, R.id.btn_login, R.id.btn_fb, R.id.btn_gplus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_forgot_password:
                startActivity(MobileNumberActivity.class, null);
                break;

            case R.id.btn_login:
                validate();
                break;

            case R.id.btn_fb:
                socialAuth.login(SocialAuth.SocialType.FACEBOOK);
                break;

            case R.id.btn_gplus:
                startActivity(HomeActivity.class, null);
                break;
        }
    }

    private void validate() {
        viewUtil.hideKeyboard(getActivity());
        boolean isValid = isValidEmail(edtEmail.getText()) || isValidNumber(edtEmail.getText());

        if (isValid) {
            startActivity(HomeActivity.class, null);
            inputLayoutEmail.setErrorEnabled(false);

            return;
        }

        inputLayoutEmail.setErrorEnabled(true);
        inputLayoutEmail.setError("Invalid email or mobile number");
    }

    private final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private final static boolean isValidNumber(CharSequence target) {
        if (target == null || target.length() != 10) {
            return false;
        }

        return Patterns.PHONE.matcher(target).matches();
    }

    @Override
    public void onSuccess(AuthResult result) {
        Timber.d(result.toString());
        startActivity(HomeActivity.class, null);
        getActivity().finish();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
