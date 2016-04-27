package com.batua.android.user.util.social;

/**
 * @author Aaditya Deowanshi.
 */

public interface SocialAuthCallback {

    void onSuccess(AuthResult result);

    void onCancel();

    void onError(Throwable throwable);

}
