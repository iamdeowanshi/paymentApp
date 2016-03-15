package com.batua.android.user.app;

import android.content.pm.ActivityInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Farhan Ali
 *         <p>
 *         This class is responsible for keeping application configuration as constants.
 */
public class Config {

    //--------------------------------------------------------------------------------
    // App generic configurations
    //--------------------------------------------------------------------------------
    public static final boolean DEBUG = true;

    public static final int ORIENTATION_PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    public static final int ORIENTATION_LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    public static final int ORIENTATION_SENSOR = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
    public static final int ORIENTATION_DEFAULT = ORIENTATION_PORTRAIT;

    //--------------------------------------------------------------------------------
    // API related constants/configurations - used in ApiModule
    //--------------------------------------------------------------------------------
    public static final String API_BASE_URL_PRODUCTION = "";
    public static final String API_BASE_URL_DEVELOP = "";
    public static final String API_BASE_URL_MOCK = "";

    // Active base url
    public static final String API_BASE_URL = API_BASE_URL_MOCK;

    // Cache size in bytes, 50 MB = 50 * 1024 KB, 1 KB = 1024 Bytes
    public static final int HTTP_DISK_CACHE_SIZE = (int) 50 * 1024 * 1024;

    // Headers required to be added by interceptor
    public static final Map<String, String> API_HEADERS = new HashMap<String, String>() {{
        put("User-Agent", "Batua-Android-User-App");
        put("Content-Type", "application/json");
    }};

}
