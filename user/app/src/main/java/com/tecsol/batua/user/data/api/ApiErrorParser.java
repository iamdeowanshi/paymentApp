package com.tecsol.batua.user.data.api;

import com.google.gson.Gson;
import com.tecsol.batua.user.injection.Injector;

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
