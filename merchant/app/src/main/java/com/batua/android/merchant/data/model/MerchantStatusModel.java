package com.batua.android.merchant.data.model;

/**
 * Created by febinp on 03/03/16.
 */
public class MerchantStatusModel {

    private String merchantTitle;
    private String merchantAddress;
    private String merchantShortCode;
    private String status;

    public MerchantStatusModel(String merchantTitle, String merchantAddress, String merchantShortCode, String status) {
        this.merchantTitle = merchantTitle;
        this.merchantAddress = merchantAddress;
        this.merchantShortCode = merchantShortCode;
        this.status = status;
    }

    public String getMerchantTitle() {
        return merchantTitle;
    }

    public void setMerchantTitle(String merchantTitle) {
        this.merchantTitle = merchantTitle;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantShortCode() {
        return merchantShortCode;
    }

    public void setMerchantShortCode(String merchantShortCode) {
        this.merchantShortCode = merchantShortCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
