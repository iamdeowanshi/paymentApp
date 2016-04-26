package com.batua.android.merchant.module.profile.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.PreferenceUtil;
import com.batua.android.merchant.module.profile.presenter.ProfilePresenter;
import com.batua.android.merchant.module.profile.presenter.ProfileViewInteractor;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by febinp on 03/03/16.
 */
public class ProfileActivity extends BaseActivity{

    @Inject ProfilePresenter presenter;
    @Inject Bakery bakery;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.img_profile) CircularImageView imgProfile;
    @Bind(R.id.txt_display_name) TextView txtDisplayName;
    @Bind(R.id.txt_merchant_email) TextView txtMerchantEmail;
    @Bind(R.id.progress) ProgressBar progressBar;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Injector.component().inject(this);

        user = (User) preferenceUtil.read(preferenceUtil.USER, User.class);
        setToolBar();
        if (user != null) {
            loadUser(user);
            return;
        }
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_add_merchant).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Bundle bundle = new Bundle();
                bundle.putParcelable("User", Parcels.wrap(user));
                startActivity(EditProfileActivity.class, bundle);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadUser(User user) {
        this.user = user;
        Picasso.with(this).load(user.getProfileImageUrl()).into(imgProfile);
        txtDisplayName.setText(user.getName());
        txtMerchantEmail.setText(user.getEmail());
    }

}
