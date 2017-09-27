package com.tecsol.batua.user.data.model.User;

import com.google.gson.annotations.SerializedName;
import com.tecsol.batua.user.data.model.BaseModel;
import com.tecsol.batua.user.data.model.Merchant.Discount;
import com.tecsol.batua.user.data.model.Merchant.Merchant;

import org.parceler.Parcel;

/**
 * @author Arnold Laishram.
 */
@Parcel
public class TransactionResponse extends BaseModel {

    @SerializedName("id")
    private Integer id;

    @SerializedName("initialAmount")
    private Float initialAmount;

    @SerializedName("reducedAmount")
    private Double reducedAmount;

    @SerializedName("paidAmount")
    private Double paidAmount;

    @SerializedName("isConfirmed")
    private Boolean isConfirmed;

    @SerializedName("isCancelled")
    private Boolean isCancelled;

    @SerializedName("type")
    private String type;

    @SerializedName("canccellationDate")
    private String canccellationDate;

    @SerializedName("cancellationDescription")
    private String cancellationDescription;

    @SerializedName("userId")
    private Integer userId;

    @SerializedName("promocodeId")
    private Integer promocodeId;

    @SerializedName("offerDiscountId")
    private Integer offerDiscountId;

    @SerializedName("merchantId")
    private Integer merchantId;

    @SerializedName("paymentModeId")
    private Integer paymentModeId;

    @SerializedName("transactionDetailId")
    private Integer transactionDetailId;

    @SerializedName("merchant")
    private Merchant merchant;

    @SerializedName("promocode")
    private Discount promocode;

    @SerializedName("offerDiscount")
    private Discount offerDiscount;

    @SerializedName("transactionDetail")
    private Transaction transactionDetail;

    @SerializedName("paymentMode")
    private PaymentMode paymentMode;

    @SerializedName("promocodeAmount")
    private Float promocodeAmount;

    @SerializedName("batuaCommission")
    private Float batuaCommission;

    @SerializedName("merchantFee")
    private Float merchantFee;

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
     * The initialAmount
     */
    public Float getInitialAmount() {
        return initialAmount;
    }

    /**
     *
     * @param initialAmount
     * The initialAmount
     */
    public void setInitialAmount(Float initialAmount) {
        this.initialAmount = initialAmount;
    }

    /**
     *
     * @return
     * The reducedAmount
     */
    public Double getReducedAmount() {
        return reducedAmount;
    }

    /**
     *
     * @param reducedAmount
     * The reducedAmount
     */
    public void setReducedAmount(Double reducedAmount) {
        this.reducedAmount = reducedAmount;
    }

    /**
     *
     * @return
     * The paidAmount
     */
    public Double getPaidAmount() {
        return paidAmount;
    }

    /**
     *
     * @param paidAmount
     * The paidAmount
     */
    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     *
     * @return
     * The isConfirmed
     */
    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    /**
     *
     * @param isConfirmed
     * The isConfirmed
     */
    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    /**
     *
     * @return
     * The isCancelled
     */
    public Boolean getIsCancelled() {
        return isCancelled;
    }

    /**
     *
     * @param isCancelled
     * The isCancelled
     */
    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The canccellationDate
     */
    public String getCanccellationDate() {
        return canccellationDate;
    }

    /**
     *
     * @param canccellationDate
     * The canccellationDate
     */
    public void setCanccellationDate(String canccellationDate) {
        this.canccellationDate = canccellationDate;
    }

    /**
     *
     * @return
     * The cancellationDescription
     */
    public String getCancellationDescription() {
        return cancellationDescription;
    }

    /**
     *
     * @param cancellationDescription
     * The cancellationDescription
     */
    public void setCancellationDescription(String cancellationDescription) {
        this.cancellationDescription = cancellationDescription;
    }

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The promocodeId
     */
    public Integer getPromocodeId() {
        return promocodeId;
    }

    /**
     *
     * @param promocodeId
     * The promocodeId
     */
    public void setPromocodeId(Integer promocodeId) {
        this.promocodeId = promocodeId;
    }

    /**
     *
     * @return
     * The offerDiscountId
     */
    public Integer getOfferDiscountId() {
        return offerDiscountId;
    }

    /**
     *
     * @param offerDiscountId
     * The offerDiscountId
     */
    public void setOfferDiscountId(Integer offerDiscountId) {
        this.offerDiscountId = offerDiscountId;
    }

    /**
     *
     * @return
     * The merchantId
     */
    public Integer getMerchantId() {
        return merchantId;
    }

    /**
     *
     * @param merchantId
     * The merchantId
     */
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    /**
     *
     * @return
     * The paymentModeId
     */
    public Integer getPaymentModeId() {
        return paymentModeId;
    }

    /**
     *
     * @param paymentModeId
     * The paymentModeId
     */
    public void setPaymentModeId(Integer paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

    /**
     *
     * @return
     * The transactionDetailId
     */
    public Integer getTransactionDetailId() {
        return transactionDetailId;
    }

    /**
     *
     * @param transactionDetailId
     * The transactionDetailId
     */
    public void setTransactionDetailId(Integer transactionDetailId) {
        this.transactionDetailId = transactionDetailId;
    }

    /**
     *
     * @return
     * The merchant
     */
    public Merchant getMerchant() {
        return merchant;
    }

    /**
     *
     * @param merchant
     * The merchant
     */
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
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

    /**
     *
     * @return
     * The offerDiscount
     */
    public Discount getOfferDiscount() {
        return offerDiscount;
    }

    /**
     *
     * @param offerDiscount
     * The offerDiscount
     */
    public void setOfferDiscount(Discount offerDiscount) {
        this.offerDiscount = offerDiscount;
    }

    /**
     *
     * @return
     * The transactionDetail
     */
    public Transaction getTransactionDetail() {
        return transactionDetail;
    }

    /**
     *
     * @param transactionDetail
     * The transactionDetail
     */
    public void setTransactionDetail(Transaction transactionDetail) {
        this.transactionDetail = transactionDetail;
    }

    /**
     *
     * @return
     * The paymentMode
     */
    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    /**
     *
     * @param paymentMode
     * The paymentMode
     */
    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     *
     * @return
     * The promocodeAmount
     */
    public Float getPromocodeAmount() {
        return promocodeAmount;
    }

    /**
     *
     * @param promocodeAmount
     * The promocodeAmount
     */
    public void setPromocodeAmount(Float promocodeAmount) {
        this.promocodeAmount = promocodeAmount;
    }

    /**
     *
     * @return
     * The batuaCommission
     */
    public Float getBatuaCommission() {
        return batuaCommission;
    }

    /**
     *
     * @param batuaCommission
     * The batuaCommission
     */
    public void setBatuaCommission(Float batuaCommission) {
        this.batuaCommission = batuaCommission;
    }

    /**
     *
     * @return
     * The merchantFee
     */
    public Float getMerchantFee() {
        return merchantFee;
    }

    /**
     *
     * @param merchantFee
     * The merchantFee
     */
    public void setMerchantFee(Float merchantFee) {
        this.merchantFee = merchantFee;
    }

}
