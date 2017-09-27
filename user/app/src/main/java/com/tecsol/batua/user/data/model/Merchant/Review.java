package com.tecsol.batua.user.data.model.Merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tecsol.batua.user.data.model.BaseModel;
import com.tecsol.batua.user.data.model.User.User;

/**
 * @author Arnold Laishram.
 */
public class Review extends BaseModel {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("userId")
    @Expose
    private User userId;
    @SerializedName("merchantId")
    @Expose
    private Merchant merchantId;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * @param rating The rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * @return The review
     */
    public String getReview() {
        return review;
    }

    /**
     * @param review The review
     */
    public void setReview(String review) {
        this.review = review;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Merchant getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Merchant merchantId) {
        this.merchantId = merchantId;
    }

}
