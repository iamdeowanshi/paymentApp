package com.batua.android.user.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseFragment;
import com.batua.android.user.ui.activity.MobileNumberActivity;
import com.batua.android.user.util.ViewUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Aaditya Deowanshi.
 */
public class SignUpFragment extends BaseFragment {

    @Inject ViewUtil viewUtil;

    @Bind(R.id.edt_mobile) EditText edtMobile;
    @Bind(R.id.input_layout_mobile) TextInputLayout inputLayoutMobile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, null);
    }

    @OnClick({R.id.btn_login, R.id.btn_fb, R.id.btn_gplus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                validate();
                break;

            case R.id.btn_fb:
                startActivity(MobileNumberActivity.class, null);
                break;

            case R.id.btn_gplus:
                startActivity(MobileNumberActivity.class, null);
                break;
        }
    }

    private void validate() {
        viewUtil.hideKeyboard(getActivity());
        boolean isValid = isValidNumber(edtMobile.getText());

        if (isValid) {
            startActivity(MobileNumberActivity.class, null);
            inputLayoutMobile.setErrorEnabled(false);

            return;
        }

        inputLayoutMobile.setErrorEnabled(true);
        inputLayoutMobile.setError("Invalid mobile number");
    }

    private final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return true;
        }

        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private final static boolean isValidNumber(CharSequence target) {
        if (target == null || target.length() != 10) {
            return false;
        }

        return Patterns.PHONE.matcher(target).matches();
    }

}
