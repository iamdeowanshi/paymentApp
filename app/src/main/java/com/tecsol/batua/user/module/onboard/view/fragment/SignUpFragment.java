package com.tecsol.batua.user.module.onboard.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseFragment;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.common.util.social.SocialAuth;
import com.tecsol.batua.user.module.onboard.view.activity.OnBoardActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author Arnold.
 */
public class SignUpFragment extends BaseFragment {

    @Inject ViewUtil viewUtil;
    @Inject Bakery bakery;

    @Bind(R.id.edt_mobile) EditText edtMobile;
    @Bind(R.id.edt_email) EditText edtEmail;
    @Bind(R.id.input_layout_mobile) TextInputLayout inputLayoutMobile;
    @Bind(R.id.input_layout_email) TextInputLayout inputLayoutEmail;
    @Bind(R.id.edt_password) EditText edtPassword;

    @OnClick({R.id.btn_sign_up, R.id.btn_sign_up_fb, R.id.btn_sign_up_gplus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                performNormalSignUp();
                break;

            case R.id.btn_sign_up_fb:
                ((OnBoardActivity)getActivity()).socialSignUp(SocialAuth.SocialType.FACEBOOK_SIGNUP);
                break;

            case R.id.btn_sign_up_gplus:
                ((OnBoardActivity)getActivity()).socialSignUp(SocialAuth.SocialType.GOOGLE_SIGNUP);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void performNormalSignUp() {
        viewUtil.hideKeyboard(getActivity());

        if (!(isValidNumber(edtMobile.getText()) || isValidEmail(edtEmail.getText()))){
            bakery.snackShort(getContentView(), "Invalid email or mobile number");

            return;
        }

        if (edtPassword.getText().toString().isEmpty()) {
            bakery.snackShort(getContentView(), "Password cannot be empty");
            return;
        }

        Long mobile = Long.valueOf(edtMobile.getText().toString());
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (mobile.longValue()!=0){
            User user = new User(mobile.longValue(), email, password);
            ((OnBoardActivity)getActivity()).normalSignUp(user);
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

        try {
            Long.valueOf(target.toString());
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }

}
