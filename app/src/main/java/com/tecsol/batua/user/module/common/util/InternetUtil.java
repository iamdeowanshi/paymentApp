package com.tecsol.batua.user.module.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by febinp on 13/11/15.
 */
public class InternetUtil {

    public InternetUtil() {}

    public static boolean  hasInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getActiveNetworkInfo();
        if (wifiNetwork != null && wifiNetwork.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }


}
