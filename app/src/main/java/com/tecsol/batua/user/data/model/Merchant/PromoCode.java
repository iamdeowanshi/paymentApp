package com.tecsol.batua.user.data.model.Merchant;

import com.google.gson.annotations.SerializedName;
import com.tecsol.batua.user.data.model.BaseModel;

/**
 * Created by arnold on 11/5/16.
 */
public class PromoCode extends BaseModel{

    @SerializedName("id")
    private Integer id;

    @SerializedName("merchantId")
    private Integer merchantId;

    @SerializedName("promocode")
    private String promocode;

    @SerializedName("discountPercentage")
    private Double discountPercentage;

    @SerializedName("description")
    private String description;

    @SerializedName("maximumAmountLimit")
    private Integer maximumAmountLimit;

    @SerializedName("validFrom")
    private String validFrom;

    @SerializedName("validTo")
    private String validTo;

    @SerializedName("percentageCostBourneByBatua")
    private Integer percentageCostBourneByBatua;

    @SerializedName("percentageCostBourneByMerchant")
    private Integer percentageCostBourneByMerchant;

    @SerializedName("status")
    private String status;

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
     * The promocode
     */
    public String getPromocode() {
        return promocode;
    }

    /**
     *
     * @param promocode
     * The promocode
     */
    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    /**
     *
     * @return
     * The discountPercentage
     */
    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     *
     * @param discountPercentage
     * The discountPercentage
     */
    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The maximumAmountLimit
     */
    public Integer getMaximumAmountLimit() {
        return maximumAmountLimit;
    }

    /**
     *
     * @param maximumAmountLimit
     * The maximumAmountLimit
     */
    public void setMaximumAmountLimit(Integer maximumAmountLimit) {
        this.maximumAmountLimit = maximumAmountLimit;
    }

    /**
     *
     * @return
     * The validFrom
     */
    public String getValidFrom() {
        return validFrom;
    }

    /**
     *
     * @param validFrom
     * The validFrom
     */
    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    /**
     *
     * @return
     * The validTo
     */
    public String getValidTo() {
        return validTo;
    }

    /**
     *
     * @param validTo
     * The validTo
     */
    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    /**
     *
     * @return
     * The percentageCostBourneByBatua
     */
    public Integer getPercentageCostBourneByBatua() {
        return percentageCostBourneByBatua;
    }

    /**
     *
     * @param percentageCostBourneByBatua
     * The percentageCostBourneByBatua
     */
    public void setPercentageCostBourneByBatua(Integer percentageCostBourneByBatua) {
        this.percentageCostBourneByBatua = percentageCostBourneByBatua;
    }

    /**
     *
     * @return
     * The percentageCostBourneByMerchant
     */
    public Integer getPercentageCostBourneByMerchant() {
        return percentageCostBourneByMerchant;
    }

    /**
     *
     * @param percentageCostBourneByMerchant
     * The percentageCostBourneByMerchant
     */
    public void setPercentageCostBourneByMerchant(Integer percentageCostBourneByMerchant) {
        this.percentageCostBourneByMerchant = percentageCostBourneByMerchant;
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

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
}
