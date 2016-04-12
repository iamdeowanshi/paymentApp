package com.batua.android.merchant.data.model.Merchant;

import com.batua.android.merchant.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aaditya Deowanshi.
 */
@Parcel
public class Merchant extends BaseModel {

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
    @SerializedName("phone")
    @Expose
    private BigInteger phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String  address;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("averageRating")
    @Expose
    private float averageRating;
    @SerializedName("fees")
    @Expose
    private Integer fees;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("branchName")
    @Expose
    private String branchName;
    @SerializedName("accountHolder")
    @Expose
    private String accountHolder;
    @SerializedName("accountNumber")
    @Expose
    private BigInteger accountNumber;
    @SerializedName("ifscCode")
    @Expose
    private String ifscCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("locationId")
    @Expose
    private Integer locationId;
    @SerializedName("createdSalesId")
    @Expose
    private Integer createdSalesId;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("users")
    @Expose
    private User User;
    @SerializedName("galleries")
    @Expose
    private List<Gallery> galleries = new ArrayList<Gallery>();
    @SerializedName("categories")
    @Expose
    private Category Category;
    @SerializedName("locations")
    @Expose
    private Location Location;

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
     * The phone
     */
    public BigInteger getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(BigInteger phone) {
        this.phone = phone;
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
     * The latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The averageRating
     */
    public float getAverageRating() {
        return averageRating;
    }

    /**
     *
     * @param averageRating
     * The averageRating
     */
    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    /**
     *
     * @return
     * The fees
     */
    public Integer getFees() {
        return fees;
    }

    /**
     *
     * @param fees
     * The fees
     */
    public void setFees(Integer fees) {
        this.fees = fees;
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
     * The branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     *
     * @param branchName
     * The branchName
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
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
    public BigInteger getAccountNumber() {
        return accountNumber;
    }

    /**
     *
     * @param accountNumber
     * The accountNumber
     */
    public void setAccountNumber(BigInteger accountNumber) {
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
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The locationId
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     *
     * @param locationId
     * The locationId
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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
     * The User
     */
    public User getUser() {
        return User;
    }

    /**
     *
     * @param User
     * The User
     */
    public void setUser(User User) {
        this.User = User;
    }

    /**
     *
     * @return
     * The galleries
     */
    public List<Gallery> getGalleries() {
        return galleries;
    }

    /**
     *
     * @param Galleries
     * The galleries
     */
    public void setGalleries(List<Gallery> Galleries) {
        this.galleries = Galleries;
    }

    /**
     *
     * @return
     * The Category
     */
    public Category getCategory() {
        return Category;
    }

    /**
     *
     * @param Category
     * The Category
     */
    public void setCategory(Category Category) {
        this.Category = Category;
    }

    /**
     *
     * @return
     * The Location
     */
    public Location getLocation() {
        return Location;
    }

    /**
     *
     * @param Location
     * The Location
     */
    public void setLocation(Location Location) {
        this.Location = Location;
    }

}
