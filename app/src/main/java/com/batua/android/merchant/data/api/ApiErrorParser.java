package com.batua.android.merchant.data.api;

import com.batua.android.merchant.injection.Injector;
import com.google.gson.Gson;

import javax.inject.Inject;

import okhttp3.ResponseBody;

/**
 * @author Aaditya Deowanshi.
 */
public class ApiErrorParser {

    @Inject Gson gson;

    public ApiErrorParser() {
        Injector.component().inject(this);
    }

    public ApiErrorResponse parse(ResponseBody responseBody) {
        try {
            String errorJson = responseBody.string();
            return gson.fromJson(errorJson, ApiErrorResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

}
