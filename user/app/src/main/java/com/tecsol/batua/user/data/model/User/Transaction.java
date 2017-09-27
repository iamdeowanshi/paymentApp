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

    @SerializedName("bankName")
    private String bankName;
    @SerializedName("orderNumber")
    private String orderNumber;
    @SerializedName("transactionId")
    private String transactionId;
    @SerializedName("mode")
    private String mode;


    @SerializedName("additionalCharges")
    private Float additionalCharges;
    @SerializedName("netAmountDebited")
    private String netAmountDebited;
    @SerializedName("bankReferenceNumber")
    private String bankReferenceNumber;
    @SerializedName("cardType")
    private String cardType;
    @SerializedName("cardNumber")
    private String cardNumber;
    @SerializedName("expiryDate")
    private String expiryDate;

    @SerializedName("createdAt")
    private String createdAt;

    /**
     *
     * @return
     * The bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     *
     * @param bankName
     * The bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     *
     * @return
     * The orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     *
     * @param orderNumber
     * The orderNumber
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     *
     * @return
     * The transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     *
     * @param transactionId
     * The transactionId
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     *
     * @return
     * The mode
     */
    public Object getMode() {
        return mode;
    }

    /**
     *
     * @param mode
     * The mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     *
     * @return
     * The additionalCharges
     */
    public Float getAdditionalCharges() {
        return additionalCharges;
    }

    /**
     *
     * @param additionalCharges
     * The additionalCharges
     */
    public void setAdditionalCharges(Float additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    /**
     *
     * @return
     * The netAmountDebited
     */
    public Object getNetAmountDebited() {
        return netAmountDebited;
    }

    /**
     *
     * @param netAmountDebited
     * The netAmountDebited
     */
    public void setNetAmountDebited(String netAmountDebited) {
        this.netAmountDebited = netAmountDebited;
    }

    /**
     *
     * @return
     * The bankReferenceNumber
     */
    public Object getBankReferenceNumber() {
        return bankReferenceNumber;
    }

    /**
     *
     * @param bankReferenceNumber
     * The bankReferenceNumber
     */
    public void setBankReferenceNumber(String bankReferenceNumber) {
        this.bankReferenceNumber = bankReferenceNumber;
    }

    /**
     *
     * @return
     * The cardType
     */
    public Object getCardType() {
        return cardType;
    }

    /**
     *
     * @param cardType
     * The cardType
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     *
     * @return
     * The cardNumber
     */
    public Object getCardNumber() {
        return cardNumber;
    }

    /**
     *
     * @param cardNumber
     * The cardNumber
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     *
     * @return
     * The expiryDate
     */
    public Object getExpiryDate() {
        return expiryDate;
    }

    /**
     *
     * @param expiryDate
     * The expiryDate
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }


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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
