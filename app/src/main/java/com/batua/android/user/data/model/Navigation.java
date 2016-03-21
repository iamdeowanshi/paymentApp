package com.batua.android.user.data.model;

/**
 * @author Arnold Laishram.
 */
public class Navigation {

    private String imageUrl;
    private String userName;
    private String walletAmount;

    public Navigation(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

}
