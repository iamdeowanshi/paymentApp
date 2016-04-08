package com.batua.android.merchant.module.merchant.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.merchant.util.SpinnerLoad;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
import com.batua.android.merchant.module.merchant.view.listener.RemoveImageClickedListener;
import com.batua.android.merchant.module.merchant.view.adapter.AddImagesAdapter;
import com.batua.android.merchant.module.merchant.view.activity.MerchantDetailsActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.PermissionUtil;
import com.github.siyamed.shapeimageview.CircularImageView;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantBasicInfoFragment extends BaseFragment implements Picker.PickListener, RemoveImageClickedListener {

    private static final String TAG = "Image Multipick";
    private static int BASIC_INFO_POSITION = 0;
    private final static int CAMERA_STORAGE_REQUEST_CODE = 1;
    private final static int GET_IMAGE_FROM_GALLERY_REQUEST_CODE = 2;
    private static final int CAMERA_REQUEST_CODE = 3;
    private static final int READ_STORAGE_REQUEST_CODE = 4;
    private static final int PHOTO_MULTI_SELECT_LIMIT = 10;

    final String[] CAMERA_PERMISSION = {Manifest.permission.CAMERA};
    final String[] STORAGE_PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private ArrayList<ImageEntry> selectedImages;
    private AddImagesAdapter addImagesAdapter;

    @Bind(R.id.spinner_merchant_category) Spinner spinnerMerchantCategory;
    @Bind(R.id.add_images_recycler_view) RecyclerView addImagesrecyclerView;
    @Bind(R.id.edt_merchant_email) EditText edtEmail;
    @Bind(R.id.img_profile) CircularImageView profileImage;
    @Bind(R.id.edt_merchant_name) EditText edtName;
    @Bind(R.id.edt_merchant_short_code) EditText edtShortCode;
    @Bind(R.id.edt_merchant_mobile) EditText edtMobile;
    @Bind(R.id.edt_merchant_fee) EditText edtFee;

    @Inject Bakery bakery;

    private View view;
    private NextClickedListener nextClickedListener;
    private MerchantRequest merchantRequest;

    public MerchantBasicInfoFragment() {
    }

    public MerchantBasicInfoFragment(MerchantRequest merchantRequest) {
        this.merchantRequest = merchantRequest;
    }

    @OnClick(com.batua.android.merchant.R.id.txt_load_next)
    public void loadNextClicked(){
        nextClickedListener.nextClicked(BASIC_INFO_POSITION);
    }

    @OnClick(com.batua.android.merchant.R.id.txt_add_image)
    public void addImage(){
        new Picker.Builder(getContext(), this, com.batua.android.merchant.R.style.AppTheme)
                .setPickMode(Picker.PickMode.MULTIPLE_IMAGES)
                .setLimit(PHOTO_MULTI_SELECT_LIMIT)
                .build()
                .startActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(com.batua.android.merchant.R.layout.fragment_merchant_basic_info, null);
        onViewCreated(view, null);
        Injector.component().inject(this);
        SpinnerLoad.loadSpinner(getContext(), com.batua.android.merchant.R.array.merchant_category, spinnerMerchantCategory);
        hideAddImageRecyclerView();
        loadData();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case com.batua.android.merchant.R.id.action_save:
                startActivity(MerchantDetailsActivity.class, null);
                getActivity().onOptionsItemSelected(item);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {

            case CAMERA_STORAGE_REQUEST_CODE:
                
                break;

            case GET_IMAGE_FROM_GALLERY_REQUEST_CODE:

                /*Uri selectedImageUri = data.getData();
                ArrayList<ImageEntry> customGalleries = new ArrayList<ImageEntry>();
                CustomGallery item = new CustomGallery();
                item.setImagePath(selectedImageUri);
                customGalleries.add(item);
                populateAdapter(customGalleries, addImagesrecyclerView);
                showAddImageRecyclerView();*/

                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case CAMERA_REQUEST_CODE:
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    // Permission Granted
                    startCamera();
                } else {
                    // Permission Denied
                    showCameraPermissionsSnackbar();
                }

                break;

            case READ_STORAGE_REQUEST_CODE:
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    // Permission Granted
                    chooseFromGallery();
                } else {
                    // Permission Denied
                    showReadStoragePermissionsSnackbar();
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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

        if (addImagesAdapter!=null) {
            addImagesAdapter.notifyDataSetChanged();
        }

        if (selectedImages.isEmpty()) {
            hideAddImageRecyclerView();
        }
    }

    private void loadData() {
        merchantRequest.setName(edtName.getText().toString());
    }

    private void hideAddImageRecyclerView() {
        addImagesrecyclerView.setVisibility(View.GONE);
    }

    private void showAddImageRecyclerView() {
        addImagesrecyclerView.setVisibility(View.VISIBLE);
    }

    public void setNextClickedListener(NextClickedListener nextClickedListener){
        this.nextClickedListener = nextClickedListener;
    }

    public void getImage(){
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Choose what to use!")
                .setIcon(android.R.drawable.ic_menu_camera)
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            checkCameraPermission();
                        } else if (options[item].equals("Choose from Gallery")) {
                            checkReadStoragePermission();
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });

        builder.create().show();
    }

    private void checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this.getActivity(), CAMERA_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermissions();
        } else {
            startCamera();
        }
    }

    private void checkReadStoragePermission() {
        if (ActivityCompat.checkSelfPermission(this.getActivity(), STORAGE_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(), STORAGE_PERMISSION[1]) != PackageManager.PERMISSION_GRANTED) {
            requestReadStoragePermissions();
        } else {
            chooseFromGallery();
        }
    }

    private void requestReadStoragePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), STORAGE_PERMISSION[0]) && ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), STORAGE_PERMISSION[1])) {
            Timber.d("request");
            // Display a SnackBar with an explanation and a button to trigger the request.
            bakery.snack(getContentView(), "Storage permission required for accessing image", Snackbar.LENGTH_INDEFINITE, "Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(getActivity(), STORAGE_PERMISSION, READ_STORAGE_REQUEST_CODE);
                }
            });

        } else {
            // Permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(getActivity(), STORAGE_PERMISSION, READ_STORAGE_REQUEST_CODE);
        }
    }


    private void requestCameraPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), CAMERA_PERMISSION[0])) {
            Timber.d("request");
            // Display a SnackBar with an explanation and a button to trigger the request.
            bakery.snack(getContentView(), "Camera permission are required for uploading images", Snackbar.LENGTH_INDEFINITE, "Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(getActivity(), CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
                }
            });

        } else {
            // Permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(getActivity(), CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
        }
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(this.getActivity().getPackageManager()) != null) {
            Log.d("Internal:", "............................");
            startActivityForResult(intent, CAMERA_STORAGE_REQUEST_CODE);
        }
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), GET_IMAGE_FROM_GALLERY_REQUEST_CODE);
    }

    private void showCameraPermissionsSnackbar() {
        Snackbar.make(getContentView(), "Camera permission is required to continue!", Snackbar.LENGTH_LONG)
                .setAction("ALLOW", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkCameraPermission();
                    }
                });
    }

    private void showReadStoragePermissionsSnackbar() {
        Snackbar.make(getContentView(), "Storage permission is required to continue!", Snackbar.LENGTH_LONG)
                .setAction("ALLOW", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkReadStoragePermission();
                    }
                });

    }

    public View getContentView(){
        return (View)getActivity().findViewById(android.R.id.content);
    }

    private void populateAdapter(List<ImageEntry> customGalleryList, RecyclerView imageRecyclerView){
        addImagesAdapter = new AddImagesAdapter(customGalleryList, this);
        LinearLayoutManager llayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(llayout);
        imageRecyclerView.setAdapter(addImagesAdapter);
    }

}
