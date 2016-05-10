package com.tecsol.batua.user.module.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tecsol.batua.user.BatuaUserApplication;
import com.tecsol.batua.user.module.base.BaseActivity;

public class InternetStatusReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        BaseActivity currentActivity = ((BatuaUserApplication)context.getApplicationContext()).getCurrentActivity();
        currentActivity.showNoInternetTitleDialog(currentActivity);
    }

}
