package com.batua.android.user.ui.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.util.ViewUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class PrePaymentConfirmationActivity extends BaseActivity {

    @Inject ViewUtil viewUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edt_enter_amount) EditText edtAmount;
    @Bind(R.id.scrollView) ScrollView scrollView;
    @Bind(R.id.amount_layout) RelativeLayout amountLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payment_confirmation);
        injectDependencies();

        setToolBar();
        viewUtil.keyboardListener(this, new ViewUtil.KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        togglePaymentView(isOpen);
                    }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return true;
            }});
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void togglePaymentView(boolean isKeyboardVisible) {
        if (isKeyboardVisible) {
            RelativeLayout.LayoutParams amountLayoutLayoutParams = (RelativeLayout.LayoutParams) amountLayout.getLayoutParams();
            amountLayoutLayoutParams.setMargins(getPx(60), getPx(5),0, getPx(15));
            amountLayoutLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,0);
            amountLayoutLayoutParams.addRule(RelativeLayout.ABOVE, R.id.default_wallet_layout);
            amountLayout.setLayoutParams(amountLayoutLayoutParams);

            return;
        }

        RelativeLayout.LayoutParams amountLayoutLayoutParams = (RelativeLayout.LayoutParams) amountLayout.getLayoutParams();
        amountLayoutLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,1);
        amountLayoutLayoutParams.addRule(RelativeLayout.ABOVE, 0);
        amountLayoutLayoutParams.setMargins(getPx(60), getPx(0),0, getPx(0));
        amountLayout.setLayoutParams(amountLayoutLayoutParams);
    }

    private int getPx(int dimensionDp) {
         return (int) viewUtil.convertDpToPx(this, dimensionDp);
    }

}
