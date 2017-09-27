package com.batua.android.merchant.data.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiErrorResponse {

    @SerializedName("errors")
    public List<Error> errors;

    public static class Error {
        @SerializedName("message")
        public String message;
    }

}