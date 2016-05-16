package com.tecsol.batua.user.module.dashboard.presenter;

import com.tecsol.batua.user.data.model.Merchant.Merchant;
import com.tecsol.batua.user.module.base.NetworkViewInteractor;

import java.util.List;

/**
 * @author Aaditya Deowanshi.
 */
public interface ContactUsViewInteractor extends NetworkViewInteractor {

    void onEmailSent(String message);

}
