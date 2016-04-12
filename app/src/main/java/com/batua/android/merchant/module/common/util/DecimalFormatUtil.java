package com.batua.android.merchant.module.common.util;

import android.util.Log;

/**
 * Created by Aaditya Deowanshi on 24/02/16.
 */
public class DecimalFormatUtil {

    public static Double formatToExactTwoDecimal(String distance){

        double eventDis = 0.0;

        if (distance.length() > 4) {

            try {
                double tempNum = Float.parseFloat(distance);
                eventDis = Math.round(tempNum*100.0f)/100.0f;

            } catch (NumberFormatException e) {
                Log.d("", e.toString());
            }

        } else {

            try {
                eventDis = Double.parseDouble(distance);

            } catch (NumberFormatException e) {
                Log.d("",e.toString());
            }


        }

        return eventDis;

    }
}
