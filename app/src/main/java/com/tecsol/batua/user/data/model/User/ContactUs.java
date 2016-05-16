package com.tecsol.batua.user.data.model.User;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arnold on 11/5/16.
 */
public class ContactUs {

    @SerializedName("email")
    private String email;

    @SerializedName("query")
    private String query;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
