package com.tecsol.batua.user.data.model.Merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tecsol.batua.user.data.model.BaseModel;

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
