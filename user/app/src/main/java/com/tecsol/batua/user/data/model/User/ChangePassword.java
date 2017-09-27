package com.tecsol.batua.user.data.model.User;

import com.google.gson.annotations.SerializedName;

/**
 * @author Arnold Laishram.
 */
public class ChangePassword {
    @SerializedName("userId")
    private Integer userId;

    @SerializedName("currentPassword")
    private String currentPassword;

    @SerializedName("newPassword")
    private String newPassword;

    @SerializedName("password")
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
