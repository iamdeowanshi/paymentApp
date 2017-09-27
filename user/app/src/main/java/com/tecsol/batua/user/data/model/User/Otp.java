package com.tecsol.batua.user.data.model.User;

import com.google.gson.annotations.SerializedName;

/**
 * @author Arnold Laishram.
 */
public class Otp {

    @SerializedName("phone")
    private long phone;

    @SerializedName("otp")
    private long otp;

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("userId")
    private Integer userId;

    @SerializedName("type")
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getOtp() {
        return otp;
    }

    public void setOtp(long otp) {
        this.otp = otp;
    }
}
