package com.tecsol.batua.user.data.model.User;

import com.google.gson.annotations.SerializedName;

/**
 * @author Arnold Laishram.
 */
public class CustomResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("userId")
    private Integer userId;

    public String getMessage() {
        return message;
    }

    public Integer getUserId() {
        return userId;
    }
}
