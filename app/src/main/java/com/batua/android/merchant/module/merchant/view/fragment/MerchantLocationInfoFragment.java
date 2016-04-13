package com.batua.android.merchant.module.merchant.view.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

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
import com.batua.android.merchant.module.merchant.view.adapter.SearchCityAdapter;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.PermissionUtil;
import com.batua.android.merchant.module.common.util.ViewUtil;
import com.batua.android.merchant.module.merchant.view.adapter.SearchAddressAdapter;
import com.batua.android.merchant.module.merchant.view.listener.AutoCompleteRecyclerItemClickListener;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
import com.batua.android.merchant.module.merchant.view.listener.CitySelectedListener;
import com.batua.android.merchant.module.merchant.view.listener.PreviousClickedListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import timber.log.Timber;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantLocationInfoFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, CityViewInteractor, CitySelectedListener {

    private static final int LOCATION_INFO_POSITION = 1;
    private static final int LOCATION_REQUEST_CODE = 2;
    final String[] LOCATION_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final LatLngBounds BOUNDS = new LatLngBounds(new LatLng(-85, 180), new LatLng(85, -180));
    private static final String NO_SERVICE = "No Service!";

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;
    @Inject CityPresenter cityPresenter;

    @Bind(R.id.edt_merchant_city) EditText edtCity;
    @Bind(R.id.edt_merchant_pin_code) EditText edtPin;
    @Bind(R.id.recyclerView_search_address) RecyclerView searchAddressRecycler;
    @Bind(R.id.recyclerView_search_city) RecyclerView searchCityRecycler;
    @Bind(R.id.edt_merchant_address) EditText edtAddress;
    @Bind(R.id.merchant_location_map) LinearLayout locationLayout;
    @Bind(R.id.navigation_layout) LinearLayout navigationLayout;
    @Bind(R.id.input_layout_merchant_pin_code) TextInputLayout inputLayoutPin;
    @Bind(R.id.input_layout_merchant_city) TextInputLayout inputLayoutMerchantCity;

    private Merchant merchant;
    private MerchantRequest merchantRequest;

    private GoogleApiClient googleApiClient;
    private NextClickedListener nextClickedListener;
    private PreviousClickedListener previousClickedListener;
    private SearchAddressAdapter searchAddressAdapter;
    private SearchCityAdapter searchCityAdapter;
    private GoogleMap googleMap;
    private Marker marker;
    private String myLocationAddress;

    private List<City> cities;
    private Geocoder geocoder;
    private LatLng addressText;

    // Fragment override methods
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
        cityPresenter.getCities();

        merchant = Parcels.unwrap(this.getArguments().getParcelable("Merchant"));
        merchantRequest = (merchant == null) ? ((AddMerchantActivity) getActivity()).getMerchantRequest() : ((EditMerchantActivity) getActivity()).getMerchantRequest();

        if (merchant != null) {
            loadData();
        }

        checkLocationPermission();
        buildGoogleApiClient();
        inflateSearchAddressAdapter();
        initialiseMapUiSettings();

        searchAddressRecycler.addOnItemTouchListener(new AutoCompleteRecyclerItemClickListener(this.getContext(), new AutoCompleteRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        showPinAndMap();

                        final SearchAddressAdapter.PlaceAutocomplete item = searchAddressAdapter.getItem(position);
                        final String placeDescription = String.valueOf(item.description);
                        final String placeId = String.valueOf(item.placeId);
                        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(googleApiClient, placeId);
                        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(PlaceBuffer places) {
                                if (places.getCount() == 1) {
                                    Place selectedPalce = places.get(0);
                                    if (marker!=null) {
                                        showDialog("Alert!", "Are you sure to change the Address?", selectedPalce.getLatLng());
                                        return;
                                    }
                                    displayAddress(placeDescription, selectedPalce.getLatLng());
                                    bakery.snack(getContentView(), selectedPalce.getLatLng().longitude + "  " + selectedPalce.getLatLng().latitude + ":" + placeDescription, Snackbar.LENGTH_LONG);
                                } else {
                                    bakery.snack(getContentView(), NO_SERVICE, Snackbar.LENGTH_SHORT);
                                }
                            }
                        });
                    }
                })
        );

        locationLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        v.requestFocus();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        v.requestFocus();
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    // GoogleApiClient.ConnectionCallbacks and GoogleApiClient.OnConnectionFailedListener override methods

    @Override
    public void onConnected(Bundle bundle) {
        if (merchant != null && merchant.getAddress() != null) {
            updateLocation(new LatLng(merchant.getLatitude(), merchant.getLongitude()));
            animateCamera(new LatLng(merchant.getLatitude(), merchant.getLongitude()), "address");
            return;
        }

        showCurrentPosition();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this.getActivity(), LOCATION_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this.getActivity(), LOCATION_PERMISSION[1]) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions();
        } else {
            buildGoogleApiClient();
            inflateSearchAddressAdapter();
        }
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), LOCATION_PERMISSION[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), LOCATION_PERMISSION[1])) {
            Timber.d("request");
            // Display a SnackBar with an explanation and a button to trigger the request.
            bakery.snack(getContentView(), "Location permission is required to continue!", Snackbar.LENGTH_INDEFINITE, "Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(getActivity(), LOCATION_PERMISSION, LOCATION_REQUEST_CODE);
                }
            });

        } else {
            // Permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this.getActivity(), LOCATION_PERMISSION, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    buildGoogleApiClient();
                    inflateSearchAddressAdapter();
                } else {
                    // Permission Denied
                    showLocationPermissionsSnackbar();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

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
        if (cities!=null && !text.toString().isEmpty()) {
            hideAllExceptCity();
            searchCityAdapter.getFilter().filter(text.toString());

            return;
        }
        searchCityAdapter.getFilter().filter(text.toString());
        showAll();
    }

    @OnTextChanged(R.id.edt_merchant_address)
    void onAddressChange(CharSequence text) {
        if (!text.toString().equals("") && googleApiClient.isConnected()) {
            edtAddress.setError(null);
            hidePinAndMap();
            if (!text.toString().equalsIgnoreCase(myLocationAddress)) {
                searchAddressAdapter.getFilter().filter(text.toString());
                return;
            }
            showPinAndMap();

        } else if (!googleApiClient.isConnected()) {
            bakery.snack(getContentView(), NO_SERVICE, Snackbar.LENGTH_LONG);
        } else if (edtAddress.isFocused() && text.toString().equals("")) {
            edtAddress.setError("Address cannot be empty");
        } else {
            merchantRequest.setAddress(text.toString());
        }
    }

    @OnTextChanged(R.id.edt_merchant_pin_code)
    void onPinChange(CharSequence text) {
        if (text.length() != 6) {
            merchantRequest.setFee(0.0);
            inputLayoutPin.setErrorEnabled(true);
            inputLayoutPin.setError("Invalid Pin");
            return;
        }


        if (!edtAddress.getText().toString().isEmpty() && merchantRequest.getLatitude()!=0.0 && merchantRequest.getLongitude()!=0.0){
            Integer pin = getPinCode(merchantRequest.getLatitude(), merchantRequest.getLongitude());
            if (Integer.parseInt(text.toString())==pin){
                merchantRequest.setPincode(Integer.parseInt(text.toString()));
                inputLayoutPin.setErrorEnabled(false);

                return;
            }
            inputLayoutPin.setErrorEnabled(true);
            inputLayoutPin.setError("Pin mismatch with location");
            return;
        }

        merchantRequest.setPincode(Integer.parseInt(text.toString()));
        inputLayoutPin.setErrorEnabled(false);
    }


    @Override
    public void showCities(List<City> cities) {
        this.cities = cities;
        inflateSearchCityAdapter();
    }

    @Override
    public void displayError() {
        edtCity.setError("We don't have service in this city");
    }

    @Override
    public void displayCity(int cityId, String cityName) {
        if(merchantRequest.getLatitude()!=0.0 && merchantRequest.getLongitude()!=0.0){
            String city = getCity(merchantRequest.getLatitude(), merchantRequest.getLongitude());
            if (city!=null || (!city.equals("") && !cityName.equalsIgnoreCase(city))) {
                bakery.snackLong(getContentView(), "Please select a city that has your address");

                for (City city1: cities) {
                    if (city1.getName().equalsIgnoreCase(city)){
                        edtCity.setText(city1.getName());
                        merchantRequest.setCityId(city1.getId());
                        return;
                    }
                }

                edtCity.setText(city);
                showAll();
                return;
            }
            return;
        }

        edtCity.setError(null);
        edtCity.setText(cityName);
        merchantRequest.setCityId(cityId);
        showAll();
    }

    private void showLocationPermissionsSnackbar() {
        Snackbar.make(getContentView(), "Location permission is required to continue!", Snackbar.LENGTH_LONG)
                .setAction("ALLOW", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkLocationPermission();
                    }
                });
    }

    private void inflateSearchAddressAdapter() {
        searchAddressAdapter = new SearchAddressAdapter(this.getContext(), R.layout.search_address, googleApiClient, BOUNDS, null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        searchAddressRecycler.setLayoutManager(linearLayoutManager);
        searchAddressRecycler.setAdapter(searchAddressAdapter);
    }

    private void inflateSearchCityAdapter() {
        searchCityAdapter = new SearchCityAdapter(cities, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        searchCityRecycler.setLayoutManager(linearLayoutManager);
        searchCityRecycler.setAdapter(searchCityAdapter);
    }

    private void initialiseMapUiSettings() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        // Getting GoogleMap object from the fragment
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Enabling MyLocation in Google Map
                try {
                    MerchantLocationInfoFragment.this.googleMap = googleMap;
                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                    googleMap.getUiSettings().setRotateGesturesEnabled(false);
                    googleMap.getUiSettings().setZoomGesturesEnabled(true);
                    googleMap.getUiSettings().setCompassEnabled(false);
                    googleMap.getUiSettings().setMapToolbarEnabled(false);
                } catch (SecurityException e) {
                    Log.d("Location Security", e.toString());
                }

                googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {

                        if (marker != null) {
                            try {
                                if (!isLocationEnabled()) {
                                    showSettingsAlert();
                                    return true;
                                }
                                Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                                if (location != null) {
                                    LatLng curentpoint = new LatLng(location.getLatitude(), location.getLongitude());
                                    showDialog("Alert!", "Are you sure to change the location?", curentpoint);
                                    return true;
                                }
                            } catch (SecurityException e) {
                                bakery.snack(getContentView(), "Please enable Location", Snackbar.LENGTH_SHORT);
                            }

                            return true;
                        }

                        showCurrentPosition();
                        return false;
                    }
                });

                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        if (marker != null) {
                            showDialog("Alert!", "Are you sure to change the marker?", latLng);
                            return;
                        }

                        addMarker(latLng);
                    }
                });
            }
        });
    }

    private void showDialog(String title, final String message, final LatLng latLng) {
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(getContext());
        alertbuilder.setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if (message.equals("Are you sure to change the Address?")) {
                                String editCity = edtCity.getText().toString();
                                if (!editCity.isEmpty() && latLng.latitude != 0.0 && latLng.longitude != 0.0) {
                                    String city = getCity(latLng.latitude, latLng.longitude);
                                    if (city!=null || (!city.equals("") && !city.equalsIgnoreCase(editCity))) {
                                        bakery.snackLong(getContentView(), "Please select an address in your city");
                                        showAll();
                                        return;
                                    }
                                    return;

                                }
                                googleMap.clear();
                                updateLocation(latLng);
                                setAddressText(latLng);
                                animateCamera(latLng, "address");
                                dialog.dismiss();
                                return;
                            }

                            if (message.equals("Are you sure to change the marker?")) {
                                googleMap.clear();
                                addMarker(latLng);
                                dialog.dismiss();
                                return;
                            }
                            googleMap.clear();
                            showCurrentPosition();
                            dialog.dismiss();
                        }
                    });
        AlertDialog alert = alertbuilder.create();
        alert.show();
    }

    private void hideAllExceptCity(){
        edtCity.setError(null);
        searchCityRecycler.setVisibility(View.VISIBLE);
        edtAddress.setVisibility(View.GONE);
        searchAddressRecycler.setVisibility(View.GONE);
        edtPin.setVisibility(View.GONE);
        locationLayout.setVisibility(View.GONE);
        navigationLayout.setVisibility(View.GONE);
    }

    private void showAll(){
        edtCity.setError(null);
        searchCityRecycler.setVisibility(View.GONE);
        edtAddress.setVisibility(View.VISIBLE);
        searchAddressRecycler.setVisibility(View.GONE);
        edtPin.setVisibility(View.VISIBLE);
        locationLayout.setVisibility(View.VISIBLE);
        navigationLayout.setVisibility(View.VISIBLE);
        viewUtil.hideKeyboard(getActivity());
    }

    private void hidePinAndMap() {
        searchAddressRecycler.setVisibility(View.VISIBLE);
        edtPin.setVisibility(View.GONE);
        locationLayout.setVisibility(View.GONE);
        navigationLayout.setVisibility(View.GONE);
    }

    private void showPinAndMap() {
        searchAddressRecycler.setVisibility(View.GONE);
        edtPin.setVisibility(View.VISIBLE);
        locationLayout.setVisibility(View.VISIBLE);
        navigationLayout.setVisibility(View.VISIBLE);
        viewUtil.hideKeyboard(MerchantLocationInfoFragment.this.getActivity());
    }

    private void showCurrentPosition() {
        try {
            if ( ! isLocationEnabled()) {
                showSettingsAlert();
                return;
            }

            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                LatLng curentpoint = new LatLng(location.getLatitude(), location.getLongitude());
                updateLocation(curentpoint);
                setAddressText(curentpoint);
                animateCamera(curentpoint, "myLocation");
            }

        } catch (SecurityException e) {
            bakery.snack(getContentView(), "Please enable Location", Snackbar.LENGTH_SHORT);
        }
    }

    public boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(this.getContext().getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(this.getContext().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getContext());
        alertDialog.setTitle("GPS settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                MerchantLocationInfoFragment.this.getContext().startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private String getCurrentAddress(Double latitude, Double longitude) {
        List<android.location.Address> addresses;
        try {
            geocoder = new Geocoder(getContext(), Locale.ENGLISH);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (Geocoder.isPresent()) {
                if( addresses != null && addresses.size() > 0) {
                    return addresses.get(0).getAddressLine(0) + "," + addresses.get(0).getAddressLine(1);
                }
            }
        }catch (IOException e) {
            Log.e("tag--Address", e.getMessage());
            return "";
        }
        return "";

    }

    private String getCity(Double latitude, Double longitude) {
        List<android.location.Address> addresses;
        try {
            geocoder = new Geocoder(getContext(), Locale.ENGLISH);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (Geocoder.isPresent()) {
                if( addresses != null && addresses.size() > 0) {
                    return addresses.get(0).getLocality();
                }
            }
        }catch (IOException e) {
            Log.e("tag--City", e.getMessage());
            return "";
        }
        return "";

    }

    private Integer getPinCode(Double latitude, Double longitude) {
        List<android.location.Address> addresses;
        try {
            geocoder = new Geocoder(getContext(), Locale.ENGLISH);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (Geocoder.isPresent()) {
                if( addresses != null && addresses.size() > 0) {
                    if (addresses.get(0).getPostalCode()!=null) {
                        Log.e("tag--PIN", addresses.get(0).getPostalCode());
                        return Integer.parseInt(addresses.get(0).getPostalCode());
                    }
                }
            }
        }catch (IOException e) {
            Log.e("tag--PIN", e.getMessage());
            return 0;
        }
        return 0;

    }

    private synchronized void buildGoogleApiClient() {
        this.googleApiClient = new GoogleApiClient.Builder(this.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();

        this.googleApiClient.connect();
    }

    public void setNextClickedListener(NextClickedListener nextClickedListener) {
        this.nextClickedListener = nextClickedListener;
    }

    public void setPreviousClickedListener(PreviousClickedListener previousClickedListener) {
        this.previousClickedListener = previousClickedListener;
    }

    private void loadData() {
        if (merchant.getAddress() != null) {
			edtAddress.setError(null);
            edtAddress.setText(merchant.getAddress());
            merchantRequest.setAddress(merchant.getAddress());
            merchantRequest.setLatitude(merchant.getLatitude());
            merchantRequest.setLongitude(merchant.getLongitude());
        }

        if (merchant.getLocation() == null) {
            return;
        }

        if (merchant.getLocation().getCity() != null) {
            edtCity.setText(merchant.getLocation().getCity().getName());
            merchantRequest.setCityId(merchant.getLocation().getCityId());
        }

        if (merchant.getLocation().getPincode() != null) {
            edtPin.setText(String.valueOf(merchant.getLocation().getPincode()));
            merchantRequest.setPincode(merchant.getLocation().getPincode());
        }
    }

    public void setAddressText(LatLng latLng) {
        myLocationAddress = getCurrentAddress(latLng.latitude, latLng.longitude);
        edtAddress.setError(null);
        edtAddress.setText(myLocationAddress);
    }

    private void addMarker(LatLng latLng) {
        updateLocation(latLng);
        setAddressText(latLng);
        if (marker!=null) {
            googleMap.clear();
            marker = googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                    .position(latLng));

            return;
        }

        marker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                .position(latLng));

    }

    private void displayAddress(String placeDescription, LatLng latLng) {
        edtAddress.setError(null);

        String editCity = edtCity.getText().toString();

        if (!editCity.isEmpty() && latLng.latitude != 0.0 && latLng.longitude != 0.0) {
            String city = getCity(latLng.latitude, latLng.longitude);
            if (city!=null || (!city.equals("") && !city.equalsIgnoreCase(editCity))) {
                bakery.snackLong(getContentView(), "Please select an address in your city");
                showAll();
                return;
            }
            return;

        }
        edtAddress.setText(placeDescription);
        updateLocation(latLng);
        animateCamera(latLng, "address");
        showPinAndMap();
    }

    private void updateLocation(LatLng latLng) {
        merchantRequest.setLatitude(latLng.latitude);
        merchantRequest.setLongitude(latLng.longitude);
        myLocationAddress = getCurrentAddress(latLng.latitude, latLng.longitude);
    }

    private void animateCamera(LatLng latLng, String location) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).tilt(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (location.equals("myLocation")){
            marker = null;
            return;
        }

        marker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                .position(latLng));
    }

}
