package com.batua.android.user.data.model;

/**
 * @author Aaditya Deowanshi.
 */
public class Review extends BaseModel {

    private String name;
    private String date;
    private String review;
    private int stars;

    public Review() {
    }

    public Review(int stars, String name, String date, String review) {
        this.stars = stars;
        this.name = name;
        this.date = date;
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
