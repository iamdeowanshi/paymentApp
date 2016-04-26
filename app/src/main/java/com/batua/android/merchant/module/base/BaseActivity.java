package com.batua.android.merchant.module.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.batua.android.merchant.BatuaApplication;
import com.batua.android.merchant.Config;
import com.batua.android.merchant.module.common.util.InternetUtil;

import butterknife.ButterKnife;

/**
 * Provides some basic operations, all activities should extend this class.
 *
 * @author Farhan Ali
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final String NO_INTERNET_TITTLE = "No Internet";
    private final String NO_INTERNET_MESSAGE = "Please Check your Connection";

    private int orientation = Config.ORIENTATION_DEFAULT;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*ActivityManager mngr = (ActivityManager) getSystemService( ACTIVITY_SERVICE );

        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

        if(taskList.get(0).numActivities == 1 &&
                taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            return;
        }

        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);*/
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //noinspection WrongConstant
        setRequestedOrientation(orientation);
    }

    /**
     * Binds the viewInteractor objects within the activity.
     */
    protected void bindViews() {
        ButterKnife.bind(this);
    }

    /**
     * Starts an activity with a bundle set to the intent.
     *
     * @param activityClass Class<? extends Activity>
     * @param bundle Bundle
     */
    protected void startActivity(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);

        if (bundle != null) intent.putExtras(bundle);

        startActivity(intent);
    }

    /**
     * Start an activity by clearing all previous activities.
     *
     * @param activityClass Class<? extends Activity>
     * @param bundle Bundle
     */
    protected void startActivityClearTop(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        if (bundle != null) intent.putExtras(bundle);

        startActivity(intent);
    }

    /**
     * Restarts an activity
     */
    protected void restartActivity() {
        finish();
        startActivity(getIntent());
    }

    /**
     * Sets orientation to portrait only.
     */
    protected void setOrientationPortrait() {
        orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    /**
     * Sets orientation to landscape only.
     */
    protected void setOrientationLandscape() {
        orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    /**
     * Sets orientation to sensor mode.
     */
    protected void setOrientationSensor() {
        orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
    }

    /**
     * Set orientation to any supported one.
     *
     * @param orientation ActivityInfo.ORIENTATION_*
     */
    protected void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    /**
     * Get the content view of an activity.
     *
     * @return
     */
    protected View getContentView() {
        return findViewById(android.R.id.content);
    }

    public void showNoInternetTitleDialog(Activity currentActivity){

        if ( currentActivity!= null) {
            AlertDialog.Builder alertbuilder = new AlertDialog.Builder(currentActivity);

            alertbuilder.setTitle(NO_INTERNET_TITTLE)
                    .setMessage(NO_INTERNET_MESSAGE)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            checkInternetConnection();
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = alertbuilder.create();
            alert.show();
        }
    }

    private void checkInternetConnection(){
        if(!(InternetUtil.hasInternetConnection(getApplicationContext()))){
            showNoInternetTitleDialog(this);
        }
    }

}
