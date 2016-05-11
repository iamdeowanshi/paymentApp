package com.tecsol.batua.user.module.payment.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.batua.android.user.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tecsol.batua.user.data.model.Merchant.Merchant;
import com.tecsol.batua.user.data.model.Merchant.PromoCode;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.callback.PermissionCallback;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.DecimalFormatUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.payment.presenter.DiscountPresenter;
import com.tecsol.batua.user.module.payment.presenter.DiscountViewInteractor;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class PrePaymentConfirmationActivity extends BaseActivity implements DiscountViewInteractor{

    final String[] CALL_PERMISSION = {Manifest.permission.CALL_PHONE};

    @Inject ViewUtil viewUtil;
    @Inject Bakery bakery;
    @Inject DiscountPresenter discountPresenter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edt_enter_amount) EditText edtAmount;
    @Bind(R.id.scrollView) ScrollView scrollView;
    @Bind(R.id.amount_layout) RelativeLayout amountLayout;
    @Bind(R.id.txt_merchant_title) TextView txtMerchantTitle;
    @Bind(R.id.txt_merchant_address) TextView txtMerchantAddress;
    @Bind(R.id.txt_distance) TextView txtDistance;
    @Bind(R.id.rating_review) RatingBar ratingBar;
    @Bind(R.id.txt_reviewed_num) TextView txtReviewedNumber;
    @Bind(R.id.img_background) ImageView imgBackground;
    @Bind(R.id.discount_progressBar) ProgressBar discountProgress;

    private Merchant merchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payment_confirmation);
        Injector.component().inject(this);
        discountPresenter.attachViewInteractor(this);

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
            }
        });

        merchant = Parcels.unwrap(getIntent().getExtras().getParcelable("Merchant"));
        showMerchantDetails(merchant);
    }

    @OnClick(R.id.txt_promo_code)
    void showPromocodeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_promocode);

        dialog.show();

        TextView positiveButton = (TextView) dialog.findViewById(R.id.button_ok);
        TextView negativeButton = (TextView) dialog.findViewById(R.id.button_cancel);
        final EditText edtPromocode = (EditText) dialog.findViewById(R.id.edt_promocode);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPromocode.getText().toString().isEmpty()) {
                    viewUtil.hideKeyboard(PrePaymentConfirmationActivity.this);
                    bakery.snackShort(getContentView(), "Promocode cannot be empty");
                    return;
                }
                PromoCode promoCode = new PromoCode();
                promoCode.setPromocode(edtPromocode.getText().toString());
                promoCode.setMerchantId(merchant.getId());
                discountPresenter.validatePromocode(promoCode);
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

    @OnClick(R.id.call_merchant)
    void callMerchant(){
        makeCall();
    }

    private void makeCall() {
        requestPermission(CALL_PERMISSION, new PermissionCallback() {
            @Override
            public void onPermissionGranted(String[] grantedPermissions) {
                startCallActivity();
            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                bakery.snackShort(getContentView(), "Call permission is required to continue");
            }

            @Override
            public void onPermissionBlocked(String[] blockedPermissions) {
                bakery.snackShort(getContentView(), "Call permission is required to continue");
            }
        });
    }

    private void startCallActivity() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + merchant.getPhone()));
            startActivity(intent);
        } catch (SecurityException e) {
            Log.d("--", "Security exception Phone");
        }
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
        amountLayoutLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 1);
        amountLayoutLayoutParams.addRule(RelativeLayout.ABOVE, 0);
        amountLayoutLayoutParams.setMargins(getPx(60), getPx(0), 0, getPx(0));
        amountLayout.setLayoutParams(amountLayoutLayoutParams);
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

    private void showMerchantDetails(Merchant merchant) {
        if (merchant!=null) {
            txtMerchantTitle.setText(merchant.getName());
            txtMerchantAddress.setText(merchant.getAddress());
            txtDistance.setText(DecimalFormatUtil.formatToExactTwoDecimal(merchant.getDistance())+" km");
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            if (merchant.getAverageRating() != 5.0f) {
                stars.getDrawable(2).setColorFilter(Color.rgb(249, 173, 35), PorterDuff.Mode.SRC_ATOP);
            } else {
                stars.getDrawable(2).setColorFilter(Color.rgb(138, 211, 33), PorterDuff.Mode.SRC_ATOP);
            }

            Picasso.with(this).load(merchant.getProfileImageUrl()).placeholder(R.drawable.grey_img_background).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imgBackground.setBackground(new BitmapDrawable(getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d("TAG", "FAILED");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    Log.d("TAG", "onPrepareLoad");
                }
            });
        }
    }

    private int getPx(int dimensionDp) {
         return (int) viewUtil.convertDpToPx(this, dimensionDp);
    }

    @Override
    public void onValidPromocode(PromoCode promoCode) {
        showAppliedPromocodeDialog();
    }

    @Override
    public void onNetworkCallProgress() {
        discountProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        discountProgress.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        viewUtil.hideKeyboard(this);
        discountProgress.setVisibility(View.GONE);

        if (e.getMessage()== null || e.getMessage().isEmpty()){
            return;
        }

        if (e.getMessage().startsWith("failed to connect")) {
            bakery.snackShort(getContentView(), "Server error");
            return;
        }

        bakery.snackShort(getContentView(), e.getMessage());
    }

    private void showAppliedPromocodeDialog(){

        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);

        alertbuilder.setTitle("Valid Promocode")
                .setMessage("Your Promocode has been applied. Please continue to make payment")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertbuilder.create();
        alert.show();
    }

}
