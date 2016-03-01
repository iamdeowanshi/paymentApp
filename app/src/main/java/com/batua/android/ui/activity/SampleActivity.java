package com.batua.android.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;
import com.batua.android.presenter.SamplePresenter;
import com.batua.android.presenter.SampleViewInteractor;
import com.batua.android.presenter.concrete.SamplePresenterImpl;
import com.batua.android.util.Bakery;
import com.batua.android.util.PreferenceUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class SampleActivity extends BaseActivity implements SampleViewInteractor {

    @Inject SamplePresenter presenter;

    @Inject Bakery bakery;
    @Inject PreferenceUtil preference;


    @Bind(R.id.btn_do_something) Button btnDoSomething;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        // call to inject dependencies
        injectDependencies();

        presenter.setViewInteractor(this);
    }

    @OnClick(R.id.btn_do_something)
    void doSomething() {
        presenter.createBox();
    }

    @Override
    public void showSomeMessage(String message) {
        bakery.toastShort(message);
    }

    @Override
    public void boxCreated() {
        SamplePresenterImpl.Box box = (SamplePresenterImpl.Box) preference.read("box", SamplePresenterImpl.Box.class);
        bakery.snackShort(getContentView(), box.toString() + " Created");
    }

}
