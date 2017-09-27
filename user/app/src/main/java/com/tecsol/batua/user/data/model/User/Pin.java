package com.tecsol.batua.user.data.model.User;

import com.google.gson.annotations.SerializedName;

/**
 * @author Arnold Laishram.
 */
public class Pin {

    @SerializedName("pin")
    private int pin;

    @SerializedName("userId")
    private Integer userId;

    @SerializedName("isPinActivated")
    private boolean isPinActivated;

    @SerializedName("currentPin")
    private int currentPin;

    @SerializedName("newPin")
    private int newPin;

    @SerializedName("deviceId")
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getCurrentPin() {
        return currentPin;
    }

    public void setCurrentPin(int currentPin) {
        this.currentPin = currentPin;
    }

    public int getNewPin() {
        return newPin;
    }

    public void setNewPin(int newPin) {
        this.newPin = newPin;
    }

    public boolean isPinActivated() {
        return isPinActivated;
    }

    public void setIsPinActivated(boolean isPinActivated) {
        this.isPinActivated = isPinActivated;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int phone) {
        this.pin = phone;
    }
}
