package com.batua.android.merchant.module.merchant.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.City;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.merchant.presenter.CityPresenter;
import com.batua.android.merchant.module.merchant.presenter.CityViewInteractor;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantViewInteractor;
import com.batua.android.merchant.module.merchant.view.activity.AddMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.EditMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.MerchantDetailsActivity;
import com.batua.android.merchant.module.merchant.view.adapter.SpinAdapter;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
import com.batua.android.merchant.module.merchant.view.listener.PreviousClickedListener;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantLocationInfoFragment extends BaseFragment implements MerchantViewInteractor, CityViewInteractor{

    private static int LOCATION_INFO_POSITION = 1;

    @Inject MerchantPresenter merchantPresenter;
    @Inject CityPresenter cityPresenter;

    @Bind(R.id.edt_merchant_city) EditText edtCity;
    @Bind(R.id.edt_merchant_address) EditText edtAddress;
    @Bind(R.id.edt_merchant_pin_code) EditText edtPin;

    private Merchant merchant;
    private MerchantRequest merchantRequest;
    private NextClickedListener nextClickedListener;
    private PreviousClickedListener previousClickedListener;

    private double latitude;
    private double longitude;

    private List<City> cities;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.batua.android.merchant.R.layout.fragment_merchant_location_info, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Injector.component().inject(this);
        cityPresenter.attachViewInteractor(this);
        merchantPresenter.attachViewInteractor(this);
        cityPresenter.getCities();

        merchant = Parcels.unwrap(this.getArguments().getParcelable("Merchant"));
        merchantRequest = (merchant == null) ? ((AddMerchantActivity) getActivity()).getMerchantRequest() : ((EditMerchantActivity) getActivity()).getMerchantRequest();
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                setData();
                togglePresenter();
                break;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @OnClick(R.id.txt_load_next)
    public void onNextClicked(){
        nextClickedListener.nextClicked(LOCATION_INFO_POSITION);
    }

    @OnClick(R.id.txt_load_previous)
    public void onPreviousClicked(){
        previousClickedListener.previousClicked(LOCATION_INFO_POSITION);
    }

    @OnTextChanged(R.id.edt_merchant_city)
    void onCityChange(CharSequence text) {
        int position = 0;

        if (cities.size() > 0) {
            position = cities.indexOf(text.toString());
        }

        /*if ( position >= 0) {
            merchantRequest.setCityId(cities.get(position).getId());
        }*/
    }

    @OnTextChanged(R.id.edt_merchant_address)
    void onAddressChange(CharSequence text) {
        merchantRequest.setAddress(text.toString());
        merchantRequest.setLongitude(longitude);
        merchantRequest.setLatitude(latitude);
    }

    @OnTextChanged(R.id.edt_merchant_pin_code)
    void onPinChange(int text) {
        merchantRequest.setPincode(text);
    }

    @Override
    public void showMerchant(Merchant response) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Merchant", Parcels.wrap(response));
        startActivity(MerchantDetailsActivity.class, bundle);
        getActivity().finish();
    }

    @Override
    public void showCities(List<City> cities) {
        this.cities = cities;

        /*SpinAdapter arrayAdapter = new SpinAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, this.cities);
        edtCity.setAdapter(arrayAdapter);*/

    }

    public void setNextClickedListener(NextClickedListener nextClickedListener){
        this.nextClickedListener = nextClickedListener;
    }

    public void setPreviousClickedListener(PreviousClickedListener previousClickedListener){
        this.previousClickedListener = previousClickedListener;
    }

/*    private void togglePresenter() {
        if (merchant != null) {
            merchantPresenter.updateMerchant(merchantRequest);
            return;
        }

        merchantPresenter.addMerchant(merchantRequest);
    }*/

/*    private void setData() {
        merchantRequest = (merchant == null) ? ((AddMerchantActivity) getActivity()).getMerchantRequest() : ((EditMerchantActivity) getActivity()).getMerchantRequest();

        merchantRequest.setCityId(cities.get(cities.indexOf(edtCity.getText())).getId());
        merchantRequest.setAddress(edtAddress.getText().toString());
        merchantRequest.setPincode(Integer.valueOf(edtPin.getText().toString()));
        merchantRequest.setLatitude(latitude);
        merchantRequest.setLongitude(longitude);
        merchantRequest.setStatus("Drafted");
    }*/

}
