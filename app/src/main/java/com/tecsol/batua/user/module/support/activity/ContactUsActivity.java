package com.tecsol.batua.user.module.support.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.User.ContactUs;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.dashboard.presenter.ContactUsPresenter;
import com.tecsol.batua.user.module.dashboard.presenter.ContactUsViewInteractor;
import com.tecsol.batua.user.module.dashboard.view.activity.HomeActivity;
import com.tecsol.batua.user.module.onboard.view.activity.MobileNumberActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class ContactUsActivity extends BaseActivity implements ContactUsViewInteractor{

    @Inject ViewUtil viewUtil;
    @Inject Bakery bakery;
    @Inject ContactUsPresenter contactUsPresenter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edt_query) EditText edtQuery;
    @Bind(R.id.edt_email_title) EditText edtEmail;
    @Bind(R.id.query_progressBar) ProgressBar queryProgress;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Injector.component().inject(this);
        contactUsPresenter.attachViewInteractor(this);

        email = getIntent().getStringExtra("Email");
        setToolBar();
        setEmail(email);
    }

    @OnClick(R.id.btn_submit)
    void submitQuery(){

        if (!isValidEmail(edtEmail.getText())) {
            bakery.snackShort(getContentView(), "Please enter a valid email");
            return;
        }

        if (edtQuery.getText().toString().length() < 20) {
            bakery.snackShort(getContentView(), "Your query must be atleast 20 words");
            return;
        }

        ContactUs contactUs = new ContactUs();
        contactUs.setEmail(edtEmail.getText().toString());
        contactUs.setQuery(edtQuery.getText().toString());
        contactUsPresenter.contactBatua(contactUs);
        viewUtil.hideKeyboard(this);
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

    @Override
    public void onEmailSent(String message) {
        showeEmailSentDialog();
    }

    @Override
    public void onNetworkCallProgress() {
        queryProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        queryProgress.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        queryProgress.setVisibility(View.GONE);
        viewUtil.hideKeyboard(this);

        if (e.getMessage()== null || e.getMessage().isEmpty()){
            return;
        }

        if (e.getMessage().startsWith("failed to connect")) {
            bakery.snackShort(getContentView(), "Server error");
            return;
        }

        bakery.snackShort(getContentView(), e.getMessage());
    }

    public void setEmail(String email) {
        edtEmail.setText(email);
    }

    private final static boolean isValidEmail(CharSequence target) {
        if (target == null || target.toString().isEmpty()) {
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void showeEmailSentDialog() {
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);

        alertbuilder.setTitle("Query Received")
                .setMessage("Thank you for contacting us. We have received your query and will get in touch within next 24 hours")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(HomeActivity.class, null);
                        dialog.dismiss();
                        finish();
                    }
                });
        AlertDialog alert = alertbuilder.create();
        alert.show();
    }

}
