package com.batua.android.merchant.module.merchant.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantViewInteractor;
import com.batua.android.merchant.module.merchant.util.SpinnerLoad;
import com.batua.android.merchant.module.merchant.view.activity.AddMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.EditMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.MerchantDetailsActivity;
import com.batua.android.merchant.module.merchant.view.listener.PreviousClickedListener;

import org.parceler.Parcels;

import java.math.BigInteger;
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

    @Bind(R.id.edt_account_holder) EditText edtAccountHolder;
    @Bind(R.id.edt_account_number) EditText edtAccountNumber;
    @Bind(R.id.spinner_bank) Spinner spinnerBank;
    @Bind(R.id.edt_bank_branch) EditText edtBankBranch;
    @Bind(R.id.edt_ifsc_code) EditText edtIfscCode;

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
        merchantRequest.setBankBranch(text.toString());
    }

    @OnTextChanged(R.id.edt_ifsc_code)
    void onIfscChange(CharSequence text) {
        merchantRequest.setIfscCode(text.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                merchantRequest.setStatus("Drafted");
                togglePresenter();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMerchant(Merchant response) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Merchant", Parcels.wrap(response));
        startActivity(MerchantDetailsActivity.class, bundle);
        getActivity().finish();
    }

    @OnClick(R.id.txt_load_previous)
    public void onPreviousClicked() {
        previousClickedListener.previousClicked(BANK_INFO_POSITION);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        if (validateData()) {
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
            return false;
        }

        if (merchantRequest.getShortCode() == null || merchantRequest.getShortCode().isEmpty()) {
            return false;
        }

        if (merchantRequest.getEmail() == null || merchantRequest.getEmail().isEmpty()) {
            return false;
        }

        if ( merchantRequest.getPhone() == null || merchantRequest.getPhone().isEmpty()) {
            return false;
        }

        if (merchantRequest.getFee() == null || merchantRequest.getFee() == 0) {
            return false;
        }

        if (merchantRequest.getCityId() == null || merchantRequest.getCityId() == 0) {
            return false;
        }

        if (merchantRequest.getAddress() == null || merchantRequest.getAddress().isEmpty()) {
            return false;
        }

        if ( merchantRequest.getPincode() == null || merchantRequest.getPincode() == 0) {
            return false;
        }

        if ( merchantRequest.getAccountHolder() == null || merchantRequest.getAccountHolder().isEmpty()) {
            return false;
        }

        if ( merchantRequest.getAccountNumber() == null || merchantRequest.getAccountNumber().isEmpty()) {
            return false;
        }

        if ( merchantRequest.getBankBranch() == null || merchantRequest.getBankBranch().isEmpty()) {
            return false;
        }

        if ( merchantRequest.getIfscCode() == null || merchantRequest.getIfscCode().isEmpty()) {
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
        }

        if (merchant.getBankName() != null) {
            spinnerBank.setSelection(bankList.indexOf(merchant.getBankName()));
        }

        if (merchant.getAccountNumber() != null) {
            edtAccountNumber.setText(merchant.getAccountNumber().toString());
        }

        if (merchant.getBranchName() != null) {
            edtBankBranch.setText(merchant.getBranchName());
        }

        if (merchant.getIfscCode() != null) {
            edtIfscCode.setText(merchant.getIfscCode());
        }
    }

}
