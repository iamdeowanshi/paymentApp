package com.tecsol.batua.user.module.onboard.view.fragment;

import android.content.Intent;
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
import android.widget.ProgressBar;

import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseFragment;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.InternetUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.common.util.social.AuthResult;
import com.tecsol.batua.user.module.common.util.social.SocialAuth;
import com.tecsol.batua.user.module.common.util.social.SocialAuthCallback;
import com.tecsol.batua.user.module.dashboard.view.activity.HomeActivity;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenter;
import com.tecsol.batua.user.module.onboard.presenter.LoginViewInteractor;
import com.tecsol.batua.user.module.onboard.view.activity.MobileNumberActivity;
import com.tecsol.batua.user.module.onboard.view.activity.OnBoardActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * @author Arnold.
 */
public class LoginFragment extends BaseFragment implements SocialAuthCallback, LoginViewInteractor {

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;
    @Inject LoginPresenter loginPresenter;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.edt_email) EditText edtEmail;
    @Bind(R.id.input_layout_email) TextInputLayout inputLayoutEmail;
    @Bind(R.id.edt_password) EditText edtPassword;
    @Bind(R.id.input_layout_password) TextInputLayout inputLayoutPassword;
    @Bind(R.id.btn_login) Button btnLogin;
    @Bind(R.id.layout_social_buttons) LinearLayout layoutSocialButtons;
    @Bind(R.id.progressBar2) ProgressBar progressBar;

    private SocialAuth socialAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.component().inject(this);
        loginPresenter.attachViewInteractor(this);
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
                startActivity(HomeActivity.class, null);
                //socialAuth.login(SocialAuth.SocialType.FACEBOOK_LOGIN);
                break;

            case R.id.btn_gplus:
                viewUtil.hideKeyboard(getActivity());
                if (!InternetUtil.hasInternetConnection(getActivity())){
                    ((OnBoardActivity)getActivity()).showNoInternetTitleDialog(getActivity());

                    return;
                }

                socialAuth.login(SocialAuth.SocialType.GOOGLE_LOGIN);
                break;
        }
    }

    // overridden methods of Social Auth
    @Override
    public void onSocialConnectionSuccess(AuthResult result) {
        Timber.d(result.toString());
        loginPresenter.socialLogin(result.getAuthUser().getEmail(), "deviceId", "", result.getAuthUser().getSocialId());
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void validate() {
        viewUtil.hideKeyboard(getActivity());
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String deviceId = "";
        boolean isValid = isValidEmail(edtEmail.getText()) || isValidNumber(edtEmail.getText());

        if (isValid) {
            loginPresenter.normalLogin(email, password, deviceId);
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

        return true;
    }

    @Override
    public void onNormalLoginSuccess(User user) {
        preferenceUtil.save(preferenceUtil.USER, user);
        startActivity(HomeActivity.class, null);
        getActivity().finish();
    }

    @Override
    public void onSocialLoginSuccess(User user) {
        preferenceUtil.save(preferenceUtil.USER, user);
        startActivity(HomeActivity.class, null);
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
}
