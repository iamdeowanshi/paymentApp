package com.batua.android.presenter.concrete;

import com.batua.android.app.base.BasePresenter;
import com.batua.android.presenter.SamplePresenter;
import com.batua.android.presenter.SampleViewInteractor;
import com.batua.android.util.PreferenceUtil;

import javax.inject.Inject;

/**
 * @author Farhan Ali
 */
public class SamplePresenterImpl extends BasePresenter<SampleViewInteractor>
        implements SamplePresenter {

    @Inject PreferenceUtil preference;

    public SamplePresenterImpl() {
        injectDependencies();
    }

    @Override
    public void createBox() {
        viewInteractor.showSomeMessage("Creating box..");
        preference.save("box", new Box(10, 12));
        viewInteractor.boxCreated();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public static class Box {
        int width;
        int length;

        public Box(int width, int length) {
            this.width = width;
            this.length = length;
        }

        @Override
        public String toString() {
            return String.format("Box (%d, %d)", width, length);
        }
    }

}
