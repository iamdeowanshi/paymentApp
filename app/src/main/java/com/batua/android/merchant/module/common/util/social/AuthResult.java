package com.batua.android.merchant.module.common.util.social;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Aaditya Deowanshi
 *
 *         Class which saves data received from social login to auth user model class.
 */

public class AuthResult {

    private AuthUser authUser;
    private String data;
    private SocialAuth.SocialType authType;

    public AuthResult(String data, SocialAuth.SocialType authType) {
        this.data = data;
        this.authType = authType;
        loadUserData();
    }

    public AuthResult(GoogleSignInAccount account) {
        this.authType = SocialAuth.SocialType.GOOGLE;
        authUser = new AuthUser();
        authUser.setSocialId(account.getId());
        authUser.setEmail(account.getEmail());
        authUser.setFirstName(account.getDisplayName().split(" ")[0]);
        authUser.setLastName(account.getDisplayName().split(" ")[1]);
        authUser.setProfilePic(String.valueOf(account.getPhotoUrl()));
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public String getData() {
        return data;
    }

    public SocialAuth.SocialType getAuthType() {
        return authType;
    }

    private void loadUserData() {
        switch (authType) {
            case GOOGLE:
                loadGoogleUserData();
                break;
            case FACEBOOK:
                break;
        }
    }

    /**
     * Method to load google data.
     */
    private void loadGoogleUserData() {
        try {
            JSONObject jsonObject = new JSONObject(data);
            authUser = new AuthUser();
            authUser.setSocialId(jsonObject.getString("id"));
            loadGoogleName(authUser, jsonObject.getJSONObject("name"));
            authUser.setProfilePic(getGoogleProfileUrl(jsonObject.getJSONObject("image")) + "0");
            authUser.setGender(jsonObject.getString("gender"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get google profile picture.
     *
     * @param jsonObject
     * @return
     */
    private String getGoogleProfileUrl(JSONObject jsonObject) {
        try {
            return jsonObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Method to get name through google.
     *
     * @param authUser
     * @param jsonObject
     */
    private void loadGoogleName(AuthUser authUser, JSONObject jsonObject) {
        try {
            authUser.setFirstName(jsonObject.getString("givenName"));
            authUser.setLastName(jsonObject.getString("familyName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
