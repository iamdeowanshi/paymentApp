package com.batua.android.merchant.module.merchant.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Category;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.ImageUtil;
import com.batua.android.merchant.module.merchant.presenter.MerchantCategoryPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantCategoryViewInteractor;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantViewInteractor;
import com.batua.android.merchant.module.merchant.view.activity.AddMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.EditMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.MerchantDetailsActivity;
import com.batua.android.merchant.module.merchant.view.adapter.AddImagesAdapter;
import com.batua.android.merchant.module.merchant.view.adapter.SpinAdapter;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
import com.batua.android.merchant.module.merchant.view.listener.RemoveImageClickedListener;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import org.parceler.Parcels;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantBasicInfoFragment extends BaseFragment implements Picker.PickListener, RemoveImageClickedListener, MerchantCategoryViewInteractor, ImageUtil.ImageUtilCallback {

    private static final String TAG = "Image Multipick";
    private static int BASIC_INFO_POSITION = 0;
    private static final int PHOTO_MULTI_SELECT_LIMIT = 10;

    @Inject Bakery bakery;
    @Inject ImageUtil imageUtil;
    @Inject MerchantPresenter merchantPresenter;
    @Inject MerchantCategoryPresenter categoryPresenter;

    @Bind(R.id.spinner_merchant_category) Spinner spinnerMerchantCategory;
    @Bind(R.id.add_images_recycler_view) RecyclerView addImagesrecyclerView;
    @Bind(R.id.edt_merchant_email) EditText edtEmail;
    @Bind(R.id.img_profile) ImageView profileImage;
    @Bind(R.id.edt_merchant_name) EditText edtName;
    @Bind(R.id.edt_merchant_short_code) EditText edtShortCode;
    @Bind(R.id.edt_merchant_mobile) EditText edtMobile;
    @Bind(R.id.edt_merchant_fee) EditText edtFee;

    private NextClickedListener nextClickedListener;
    private Merchant merchant;
    private MerchantRequest merchantRequest;

    private ArrayList<ImageEntry> selectedImages;
    private AddImagesAdapter addImagesAdapter;
    private List<Category> categories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.batua.android.merchant.R.layout.fragment_merchant_basic_info, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Injector.component().inject(this);
        categoryPresenter.attachViewInteractor(this);
        imageUtil.setImageUtilCallback(this);

        merchant = Parcels.unwrap(this.getArguments().getParcelable("Merchant"));
        merchantRequest = (merchant == null) ? ((AddMerchantActivity) getActivity()).getMerchantRequest() : ((EditMerchantActivity) getActivity()).getMerchantRequest();

        categoryPresenter.getCategory();

        if (merchant != null) {
            loadData();
        }

        hideAddImageRecyclerView();
        setSpinnerListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUtil.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.img_profile)
    void onProfileImgClick() {
        imageUtil.getImage(getActivity());
    }

    @OnClick(R.id.txt_load_next)
    public void loadNextClicked() {
        nextClickedListener.nextClicked(BASIC_INFO_POSITION);
    }

    @OnClick(R.id.txt_add_image)
    public void addImage() {
        new Picker.Builder(getContext(), this, com.batua.android.merchant.R.style.AppTheme)
                .setPickMode(Picker.PickMode.MULTIPLE_IMAGES)
                .setLimit(PHOTO_MULTI_SELECT_LIMIT)
                .build()
                .startActivity();
    }

    @OnTextChanged(R.id.edt_merchant_name)
    void onNameChange(CharSequence text) {
        merchantRequest.setName(text.toString());
    }

    @OnTextChanged(R.id.edt_merchant_short_code)
    void onSHortCodeChange(CharSequence text) {
        merchantRequest.setShortCode(text.toString());
    }

    @OnTextChanged(R.id.edt_merchant_email)
    void onEmailChange(CharSequence text) {
        merchantRequest.setEmail(text.toString());
    }

    @OnTextChanged(R.id.edt_merchant_mobile)
    void onMobileChange(CharSequence text) {
        merchantRequest.setPhone(text.toString());
    }

    @OnTextChanged(R.id.edt_merchant_fee)
    void onFeeChange(int text) {
        merchantRequest.setFee(text);
    }

    @Override
    public void onSuccess(Uri uri, File file) {
        //TODO : call presenter and on success of presenter save url to merchantRequest.
        Glide.with(this).load(uri).into(profileImage);
    }

    // overidden methods of multipick images
    @Override
    public void onPickedSuccessfully(ArrayList<ImageEntry> images) {
        if (images.size() > 0) {
            showAddImageRecyclerView();
            selectedImages = images;
            populateAdapter(selectedImages, addImagesrecyclerView);

            return;
        }

        hideAddImageRecyclerView();
    }

    @Override
    public void onCancel() {
        Log.i(TAG, "User canceled picker activity");
    }

    //overidden methods of remove clicked image
    @Override
    public void removeClickedPosition(int position) {
        selectedImages.remove(position);

        if (addImagesAdapter != null) {
            addImagesAdapter.notifyDataSetChanged();
        }

        if (selectedImages.isEmpty()) {
            hideAddImageRecyclerView();
        }
    }

    @Override
    public void showCategory(List<Category> categories) {
        this.categories = categories;

        SpinAdapter spinAdapter = new SpinAdapter(getContext(), android.R.layout.simple_spinner_item, categories);
        spinnerMerchantCategory.setSelection(0);
        spinnerMerchantCategory.setAdapter(spinAdapter);
    }

    private void loadData() {
        edtName.setText(merchant.getName());
        edtMobile.setText(merchant.getPhone().toString());
        edtShortCode.setText(merchant.getShortCode());
        edtFee.setText(String.valueOf(merchant.getFees()));
        if (merchant.getEmail() != null) {
            edtEmail.setText(merchant.getEmail());
        }

        if (categories != null) {
            spinnerMerchantCategory.setSelection(categories.indexOf(merchant.getCategoryId()));
        }

        if (merchant.getProfileImageUrl() != null) {
            Glide.with(this).load(merchant.getProfileImageUrl()).placeholder(R.drawable.profile_pic_container).fitCenter().into(profileImage);
        }
    }

    private void hideAddImageRecyclerView() {
        addImagesrecyclerView.setVisibility(View.GONE);
    }

    private void showAddImageRecyclerView() {
        addImagesrecyclerView.setVisibility(View.VISIBLE);
    }

    public void setNextClickedListener(NextClickedListener nextClickedListener) {
        this.nextClickedListener = nextClickedListener;
    }

    private void populateAdapter(List<ImageEntry> customGalleryList, RecyclerView imageRecyclerView) {
        addImagesAdapter = new AddImagesAdapter(customGalleryList, this);
        LinearLayoutManager llayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(llayout);
        imageRecyclerView.setAdapter(addImagesAdapter);
    }

    private void setSpinnerListener() {
        spinnerMerchantCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                merchantRequest.setCategoryId(categories.get(spinnerMerchantCategory.getSelectedItemPosition()).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                merchantRequest.setCategoryId(categories.get(spinnerMerchantCategory.getSelectedItemPosition()).getId());
            }
        });
    }

}
