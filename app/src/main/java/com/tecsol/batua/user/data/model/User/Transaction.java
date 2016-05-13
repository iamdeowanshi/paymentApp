package com.tecsol.batua.user.data.model.User;

import com.google.gson.annotations.SerializedName;
import com.tecsol.batua.user.data.model.BaseModel;
import com.tecsol.batua.user.data.model.Merchant.Discount;

import org.parceler.Parcel;

/**
 * @author Arnold Laishram.
 */
@Parcel
public class Transaction extends BaseModel {

    @SerializedName("merchantId")
    private String merchantId;

    @SerializedName("paymentId")
    private String paymentId;

    @SerializedName("userId")
    private String userId;

    @SerializedName("amount")
    private String amount;

    @SerializedName("paymentmodeId")
    private Integer paymentmodeId;

    @SerializedName("status")
    private String status;

    @SerializedName("offer")
    private Discount offer;

    @SerializedName("promocode")
    private Discount promocode;

    /**
     *
     * @return
     * The merchantId
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     *
     * @param merchantId
     * The merchantId
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     *
     * @return
     * The paymentId
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     *
     * @param paymentId
     * The paymentId
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     * The paymentmodeId
     */
    public Integer getPaymentmodeId() {
        return paymentmodeId;
    }

    /**
     *
     * @param paymentmodeId
     * The paymentmodeId
     */
    public void setPaymentmodeId(Integer paymentmodeId) {
        this.paymentmodeId = paymentmodeId;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The offer
     */
    public Discount getOffer() {
        return offer;
    }

    /**
     *
     * @param offer
     * The offer
     */
    public void setOffer(Discount offer) {
        this.offer = offer;
    }

    /**
     *
     * @return
     * The promocode
     */
    public Discount getPromocode() {
        return promocode;
    }

    /**
     *
     * @param promocode
     * The promocode
     */
    public void setPromocode(Discount promocode) {
        this.promocode = promocode;
    }

}
