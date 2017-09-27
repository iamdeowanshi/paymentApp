package com.tecsol.batua.user.module.payment.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Arnold Laishram.
 */
public class DateValidationUtil {

    public static String formatTime(String dateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString.split("\\.")[0]);
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

        Format formatter = new SimpleDateFormat("HH:mm:ss a");
        return (formatter.format(convertedDate));
    }

    public static String formatDateNormal(String dateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString.split("\\.")[0]);
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

        Format formatter = new SimpleDateFormat("dd MM yyyy");
        return (formatter.format(convertedDate));
    }
}
