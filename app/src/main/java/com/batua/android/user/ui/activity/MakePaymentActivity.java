package com.batua.android.user.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class MakePaymentActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        setToolBar();
    }

    @OnClick(R.id.txt_promo_code)
    public void startPromocode(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_promocode);
        dialog.show();

        TextView positiveButton = (TextView) dialog.findViewById(R.id.button_ok);
        TextView negativeButton = (TextView) dialog.findViewById(R.id.button_cancel);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
