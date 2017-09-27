package com.tecsol.batua.user.module.common.util.social;

/**
 * @author Aaditya Deowanshi.
 */

public interface SocialAuthCallback {

    void onSocialConnectionSuccess(AuthResult result);

    void onCancel();

    void onError(Throwable throwable);

}
