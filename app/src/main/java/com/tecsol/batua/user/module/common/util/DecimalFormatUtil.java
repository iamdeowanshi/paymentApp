package com.tecsol.batua.user.module.common.util;

import android.util.Log;

/**
 * Created by Arnold on 24/03/16.
 */
public class DecimalFormatUtil {

    public static float formatToExactTwoDecimal(float distance){

        return Math.round(distance*100.0f)/100.0f;

    }
}
