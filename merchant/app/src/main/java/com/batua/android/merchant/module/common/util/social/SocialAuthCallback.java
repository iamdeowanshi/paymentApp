package com.batua.android.merchant.module.common.util.social;

/**
 * @author Aaditya Deowanshi.
 */

public interface SocialAuthCallback {

    void onSuccess(AuthResult result);

    void onCancel();

    void onError(Throwable throwable);

}
