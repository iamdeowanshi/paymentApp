package com.tecsol.batua.user.module.common.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.tecsol.batua.user.injection.Injector;

import javax.inject.Inject;

/**
 * @author Farhan Ali
 */
public class Bakery {

    @Inject
    Context context;

    public Bakery() {
        Injector.component().inject(this);
    }

    /**
     * Shows a LENGTH_SHORT toast message.
     *
     * @param message
     */
    public void toastShort(String message) {
        toast(message, Toast.LENGTH_SHORT);
    }

    /**
     * Shows a LENGTH_LONG toast message.
     *
     * @param message
     */
    public void toastLong(String message) {
        toast(message, Toast.LENGTH_LONG);
    }

    /**
     * Shows a toast message.
     *
     * @param message
     * @param length
     */
    public void toast(String message, int length) {
        Toast.makeText(context, message, length).show();
    }

    /**
     * Shows a LENGTH_SHORT snack message.
     *
     * @param view
     * @param message
     */
    public void snackShort(View view, String message) {
        snack(view, message, Snackbar.LENGTH_SHORT);
    }

    /**
     * Shows a LENGTH_LONG snack message.
     *
     * @param view
     * @param message
     */
    public void snackLong(View view, String message) {
        snack(view, message, Snackbar.LENGTH_LONG);
    }

    /**
     * Shows a LENGTH_INDEFINITE snack message.
     *
     * @param view
     * @param message
     */
    public void snackIndefinite(View view, String message) {
        snack(view, message, Snackbar.LENGTH_INDEFINITE);
    }

    /**
     * Shows a snack message.
     *
     * @param view
     * @param message
     */
    public void snack(View view, String message, int length) {
        Snackbar.make(view, message, length).show();
    }

    /**
     * Shows a  snack message with action.
     * @param view
     * @param message
     * @param length
     * @param action
     * @param actionListener
     */
    public void snack(View view, String message, int length, String action, View.OnClickListener actionListener) {
        Snackbar.make(view, message, length).setAction(action, actionListener).show();
    }

}
