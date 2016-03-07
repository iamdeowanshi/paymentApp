package com.batua.android.data.model;

/**
 * @author Arnold Laishram.
 */
public class LeaderBoardModel {

    private String merchantName;
    private Integer points;

    public LeaderBoardModel(String merchantName, Integer points) {
        this.merchantName = merchantName;
        this.points = points;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
