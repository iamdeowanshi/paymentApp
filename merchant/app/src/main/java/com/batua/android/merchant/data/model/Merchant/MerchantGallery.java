package com.batua.android.merchant.data.model.Merchant;

import com.batua.android.merchant.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class MerchantGallery extends BaseModel {

@SerializedName("galleryId")
@Expose
public Integer galleryId;
@SerializedName("merchantId")
@Expose
public Integer merchantId;

/**
* 
* @return
* The galleryId
*/
public Integer getGalleryId() {
return galleryId;
}

/**
* 
* @param galleryId
* The galleryId
*/
public void setGalleryId(Integer galleryId) {
this.galleryId = galleryId;
}

/**
* 
* @return
* The merchantId
*/
public Integer getMerchantId() {
return merchantId;
}

/**
* 
* @param merchantId
* The merchantId
*/
public void setMerchantId(Integer merchantId) {
this.merchantId = merchantId;
}

}
