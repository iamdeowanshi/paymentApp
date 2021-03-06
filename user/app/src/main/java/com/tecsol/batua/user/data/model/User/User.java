package com.tecsol.batua.user.data.model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tecsol.batua.user.data.model.BaseModel;

import org.parceler.Parcel;

/**
 * @author Aaditya Deowanshi.
 */
@Parcel
public class User extends BaseModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private long phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profileImageUrl")
    @Expose
    private String profileImageUrl;
    @SerializedName("token")
    private String accessToken;

    @SerializedName("userGroup")
    private String userGroup;

    @SerializedName("password")
    private String password;

    @SerializedName("updatedAt")
    private String date;

    @SerializedName("isPinActivated")
    private boolean isPinActivated;

    @SerializedName("isPinSet")
    private boolean isPinSet;

    @SerializedName("isPhoneVerified")
    private boolean isPhoneVerified;

    @SerializedName("userId")
    @Expose
    private Integer userId;

    public User() {
    }

    public User(long phone, String email, String password) {
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
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
     * The phone
     */
    public long getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(long phone) {
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPinActivated() {
        return isPinActivated;
    }

    public void setIsPinActivated(boolean isPinActivated) {
        this.isPinActivated = isPinActivated;
    }

    public boolean isPinSet() {
        return isPinSet;
    }

    public void setIsPinSet(boolean isPinSet) {
        this.isPinSet = isPinSet;
    }

    public boolean isPhoneVerified() {
        return isPhoneVerified;
    }

    public void setIsPhoneVerified(boolean isPhoneVerified) {
        this.isPhoneVerified = isPhoneVerified;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
