package com.batua.android.merchant.data.model.Merchant;

import com.batua.android.merchant.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aaditya Deowanshi.
 */
public class MerchantRequest extends BaseModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shortCode")
    @Expose
    private String shortCode;
    @SerializedName("profileImageUrl")
    @Expose
    private String profileImageUrl;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private Integer phone;
    @SerializedName("imageGallery")
    @Expose
    private List<String> imageGallery = new ArrayList<String>();
    @SerializedName("fees")
    @Expose
    private Integer fee;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pincode")
    @Expose
    private Integer pincode;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("accountHolder")
    @Expose
    private String accountHolder;
    @SerializedName("accountNumber")
    @Expose
    private Integer accountNumber;
    @SerializedName("ifscCode")
    @Expose
    private String ifscCode;
    @SerializedName("bankBranch")
    @Expose
    private String bankBranch;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdSalesId")
    @Expose
    private Integer createdSalesId;

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The shortCode
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     *
     * @param shortCode
     * The shortCode
     */
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    /**
     *
     * @return
     * The profileImageUrl
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     *
     * @param profileImageUrl
     * The profileImageUrl
     */
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The phone
     */
    public Integer getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The imageGallery
     */
    public List<String> getImageGallery() {
        return imageGallery;
    }

    /**
     *
     * @param imageGallery
     * The imageGallery
     */
    public void setImageGallery(List<String> imageGallery) {
        this.imageGallery = imageGallery;
    }

    /**
     *
     * @return
     * The fee
     */
    public Integer getFee() {
        return fee;
    }

    /**
     *
     * @param fee
     * The fee
     */
    public void setFee(Integer fee) {
        this.fee = fee;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The categoryId
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     *
     * @param cityId
     * The cityId
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The pincode
     */
    public Integer getPincode() {
        return pincode;
    }

    /**
     *
     * @param pincode
     * The pincode
     */
    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    /**
     *
     * @return
     * The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The accountHolder
     */
    public String getAccountHolder() {
        return accountHolder;
    }

    /**
     *
     * @param accountHolder
     * The accountHolder
     */
    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    /**
     *
     * @return
     * The accountNumber
     */
    public Integer getAccountNumber() {
        return accountNumber;
    }

    /**
     *
     * @param accountNumber
     * The accountNumber
     */
    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     *
     * @return
     * The ifscCode
     */
    public String getIfscCode() {
        return ifscCode;
    }

    /**
     *
     * @param ifscCode
     * The ifscCode
     */
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    /**
     *
     * @return
     * The bankBranch
     */
    public String getBankBranch() {
        return bankBranch;
    }

    /**
     *
     * @param bankBranch
     * The bankBranch
     */
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

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
     * The createdSalesId
     */
    public Integer getCreatedSalesId() {
        return createdSalesId;
    }

    /**
     *
     * @param createdSalesId
     * The createdSalesId
     */
    public void setCreatedSalesId(Integer createdSalesId) {
        this.createdSalesId = createdSalesId;
    }

}
