package com.tecsol.batua.user;

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
    public static final String API_BASE_URL_LOCAL = "http://192.168.2.152:1337/";
    public static final String API_BASE_URL_MOCK = "http://android-training.getsandbox.com/";

    // Active base url
    public static final String API_BASE_URL = API_BASE_URL_DEVELOP;

    // Headers required to be added by interceptor
    public static final Map<String, String> API_HEADERS = new HashMap<String, String>() {{
        put("User-Agent", "Tecsol-User-App");
        put("Content-Type", "application/json");
    }};

    public static final String NORMAL_SIGN_UP = "api/user/normal/signup";
    public static final String SOCIAL_SIGN_UP = "api/user/social/signup";

    public static final String NORMAL_LOGIN = "api/user/normal/login";
    public static final String SOCIAL_LOGIN = "api/user/social/login";

    public static final String GET_MERCHANTS = "api/user/{userId}/merchant/{merchantId}/latitude/{latitude}/longitude/{longitude}/";

    public static final String GET_PARTICULAR_MERCHANTS = "api/user/{userId}/merchant/{merchantId}/latitude/{latitude}/longitude/{longitude}/";

    public static final String SAVE_PIN = "user/savePin";

    public static final String SEND_SIGN_UP_OTP = "api/user/signup/sendotp";

    public static final String VERIFY_SIGN_UP_OTP = "api/user/signup/verifyotp";

    public static final String VERIFY_FORGOT_PIN_OR_PASSWORD_OTP = "api/user/verifyotp";

    public static final String UPDATE_PROFILE = "api/user/profile";

    public static final String GET_PROFILE = "api/user/{userId}/profile";

    public static final String SET_RESET_PIN = "api/user/pin/reset";

    public static final String RESET_PASSWORD = "api/user/resetpassword";

    public static final String UPLOAD_IMAGE = "api/image/upload";

    public static final String UPDATE_PIN_STATUS = "api/user/pin/status";

    public static final String CHANGE_PIN = "api/user/pin/change";

    public static final String LOGIN_BY_PIN = "api/user/pin/login";

    public static final String CHANGE_PASSWORD = "api/user/changepassword";

    public static final String LOG_OUT_USER = "api/user/logout";

    public static final String SEND_FORGOT_PIN_PASSWORD_OTP = "api/user/forgotpassword";

    public static final String VALIDATE_PROMOCODE = "api/user/validatePromocode";

    public static final String OFFER_EXIST = "api/user/validateOffer";

    public static final String CONTACT_BATUA = "api/user/contactus";

    public static int OTP_REQUEST_ACTIVITY = 0;
    public static int FORGOT_PIN_ACTIVITY = 1;
    public static int FORGOT_PASSWORD_ACTIVITY = 2;
    public static int PHONE_VERIFICATION_AFTER_SIGNUP_ACTIVITY = 3;
    public static int PHONE_VERIFICATION_AFTER_LOGIN_ACTIVITY = 4;



}
