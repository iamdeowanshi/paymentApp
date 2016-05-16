package com.tecsol.batua.user.data.model.User;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * @author Arnold Laishram.
 */
@Parcel
public class PaymentMode {

    @SerializedName("id")
    private Integer id;

    @SerializedName("paymentMode")
    private String paymentMode;

    @SerializedName("walletType")
    private String walletType;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The paymentMode
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     *
     * @param paymentMode
     * The paymentMode
     */
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     *
     * @return
     * The walletType
     */
    public String getWalletType() {
        return walletType;
    }

    /**
     *
     * @param walletType
     * The walletType
     */
    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

}
