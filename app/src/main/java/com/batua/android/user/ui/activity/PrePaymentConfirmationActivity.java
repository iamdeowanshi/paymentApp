package com.batua.android.user.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class PrePaymentConfirmationActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edt_enter_amount) EditText edtAmount;
    @Bind(R.id.amount_layout) RelativeLayout amountLayout;
    @Bind(R.id.default_wallet_layout) LinearLayout defaultWalletLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payment_confirmation);

        setToolBar();
        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        togglePaymentView(isOpen);
                    }
                });
    }

    @OnClick(R.id.txt_promo_code)
    void showPromocodeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

    @OnClick(R.id.default_wallet)
    void onWalletClick() {
        startActivity(PaymentActivity.class, null);
    }

    @OnClick(R.id.btn_make_payment)
    void onMakePaymentClick() {
        startActivity(PaymentFailureActivity.class, null);
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

    private void togglePaymentView(boolean isKeyboardVisible) {
        if (isKeyboardVisible) {
            RelativeLayout.LayoutParams amountLayoutLayoutParams = (RelativeLayout.LayoutParams) amountLayout.getLayoutParams();
            amountLayoutLayoutParams.setMargins(getPx(60), getPx(20),0, getPx(10));
            amountLayout.setLayoutParams(amountLayoutLayoutParams);

            RelativeLayout.LayoutParams defaultWalletLayoutLayoutParams = (RelativeLayout.LayoutParams) defaultWalletLayout.getLayoutParams();
            defaultWalletLayoutLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,0);
            defaultWalletLayoutLayoutParams.addRule(RelativeLayout.BELOW, R.id.amount_layout);
            defaultWalletLayout.setLayoutParams(defaultWalletLayoutLayoutParams);

            return;
        }

        RelativeLayout.LayoutParams amountLayoutLayoutParams = (RelativeLayout.LayoutParams) amountLayout.getLayoutParams();
        amountLayoutLayoutParams.setMargins(getPx(60), getPx(140),0, getPx(60));
        amountLayout.setLayoutParams(amountLayoutLayoutParams);

        RelativeLayout.LayoutParams defaultWalletLayoutLayoutParams = (RelativeLayout.LayoutParams) defaultWalletLayout.getLayoutParams();
        defaultWalletLayoutLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,R.id.scrollView);
        defaultWalletLayout.setLayoutParams(defaultWalletLayoutLayoutParams);
        
    }

    private int getPx(int dimensionDp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dimensionDp * density + 0.5f);
    }

}
