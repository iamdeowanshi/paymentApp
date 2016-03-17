package com.batua.android.user.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class MakePaymentActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.default_wallet_layout) LinearLayout defaultWalletLayout;
    @Bind(R.id.toolbar_title) TextView toolbarTitle;
    @Bind(R.id.txt_merchant_title) TextView txtMerchantTitle;
    @Bind(R.id.rating_review) RatingBar ratingReview;
    @Bind(R.id.txt_reviewed_num) TextView txtReviewedNum;
    @Bind(R.id.merchant_title_layout) RelativeLayout merchantTitleLayout;
    @Bind(R.id.txt_merchant_address) TextView txtMerchantAddress;
    @Bind(R.id.txt_distance) TextView txtDistance;
    @Bind(R.id.call_merchant) ImageButton callMerchant;
    @Bind(R.id.edt_enter_amount) EditText edtEnterAmount;
    @Bind(R.id.txt_promo_code) TextView txtPromoCode;
    @Bind(R.id.amount_layout) RelativeLayout amountLayout;
    @Bind(R.id.default_wallet) TextView defaultWallet;
    @Bind(R.id.wallet_amount) TextView walletAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        ButterKnife.bind(this);

        setToolBar();
    }

    @OnClick(R.id.txt_promo_code)
    void startPromocode() {
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

    @OnClick(R.id.default_wallet)
    void onWalletClick() {
        startActivity(PaymentActivity.class, null);
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
