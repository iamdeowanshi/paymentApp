package com.tecsol.batua.user.module.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;


import com.tecsol.batua.user.injection.Injector;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * @author Aaditya Deowanshi.
 */
public class ImageUtil {

    @Inject Bakery bakery;
    @Inject PermissionUtil permissionUtil;

    private static final int REQUEST_CAMERA     = 100;
    private static final int REQUEST_GALLERY    = 101;
    private static final int REQUEST_CROP       = 102;

    private Uri imageCaptureUri;
    private static File file = null;

    private static String[] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private ImageUtilCallback utilCallback;
    private Activity activity;

    public ImageUtil() {
        Injector.component().inject(this);
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
                    ActivityCompat.requestPermissions(activity, PERMISSION, REQUEST_CAMERA);
                }
            });

            return;
        }

        ActivityCompat.requestPermissions(activity, PERMISSION, REQUEST_CAMERA);
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

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissionUtil.verifyPermissions(grantResults)) {
            Timber.d("come here");
            bakery.snackShort(getContentView(), "Permissions have been granted");
            showPictureDialog();

            return;
        }

        bakery.snackShort(getContentView(), "Permissions were not granted");
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        this.activity = activity;
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        Uri uri = null;
        switch (requestCode) {
            case REQUEST_CAMERA:
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                uri = saveBitmapToMedia(bitmap);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    performCrop(uri);
                }
                break;
            case REQUEST_GALLERY:
                performCrop(data.getData());
                break;
            case REQUEST_CROP:
                uri = saveCroppedPic(data.getExtras());
                break;
        }

        if (uri != null) {
            utilCallback.onSuccess(uri, new File(getRealPathFromUri(uri)));
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
        file = new File(Environment
                .getExternalStorageDirectory(), "batua_"
                + String.valueOf(System.currentTimeMillis())
                + ".jpg");
        imageCaptureUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                imageCaptureUri);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, REQUEST_CAMERA);
    }

    /**
     * Method to open gallery.
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "Select Photo"), REQUEST_GALLERY);
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
        String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), inImage, "Batua-Profile", null);

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

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 500);
            cropIntent.putExtra("outputY", 500);
            cropIntent.putExtra("return-data", true);
            activity.startActivityForResult(cropIntent, REQUEST_CROP);
        } catch (ActivityNotFoundException e) {
            bakery.toastShort("This device doesn't support the crop action!");
        }
    }

    private Uri saveCroppedPic(Bundle extras) {
        Bitmap bitmap = (Bitmap) extras.get("data");

        if (bitmap == null) return null;

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "Batua-Profile", null);

        return Uri.parse(path);
    }

    public interface ImageUtilCallback {

        void onSuccess(Uri uri, File file);

    }

}
