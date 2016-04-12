package com.batua.android.merchant;

import android.content.pm.ActivityInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Farhan Ali
 *
 * This class is responsible for keeping application configuration as constants.
 */
public class Config {

    //--------------------------------------------------------------------------------
    // App generic configurations
    //--------------------------------------------------------------------------------
    public static final boolean DEBUG = true;

    public static final int ORIENTATION_PORTRAIT    = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    public static final int ORIENTATION_LANDSCAPE   = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    public static final int ORIENTATION_SENSOR      = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
    public static final int ORIENTATION_DEFAULT     = ORIENTATION_PORTRAIT;

    //--------------------------------------------------------------------------------
    // API related constants/configurations - used in ApiModule, should end with `/`
    //--------------------------------------------------------------------------------
    public static final String API_BASE_URL_PRODUCTION = "";
    public static final String API_BASE_URL_DEVELOP = "http://52.36.228.74:1337/";
    public static final String API_BASE_URL_MOCK = "http://android-training.getsandbox.com/";

    // Active base url
    public static final String API_BASE_URL = API_BASE_URL_DEVELOP;

    // Headers required to be added by interceptor
    public static final Map<String, String> API_HEADERS = new HashMap<String, String>() {{
        put("User-Agent", "Batua-Android-App");
        put("Content-Type", "application/json");
    }};

    public static final String MERCHANT = "api/merchant";
    public static final String CATEGORY = "api/category";
    public static final String CITY = "api/city/";
    public static final String LIST_MERCHANT = "/api/salesagent/{salesAgentId}/merchant";
    public static final String UPLOAD_IMAGE = "/api/image/upload/";

}
