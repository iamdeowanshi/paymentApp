package com.tecsol.batua.user.module.dashboard.presenter;

import com.tecsol.batua.user.data.model.User.ContactUs;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface ContactUsPresenter extends Presenter<ContactUsViewInteractor> {

    void contactBatua(ContactUs contactUs);

}
