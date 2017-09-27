package com.tecsol.batua.user.module.payment.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
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
import com.razorpay.Checkout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tecsol.batua.user.data.model.Merchant.Merchant;
import com.tecsol.batua.user.data.model.Merchant.Discount;
import com.tecsol.batua.user.data.model.User.Transaction;
import com.tecsol.batua.user.data.model.User.TransactionResponse;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.callback.PermissionCallback;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.DecimalFormatUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.payment.presenter.DiscountPresenter;
import com.tecsol.batua.user.module.payment.presenter.DiscountViewInteractor;
import com.tecsol.batua.user.module.payment.presenter.TransactionPresenter;
import com.tecsol.batua.user.module.payment.presenter.TransactionViewInteractor;

import org.json.JSONObject;
import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class PrePaymentConfirmationActivity extends BaseActivity implements DiscountViewInteractor, TransactionViewInteractor {

    final String[] CALL_PERMISSION = {Manifest.permission.CALL_PHONE};

    @Inject ViewUtil viewUtil;
    @Inject Bakery bakery;
    @Inject DiscountPresenter discountPresenter;
    @Inject PreferenceUtil preferenceUtil;
    @Inject TransactionPresenter transactionPresenter;

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
    private boolean validAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payment_confirmation);
        Injector.component().inject(this);
        discountPresenter.attachViewInteractor(this);
        transactionPresenter.attachViewInteractor(this);

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
                Discount discount = new Discount();
                discount.setPromocode(edtPromocode.getText().toString());
                discount.setMerchantId(merchant.getId());
                discountPresenter.validatePromocode(discount);
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
        Discount discount = (Discount) preferenceUtil.read(preferenceUtil.PROMOCODE, Discount.class);
        if (discount != null) {
            // got to razor pay activity
            startPayment();
            viewUtil.hideKeyboard(this);
            return;
        }
        // check for any offers
        if (getAmountToBePaid() != 0.0) {
            discountPresenter.OfferExist(merchant.getId());
            viewUtil.hideKeyboard(this);
        }
    }

    @OnClick(R.id.call_merchant)
    void callMerchant() {
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
            amountLayoutLayoutParams.setMargins(getPx(60), getPx(5), 0, getPx(15));
            amountLayoutLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 0);
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
        if (merchant != null) {
            txtMerchantTitle.setText(merchant.getName());
            txtMerchantAddress.setText(merchant.getAddress());
            txtDistance.setText(DecimalFormatUtil.formatToExactTwoDecimal(merchant.getDistance()) + " km");
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
    public void onValidPromocode(Discount discount) {
        discount.setIsPromocodeApplied(true);
        preferenceUtil.save(preferenceUtil.PROMOCODE, discount);
        showAppliedPromocodeDialog();
    }

    @Override
    public void onOfferExist(Discount discount) {
        discount.setIsOfferApplied(true);
        bakery.toastShort("Offer exist");
        preferenceUtil.save(preferenceUtil.OFFER, discount);
        startPayment();
        viewUtil.hideKeyboard(this);
    }

    @Override
    public void onOfferDoesntExist() {
        bakery.toastShort("Offer doesn't exist");
        preferenceUtil.remove(preferenceUtil.OFFER);
        startPayment();
        viewUtil.hideKeyboard(this);
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

        if (e.getMessage() == null || e.getMessage().isEmpty()) {
            return;
        }

        if (e.getMessage().startsWith("failed to connect")) {
            bakery.snackShort(getContentView(), "Server error");
            return;
        }

        bakery.snackShort(getContentView(), e.getMessage());
    }

    private void showAppliedPromocodeDialog() {

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


    private void startPayment() {

        // check if amount is valid
        if (getAmountToBePaid() != 0.0) {
            /**
             * Replace with your public key
             */
            //final String public_key = "rzp_live_ILgsfZCZoFIKMb";
            //rzp_test_tJrFA1TwhaOaOh
            final String public_key = "rzp_test_tJrFA1TwhaOaOh";

            /**
             * You need to pass current activity in order to let razorpay create CheckoutActivity
             */
            final Activity activity = this;

            final Checkout co = new Checkout();
            co.setPublicKey(public_key);

            try {
                JSONObject options = new JSONObject("{" +
                        "description: 'Changing Payments'," +
                        "image: 'https://s3-ap-southeast-1.amazonaws.com/batua-s3/img_batua_logo_small.png'," +
                        "currency: 'INR'}"
                );

                options.put("amount", getAmountToBePaid()*100);
                options.put("name", "Batua");
                options.put("prefill", new JSONObject("{email: " + merchant.getEmail() + ", contact: " + merchant.getPhone() +"}"));

                co.open(activity, options);

            } catch (Exception e) {
                bakery.snackShort(getContentView(), e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
        Transaction transaction = new Transaction();
        transaction.setAmount(edtAmount.getText().toString());
        transaction.setMerchantId(merchant.getId().toString());
        transaction.setPaymentId(razorpayPaymentID);
        transaction.setStatus("success");
        transaction.setPaymentmodeId(1);
        transaction.setUserId(((User)preferenceUtil.read(preferenceUtil.USER, User.class)).getId()+"");

        Discount promocode = (Discount)preferenceUtil.read(preferenceUtil.PROMOCODE, Discount.class);
        Discount offer = (Discount)preferenceUtil.read(preferenceUtil.OFFER, Discount.class);
        if (promocode!=null && promocode.isPromocodeApplied()) {
            transaction.setPromocode(promocode);
            transaction.setOffer(null);
            transactionPresenter.makeTransaction(transaction);
            return;
        }

        if (offer!=null && offer.isOfferApplied()) {
            transaction.setOffer(offer);
            transaction.setPromocode(null);
            transactionPresenter.makeTransaction(transaction);
            return;
        }

        transaction.setOffer(null);
        transaction.setPromocode(null);
        transactionPresenter.makeTransaction(transaction);

        Log.e("transaction ID---", razorpayPaymentID);
        } catch (Exception e) {
            Log.e("com.merchant.success", e.getMessage(), e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    public void onPaymentError(int code, String response) {
        try {
            Log.e("failure", "Payment failed: " + Integer.toString(code) + " " + response);
        } catch (Exception e) {
            Log.e("com.merchant.failure", e.getMessage(), e);
        }
    }

    private Double getAmountToBePaid() {
        String amountString = edtAmount.getText().toString();
        Double amount = 0.0;
        if (amountString.isEmpty()) {
            viewUtil.hideKeyboard(this);
            bakery.snackShort(getContentView(), "Amount cannot be empty.");
            return 0.0;
        }

        try {
            amount = Double.parseDouble(amountString);
            if (amount < 1) {
                viewUtil.hideKeyboard(this);
                bakery.snackShort(getContentView(), "Transaction is minimum Rs.1");
                return 0.0;
            }
        } catch (NumberFormatException e) {
            viewUtil.hideKeyboard(this);
            bakery.snackShort(getContentView(), "Please enter a valid amount.");
            return 0.0;
        }

        return amount;
    }

    @Override
    public void onTransactionSuccess(TransactionResponse transactionResponse) {
        bakery.snackShort(getContentView(), "Success");
        preferenceUtil.remove(preferenceUtil.PROMOCODE);
        preferenceUtil.remove(preferenceUtil.OFFER);
        Bundle bundle =  new Bundle();
        bundle.putParcelable("TransactionResponse", Parcels.wrap(transactionResponse));
        startActivity(PaymentSuccessActivity.class, bundle);
    }

}
