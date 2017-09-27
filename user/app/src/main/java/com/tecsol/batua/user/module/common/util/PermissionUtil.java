package com.tecsol.batua.user.module.common.util;

import android.content.pm.PackageManager;

import com.tecsol.batua.user.injection.Injector;


/**
 * Created by Sibi on 14/01/16.
 */

/**
 * Utility class that wraps access to the runtime permissions API in M and provides basic helper
 * methods.
 */
public class PermissionUtil {

    public PermissionUtil() {
        Injector.component().inject(this);
    }

    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
