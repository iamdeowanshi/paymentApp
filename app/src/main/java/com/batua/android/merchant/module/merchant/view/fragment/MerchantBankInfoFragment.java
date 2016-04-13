package com.batua.android.merchant.module.merchant.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.ViewUtil;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantViewInteractor;
import com.batua.android.merchant.module.merchant.util.SpinnerLoad;
import com.batua.android.merchant.module.merchant.view.activity.AddMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.EditMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.MerchantDetailsActivity;
import com.batua.android.merchant.module.merchant.view.listener.PreviousClickedListener;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantBankInfoFragment extends BaseFragment implements MerchantViewInteractor {

    private static int BANK_INFO_POSITION = 2;

    @Inject MerchantPresenter merchantPresenter;
    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;

    @Bind(R.id.edt_account_holder) EditText edtAccountHolder;
    @Bind(R.id.edt_account_number) EditText edtAccountNumber;
    @Bind(R.id.spinner_bank) Spinner spinnerBank;
    @Bind(R.id.edt_bank_branch) EditText edtBankBranch;
    @Bind(R.id.edt_ifsc_code) EditText edtIfscCode;
    @Bind(R.id.progress) ProgressBar progressBar;

    private PreviousClickedListener previousClickedListener;
    private Merchant merchant;
    private MerchantRequest merchantRequest;
    private List<String> bankList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_merchant_bank_info, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Injector.component().inject(this);
        merchantPresenter.attachViewInteractor(this);

        merchant = Parcels.unwrap(this.getArguments().getParcelable("Merchant"));
        merchantRequest = (merchant == null) ? ((AddMerchantActivity) getActivity()).getMerchantRequest() : ((EditMerchantActivity) getActivity()).getMerchantRequest();

        bankList = Arrays.asList(getResources().getStringArray(R.array.merchant_bank));

        if (merchant != null) {
            loadData();
        }

        SpinnerLoad.loadSpinner(getContext(), R.array.merchant_bank, spinnerBank);
        setSpinnerListener();
    }

    @OnTextChanged(R.id.edt_account_holder)
    void onAccountChange(CharSequence text) {
        merchantRequest.setAccountHolder(text.toString());
    }

    @OnTextChanged(R.id.edt_account_number)
    void onAccountNumberChange() {
        merchantRequest.setAccountNumber(edtAccountNumber.getText().toString());
    }

    @OnTextChanged(R.id.edt_bank_branch)
    void onBankBranchChange(CharSequence text) {
        merchantRequest.setBranchName(text.toString());
    }

    @OnTextChanged(R.id.edt_ifsc_code)
    void onIfscChange(CharSequence text) {
        merchantRequest.setIfscCode(text.toString());
    }

    @Override
    public void showMerchant(Merchant response) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("MerchantDetail", Parcels.wrap(response));
        startActivity(MerchantDetailsActivity.class, bundle);
        getActivity().finish();
    }

    @Override
    public void showError() {
        bakery.snackShort(getContentView(), "Invalid data or some fields are missing");
    }

    @OnClick(R.id.txt_load_previous)
    public void onPreviousClicked() {
        previousClickedListener.previousClicked(BANK_INFO_POSITION);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        viewUtil.hideKeyboard(getActivity());

        if (validateData()) {
            merchantRequest.setCreatedSalesId(3);
            merchantRequest.setStatus("Pending for approval");
            togglePresenter();

            return;
        }

        bakery.snackShort(getContentView(), "Some data are missing to submit for approval");
    }

    public void setPreviousClickedListener(PreviousClickedListener previousClickedListener) {
        this.previousClickedListener = previousClickedListener;
    }

    private void togglePresenter() {
        if (merchant != null) {
            merchantPresenter.updateMerchant(merchantRequest);
            return;
        }

        merchantPresenter.addMerchant(merchantRequest);
    }

    private boolean validateData() {
        if (merchantRequest.getName() == null || merchantRequest.getName().isEmpty()) {
            bakery.snackShort(getContentView(), "Please enter name");
            return false;
        }

        if ( merchantRequest.getShortCode() == null || merchantRequest.getShortCode().isEmpty()) {
            bakery.snackShort(getContentView(), "Please enter Shortcode (must be 8 characters)");
            return false;
        }

        if (merchantRequest.getPhone() == null || merchantRequest.getPhone().isEmpty() || (merchantRequest.getPhone().length() != 10) ){
            bakery.snackShort(getContentView(), "Please enter Mobile Number");
            return false;
        }

        if ( merchantRequest.getFee() == 0.0) {
            bakery.snackShort(getContentView(), "Please enter Fee");
            return false;
        }

        if (merchantRequest.getProfileImageUrl() == null || merchantRequest.getProfileImageUrl().isEmpty()) {
            bakery.snackShort(getContentView(), "Upload your profile picture");
            return false;
        }

        if (merchantRequest.getEmail() == null || merchantRequest.getEmail().isEmpty()) {
            bakery.snackShort(getContentView(), "Please enter Email Address");
            return false;
        }

        if (merchantRequest.getCityId() == null || merchantRequest.getCityId() == 0) {
            bakery.snackShort(getContentView(), "Please enter your current city");
            return false;
        }

        if (merchantRequest.getAddress() == null || merchantRequest.getAddress().isEmpty()) {
            bakery.snackShort(getContentView(), "Please enter current address");
            return false;
        }

        if (merchantRequest.getPincode() == null || merchantRequest.getPincode() == 0) {
            bakery.snackShort(getContentView(), "Please enter pincode");
            return false;
        }

        if (merchantRequest.getAccountHolder() == null || merchantRequest.getAccountHolder().isEmpty()) {
            bakery.snackShort(getContentView(), "Please enter account holder name");
            return false;
        }

        if (merchantRequest.getAccountNumber() == null || merchantRequest.getAccountNumber().isEmpty()) {
            bakery.snackShort(getContentView(), "Please enter account number");
            return false;
        }

        if (merchantRequest.getBranchName() == null || merchantRequest.getBranchName().isEmpty()) {
            bakery.snackShort(getContentView(), "Please enter branch name");
            return false;
        }

        if (merchantRequest.getIfscCode() == null || merchantRequest.getIfscCode().isEmpty()) {
            bakery.snackShort(getContentView(), "Please enter ifsc code");
            return false;
        }

        return true;
    }

    private void setSpinnerListener() {
        spinnerBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                merchantRequest.setBankName(bankList.get(spinnerBank.getSelectedItemPosition()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                merchantRequest.setBankName(bankList.get(spinnerBank.getSelectedItemPosition()));
            }
        });
    }

    private void loadData() {
        if (merchant.getAccountHolder() != null) {
            edtAccountHolder.setText(merchant.getAccountHolder());
            merchantRequest.setAccountHolder(merchant.getAccountHolder());
        }

        if (merchant.getBankName() != null) {
            spinnerBank.setSelection(bankList.indexOf(merchant.getBankName()));
            merchantRequest.setBankName(merchant.getBankName());
        }

        if (merchant.getAccountNumber() != null) {
            edtAccountNumber.setText(merchant.getAccountNumber().toString());
            merchantRequest.setAccountNumber(String.valueOf(merchant.getAccountNumber()));
        }

        if (merchant.getBranchName() != null) {
            edtBankBranch.setText(merchant.getBranchName());
            merchantRequest.setBranchName(merchant.getBranchName());
        }

        if (merchant.getIfscCode() != null) {
            edtIfscCode.setText(merchant.getIfscCode());
            merchantRequest.setIfscCode(merchant.getIfscCode());
        }
    }

    @Override
    public void onNetworkCallProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        progressBar.setVisibility(View.GONE);
    }

}
