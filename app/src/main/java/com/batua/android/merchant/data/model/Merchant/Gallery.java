package com.batua.android.merchant.data.model.Merchant;

import com.batua.android.merchant.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * @author Aaditya Deowanshi.
 */
@Parcel
public class Gallery extends BaseModel {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("MerchantsGalleries")
    @Expose
    public MerchantGallery merchantGallery;
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
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
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

    public MerchantGallery getMerchantGallery() {
        return merchantGallery;
    }

    public void setMerchantGallery(MerchantGallery merchantGallery) {
        this.merchantGallery = merchantGallery;
    }

}
