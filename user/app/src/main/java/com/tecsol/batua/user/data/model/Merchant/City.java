package com.tecsol.batua.user.data.model.Merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tecsol.batua.user.data.model.BaseModel;

import org.parceler.Parcel;

/**
 * @author Aaditya Deowanshi.
 */
@Parcel
public class City extends BaseModel {

    @SerializedName("id")
    @Expose
    protected Integer id;
    @SerializedName("name")
    @Expose
    protected String name;
    @SerializedName("state")
    @Expose
    protected String state;
    @SerializedName("country")
    @Expose
    protected String country;

    public City() {}

    public City(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

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
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

}
