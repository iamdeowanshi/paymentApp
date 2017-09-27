package com.tecsol.batua.user.module.common.util;

/**
 * Created by Arnold on 24/03/16.
 */
public class DecimalFormatUtil {

    public static float formatToExactTwoDecimal(float distance){

        return Math.round(distance*10.0f)/10.0f;

    }
}
