package com.batua.android.merchant.module.merchant.view.fragment;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.ViewUtil;
import com.batua.android.merchant.module.merchant.view.adapter.SearchAddressAdapter;
import com.batua.android.merchant.module.merchant.view.listener.AutoCompleteRecyclerItemClickListener;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantLocationInfoFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMyLocationChangeListener {

    private static final int LOCATION_INFO_POSITION = 1;
    private static final LatLngBounds BOUNDS = new LatLngBounds(new LatLng(-85, 180), new LatLng(85, -180));
    public static final String NO_SERVICE = "No Service!";

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;

    @Bind(R.id.recyclerView_search_address) RecyclerView searchAddressRecycler;
    @Bind(R.id.edt_merchant_address) EditText edtAddress;
    @Bind(R.id.edt_merchant_pin_code) EditText edtPinCode;
    @Bind(R.id.edt_merchant_city) AutoCompleteTextView txtCity;
    @Bind(R.id.merchant_location_map) LinearLayout locationLayout;
    @Bind(R.id.navigation_layout) LinearLayout navigationLayout;
    @Bind(R.id.scrollView) ScrollView scrollView;

    private GoogleApiClient googleApiClient;
    private NextClickedListener nextClickedListener;
    private PreviousClickedListener previousClickedListener;
    private SearchAddressAdapter searchAddressAdapter;
    private GoogleMap googleMap;

    @OnClick(com.batua.android.merchant.R.id.txt_load_next)
    public void onNextClicked() {
        nextClickedListener.nextClicked(LOCATION_INFO_POSITION);
    }

    @OnClick(com.batua.android.merchant.R.id.txt_load_previous)
    public void onPreviousClicked() {
        previousClickedListener.previousClicked(LOCATION_INFO_POSITION);
    }

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
        buildGoogleApiClient();
        initialiseMapUiSettings();
        inflateSearchAddressAdapter();

        edtAddress.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("") && googleApiClient.isConnected()) {
                    hidePinAndMap();

                    searchAddressAdapter.getFilter().filter(s.toString());
                } else if (!googleApiClient.isConnected()) {
                    bakery.snack(getContentView(), NO_SERVICE, Snackbar.LENGTH_SHORT);
                } else {
                    showPinAndMap();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {

            }

        });

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
                v.getParent().requestDisallowInterceptTouchEvent(true);
                v.requestFocus();
                return true;
            }
        });
    }

    // GoogleApiClient.ConnectionCallbacks and GoogleApiClient.OnConnectionFailedListener override methods
    @Override
    public void onMyLocationChange(Location location) {
        animateCamera(new LatLng(location.getLatitude(), location.getLongitude()), "myLocation");
    }

    @Override
    public void onConnected(Bundle bundle) {
        showCurrentPosition();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void inflateSearchAddressAdapter() {
        searchAddressAdapter = new SearchAddressAdapter(this.getContext(), R.layout.search_address, googleApiClient, BOUNDS, null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        searchAddressRecycler.setLayoutManager(linearLayoutManager);
        searchAddressRecycler.setAdapter(searchAddressAdapter);
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
            }
        });
    }

    private void hidePinAndMap() {
        searchAddressRecycler.setVisibility(View.VISIBLE);
        edtPinCode.setVisibility(View.GONE);
        locationLayout.setVisibility(View.GONE);
        navigationLayout.setVisibility(View.GONE);
        googleMap.setOnMyLocationChangeListener(this);
    }

    private void showPinAndMap() {
        searchAddressRecycler.setVisibility(View.GONE);
        edtPinCode.setVisibility(View.VISIBLE);
        locationLayout.setVisibility(View.VISIBLE);
        navigationLayout.setVisibility(View.VISIBLE);
        viewUtil.hideKeyboard(MerchantLocationInfoFragment.this.getActivity());
        googleMap.setOnMyLocationChangeListener(null);
    }

    private void displayAddress(String placeDescription, LatLng latLng) {
        edtAddress.setText(placeDescription);
        animateCamera(latLng, "address");
        showPinAndMap();
    }

    private void animateCamera(LatLng curentpoint, String location) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(curentpoint).zoom(15).tilt(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.clear();

        if (location.equals("myLocation")){
            return;
        }

        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                .position(curentpoint));
    }

    private void showCurrentPosition() {
        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                LatLng curentpoint = new LatLng(location.getLatitude(), location.getLongitude());
                animateCamera(curentpoint, "myLocation");
            }
        } catch (SecurityException e) {

        }
    }

    protected synchronized void buildGoogleApiClient() {
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

}
