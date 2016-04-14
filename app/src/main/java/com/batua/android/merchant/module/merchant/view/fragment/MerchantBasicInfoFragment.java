package com.batua.android.merchant.module.merchant.view.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.widget.Spinner;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Category;
import com.batua.android.merchant.data.model.Merchant.Gallery;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.DecimalFormatUtil;
import com.batua.android.merchant.module.common.util.ImageUtil;
import com.batua.android.merchant.module.merchant.presenter.ImageUploadPresenter;
import com.batua.android.merchant.module.merchant.presenter.ImageUploadViewInteractor;
import com.batua.android.merchant.module.merchant.presenter.MerchantCategoryPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantCategoryViewInteractor;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenter;
import com.batua.android.merchant.module.merchant.view.activity.AddMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.EditMerchantActivity;
import com.batua.android.merchant.module.merchant.view.adapter.AddImagesAdapter;
import com.batua.android.merchant.module.merchant.view.adapter.SpinAdapter;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
import com.batua.android.merchant.module.merchant.view.listener.RemoveImageClickedListener;
import com.squareup.picasso.Picasso;
import com.github.siyamed.shapeimageview.CircularImageView;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import timber.log.Timber;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantBasicInfoFragment extends BaseFragment implements Picker.PickListener, RemoveImageClickedListener, MerchantCategoryViewInteractor, ImageUtil.ImageUtilCallback, ImageUploadViewInteractor, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "Image Multipick";
    private static final int PROFILE_FLAG = 1;
    private static final int ADD_IMAGES_FLAG = 2;
    private static int BASIC_INFO_POSITION = 0;
    private static final int PHOTO_MULTI_SELECT_LIMIT = 5;

    @Inject Bakery bakery;
    @Inject ImageUtil imageUtil;
    @Inject MerchantPresenter merchantPresenter;
    @Inject MerchantCategoryPresenter categoryPresenter;
    @Inject ImageUploadPresenter imageUploadPresenter;

    @Bind(R.id.spinner_merchant_category) Spinner spinnerMerchantCategory;
    @Bind(R.id.add_images_recycler_view) RecyclerView addImagesrecyclerView;
    @Bind(R.id.edt_merchant_email) EditText edtEmail;
    @Bind(R.id.img_profile) ImageView profileImage;
    @Bind(R.id.edt_merchant_name) EditText edtName;
    @Bind(R.id.edt_merchant_short_code) EditText edtShortCode;
    @Bind(R.id.edt_merchant_mobile) EditText edtMobile;
    @Bind(R.id.edt_merchant_fee) EditText edtFee;
    @Bind(R.id.input_layout_merchant_email) TextInputLayout inputLayoutEmail;
    @Bind(R.id.input_layout_merchant_fee) TextInputLayout inputLayoutFee;
    @Bind(R.id.input_layout_merchant_short_code) TextInputLayout inputLayoutShortCode;
    @Bind(R.id.progressBar1) ProgressBar progressBar;

    private NextClickedListener nextClickedListener;
    private Merchant merchant;
    private MerchantRequest merchantRequest;

    private List<String> selectedImages;
    private AddImagesAdapter addImagesAdapter;
    private List<Category> categories;
    private List<String> galleries = new ArrayList<String>();

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
        imageUploadPresenter.attachViewInteractor(this);
        imageUtil.setImageUtilCallback(this);

        merchant = Parcels.unwrap(this.getArguments().getParcelable("Merchant"));
        merchantRequest = (merchant == null) ? ((AddMerchantActivity) getActivity()).getMerchantRequest() : ((EditMerchantActivity) getActivity()).getMerchantRequest();

        categoryPresenter.getCategory();

        if (merchant != null) {
            loadData();
        }

        if (merchant == null) {
            toggleRecyclerViewVisibility(View.GONE);
        }
        setSpinnerListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUtil.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.txt_upload)
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
    void onShortCodeChange(CharSequence text) {

        if (text.toString().isEmpty()) {
            inputLayoutShortCode.setErrorEnabled(false);
            merchantRequest.setShortCode(null);
            return;
        }

        if (text.length() == 8) {
            inputLayoutShortCode.setErrorEnabled(false);
            merchantRequest.setShortCode(text.toString());
            return;
        }

        inputLayoutShortCode.setErrorEnabled(true);
        inputLayoutShortCode.setError("ShortCode must be 8 characters long");
        merchantRequest.setShortCode(null);
    }

    @OnTextChanged(R.id.edt_merchant_email)
    void onEmailChange(CharSequence text) {
        if (edtEmail.getText().toString() == "") {
            inputLayoutEmail.setErrorEnabled(false);
            return;
        }

        if (isValidEmail(text)) {
            merchantRequest.setEmail(text.toString());
            inputLayoutEmail.setErrorEnabled(false);
            return;
        }

        inputLayoutEmail.setErrorEnabled(true);
        inputLayoutEmail.setError("Invalid email");
    }

    @OnTextChanged(R.id.edt_merchant_mobile)
    void onMobileChange(CharSequence text) {
        merchantRequest.setPhone(text.toString());
    }

    @OnTextChanged(R.id.edt_merchant_fee)
    void onFeeChange(CharSequence text) {
        if (text.toString().equalsIgnoreCase("")) {
            merchantRequest.setFee(0.0);
            inputLayoutFee.setErrorEnabled(true);
            inputLayoutFee.setError("Invalid fee");
            return;
        }

        if (Double.valueOf(text.toString()) > 99.99) {
            merchantRequest.setFee(0.0);
            inputLayoutFee.setError("Invalid fee");
            inputLayoutFee.setErrorEnabled(true);
            return;
        }

        if (Double.valueOf(text.toString()) <= 99.99) {
            double fee = DecimalFormatUtil.formatToExactTwoDecimal(text.toString());
            merchantRequest.setFee(fee);
            inputLayoutFee.setErrorEnabled(false);
            return;
        }

        merchantRequest.setFee(0.0);
    }

    @Override
    public void onSuccess(Uri uri, File file) {
        //TODO : call presenter and on success of presenter save url to merchantRequest.
        imageUploadPresenter.uploadImage(file, PROFILE_FLAG);
    }

    // View Interactor overrides
    @Override
    public void showUploadingProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUploadingProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMerchantImageUploadSuccess(String merchantImage) {
        galleries.add(merchantImage);
        merchantRequest.setImageGallery(galleries);
        Timber.d(".......", galleries.toString());
        selectedImages = galleries;
        populateAdapter(galleries, addImagesrecyclerView);
    }

    @Override
    public void onProfileImageUploadSuccess(String merchantImage) {
        merchantRequest.setProfileImageUrl(merchantImage);
        Picasso.with(getActivity()).load(merchantImage).into(profileImage);
    }

    // overidden methods of multipick images
    @Override
    public void onPickedSuccessfully(ArrayList<ImageEntry> images) {
        if (images.size() > 0) {
            toggleRecyclerViewVisibility(View.VISIBLE);
            uploadImage(images);

            return;
        }

        toggleRecyclerViewVisibility(View.GONE);
    }

    private void uploadImage(ArrayList<ImageEntry> selectedImages) {
        for (ImageEntry image : selectedImages) {
            imageUploadPresenter.uploadImage(new File(image.path), ADD_IMAGES_FLAG);
        }
    }

    @Override
    public void onCancel() {
        Log.i(TAG, "User cancelled picker activity");
    }

    //overidden methods of remove clicked image
    @Override
    public void removeClickedPosition(int position) {
        selectedImages.remove(position);

        if (addImagesAdapter != null) {
            addImagesAdapter.notifyDataSetChanged();
        }

        if (selectedImages.isEmpty()) {
            toggleRecyclerViewVisibility(View.GONE);
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
        merchantRequest.setName(merchant.getName());
        edtMobile.setText(merchant.getPhone().toString());
        merchantRequest.setPhone(String.valueOf(merchant.getPhone()));
        edtShortCode.setText(merchant.getShortCode());
        merchantRequest.setShortCode(merchant.getShortCode());
        edtFee.setText(String.valueOf(merchant.getFees()));
        if (merchant.getEmail() != null) {
            edtEmail.setText(merchant.getEmail());
            merchantRequest.setEmail(merchant.getEmail());
        }

        if (categories != null) {
            spinnerMerchantCategory.setSelection(categories.indexOf(merchant.getCategoryId()));
            merchantRequest.setCategoryId(merchant.getCategoryId());
        }

        if (merchant.getProfileImageUrl() != null) {
            Picasso.with(getActivity()).load(merchant.getProfileImageUrl()).placeholder(R.drawable.profile_pic_container).into(profileImage);
            merchantRequest.setProfileImageUrl(merchant.getProfileImageUrl());
        }

        if (merchant.getGalleries() != null && merchant.getGalleries().size() > 0) {
            galleries = new ArrayList<>();
            for (Gallery gallery : merchant.getGalleries()) {
                galleries.add(gallery.getUrl());
            }
            selectedImages = galleries;
            merchantRequest.setImageGallery(galleries);
            populateAdapter(galleries, addImagesrecyclerView);
            toggleRecyclerViewVisibility(View.VISIBLE);
        }
    }

    private void toggleRecyclerViewVisibility(int visiblity) {
        addImagesrecyclerView.setVisibility(visiblity);
    }

    public void setNextClickedListener(NextClickedListener nextClickedListener) {
        this.nextClickedListener = nextClickedListener;
    }

    private void populateAdapter(List<String> customGalleryList, RecyclerView imageRecyclerView) {
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

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
