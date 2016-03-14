package com.batua.android.user.data.model;

/**
 * @author Arnold Laishram.
 */
public class MerchantDetail {

    private String imageUrl;
    private String merchantTitle;
    private Float ratingReview;
    private Integer reviewedNum;
    private String merchantAddress;
    private String distance;

    public MerchantDetail(String merchantTitle, Float ratingReview, Integer reviewedNum, String merchantAddress, String distance) {
        this.merchantTitle = merchantTitle;
        this.ratingReview = ratingReview;
        this.reviewedNum = reviewedNum;
        this.merchantAddress = merchantAddress;
        this.distance = distance;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMerchantTitle() {
        return merchantTitle;
    }

    public void setMerchantTitle(String merchantTitle) {
        this.merchantTitle = merchantTitle;
    }

    public Float getRatingReview() {
        return ratingReview;
    }

    public void setRatingReview(Float ratingReview) {
        this.ratingReview = ratingReview;
    }

    public Integer getReviewedNum() {
        return reviewedNum;
    }

    public void setReviewedNum(Integer reviewedNum) {
        this.reviewedNum = reviewedNum;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
