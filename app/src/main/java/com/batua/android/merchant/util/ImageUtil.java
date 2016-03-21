package com.batua.android.merchant.util;

import android.Manifest;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.batua.android.merchant.app.di.Injector;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * @author Aaditya Deowanshi.
 */
public class ImageUtil implements ActivityCompat.OnRequestPermissionsResultCallback {

    @Inject Bakery bakery;
    @Inject PermissionUtil permissionUtil;

    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    private static String[] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private ImageUtilCallback utilCallback;
    private Activity activity;

    public ImageUtil() {
        Injector.instance().inject(this);
    }

    public void setImageUtilCallback( ImageUtilCallback utilCallback) {
        this.utilCallback = utilCallback;
    }

    public void getImage(Activity activity) {
        this.activity = activity;

        checkPermissions();
    }

    /**
     * Checking runtime permission.
     */
    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(activity, PERMISSION[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, PERMISSION[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, PERMISSION[2]) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();

            return;
        }

        showPictureDialog();
    }

    /**
     * Requesting runtime permission for camera and storage.
     */
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION[0])
                 || ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION[2])) {
            bakery.snack(getContentView(), "Camera and storage permission are required to change image", Snackbar.LENGTH_INDEFINITE, "Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(activity, PERMISSION, CAMERA_REQUEST_CODE);
                }
            });

            return;
        }

        ActivityCompat.requestPermissions(activity, PERMISSION, CAMERA_REQUEST_CODE);
    }


    /**
     * Shows dialog to choose options for profile picture upload.
     */
    private void showPictureDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Photo!");
        builder.setIcon(android.R.drawable.ic_menu_camera);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    openCamera();
                } else if (items[item].equals("Choose from Gallery")) {
                    openGallery();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissionUtil.verifyPermissions(grantResults)) {
            Timber.d("come here");
            bakery.snackShort(getContentView(), "Permissions have been granted");
            showPictureDialog();

            return;
        }

        bakery.snackShort(getContentView(), "Permissions were not granted");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri;

        if (resultCode != Activity.RESULT_OK) {

            return;
        }

        switch (requestCode) {
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                uri = saveBitmapToMedia(bitmap);
                utilCallback.onSuccess(uri, new File(getRealPathFromUri(uri)));
                break;

            case MEDIA_TYPE_IMAGE:
                uri = data.getData();
                utilCallback.onSuccess(uri, new File(getRealPathFromUri(uri)));
                break;
        }
    }

    public View getContentView() {
        return activity.findViewById(android.R.id.content);
    }


    /**
     * Method to open camera.
     */
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * Method to open gallery.
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), MEDIA_TYPE_IMAGE);
    }

    /**
     * Method to save bitmap image in media.
     *
     * @param inImage
     * @return
     */
    private Uri saveBitmapToMedia(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), inImage, "MTVConnectProfile", null);

        return Uri.parse(path);
    }

    /**
     * Method to return real path of an image from uri.
     *
     * @param contentUri
     * @return
     */
    private String getRealPathFromUri(Uri contentUri) {
        String[] projections = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(activity, contentUri, projections, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();

        return result;
    }

    public interface ImageUtilCallback {

        void onSuccess(Uri uri, File file);

    }

}
