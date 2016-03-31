package com.batua.android.merchant.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Aaditya Deowanshi.
 */
public class Merchant extends BaseModel {

    @SerializedName("name")
    private String name;
    @SerializedName("accountHolder")
    private String accountHolderName;
    @SerializedName("accountNumber")
    private int accountNumber;
    @SerializedName("ifscCode")
    private String ifscCode;
    @SerializedName("bankBranch")
    private String bankBranch;
    @SerializedName("bankName")
    private String bankName;
}
