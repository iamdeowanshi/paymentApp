package com.tecsol.batua.user.module.onboard.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseFragment;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.InternetUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.common.util.social.AuthResult;
import com.tecsol.batua.user.module.common.util.social.SocialAuth;
import com.tecsol.batua.user.module.common.util.social.SocialAuthCallback;
import com.tecsol.batua.user.module.onboard.presenter.SignUpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.SignUpViewInteractor;
import com.tecsol.batua.user.module.onboard.view.activity.MobileNumberActivity;
import com.tecsol.batua.user.module.onboard.view.activity.OnBoardActivity;

import java.math.BigInteger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author Arnold.
 */
public class SignUpFragment extends BaseFragment implements SignUpViewInteractor, SocialAuthCallback{

    @Inject ViewUtil viewUtil;
    @Inject Bakery bakery;
    @Inject SignUpPresenter signUpPresenter;

    @Bind(R.id.edt_mobile) EditText edtMobile;
    @Bind(R.id.edt_email) EditText edtEmail;
    @Bind(R.id.input_layout_mobile) TextInputLayout inputLayoutMobile;
    @Bind(R.id.input_layout_email) TextInputLayout inputLayoutEmail;
    @Bind(R.id.edt_password) EditText edtPassword;
    @Bind(R.id.progressBar) ProgressBar progressBar;

    private SocialAuth socialAuth;

    @OnClick({R.id.btn_sign_up, R.id.btn_sign_up_fb, R.id.btn_sign_up_gplus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                performNormalSignUp();
                break;

            case R.id.btn_sign_up_fb:
                break;

            case R.id.btn_sign_up_gplus:
                viewUtil.hideKeyboard(getActivity());
                if (!InternetUtil.hasInternetConnection(getActivity())){
                    ((OnBoardActivity)getActivity()).showNoInternetTitleDialog(getActivity());

                    return;
                }

                socialAuth.login(SocialAuth.SocialType.GOOGLE_SIGNUP);
                break;
        }
    }

    @OnTextChanged(R.id.edt_email)
    void verifyEmail(CharSequence email){
        inputLayoutEmail.setErrorEnabled(false);

        if (email == null || email.toString().isEmpty()) {
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputLayoutEmail.setErrorEnabled(true);
            inputLayoutEmail.setError("Invalid Email");
            return;
        }
    }

    @OnTextChanged(R.id.edt_mobile)
    void verifyMobile(CharSequence mobile){
        inputLayoutMobile.setErrorEnabled(false);
        if (!isValidNumber(mobile)) {
            inputLayoutMobile.setErrorEnabled(true);
            inputLayoutMobile.setError("Invalid Mobile");
            return;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.component().inject(this);
        signUpPresenter.attachViewInteractor(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, null);
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

    //overidden method of presenter
    @Override
    public void onNormalSignUpSuccess(String message) {
        showDailog(getActivity(), "Success", "Please verify your phone");
        startActivity(MobileNumberActivity.class, null);
        getActivity().finish();
    }

    @Override
    public void onSocialSignUpSuccess(Integer userId) {
        showDailog(getActivity(), "Success", "Please verify your phone");
        startActivity(MobileNumberActivity.class, null);
        getActivity().finish();
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
        progressBar.setVisibility(View.GONE);
        bakery.snackShort(getContentView(), e.getMessage());
    }

    // overidden methods of SocialAuth
    @Override
    public void onSocialConnectionSuccess(AuthResult result) {
        Log.d("result-email", result.getAuthUser().getEmail());
        socialAuth.disconnect();
        signUpPresenter.socialSignUp(result.getAuthUser().getEmail(), result.getAuthUser().getFirstName() + result.getAuthUser().getLastName(), result.getAuthUser().getSocialId());
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(Throwable throwable) {
        bakery.toastShort(throwable.getMessage());
    }

    private void performNormalSignUp() {
        viewUtil.hideKeyboard(getActivity());
        Long mobile = 0l;
        try {
            mobile = Long.valueOf(edtMobile.getText().toString());
        } catch (NumberFormatException e){
            bakery.snackShort(getContentView(), "Invalid Phone");
        }

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (!(isValidNumber(edtMobile.getText()) || isValidEmail(edtEmail.getText()))){
            bakery.snackShort(getContentView(), "Invalid email or mobile number");

            return;
        }

        if (edtPassword.getText().toString().isEmpty()) {
            bakery.snackShort(getContentView(), "Password cannot be empty");
            return;
        }

        if (mobile.longValue()!=0){
            User user = new User(mobile.longValue(), email, password);
            signUpPresenter.normalSignUp(user);
            return;
        }
        bakery.snackShort(getContentView(), "Invalid Phone");

    }

    private final static boolean isValidEmail(CharSequence target) {
        if (target == null || target.toString().isEmpty()) {
            return true;
        }

        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private final static boolean isValidNumber(CharSequence target) {
        if (target == null || target.toString().isEmpty() || target.length() != 10) {
            return false;
        }

        return true;
    }

}
