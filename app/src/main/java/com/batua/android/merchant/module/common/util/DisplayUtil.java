package com.batua.android.merchant.module.common.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.batua.android.merchant.injection.Injector;

import javax.inject.Inject;

/**
 * @author Farhan Ali
 */
public class DisplayUtil {

    @Inject Context context;

    public DisplayUtil() {
        Injector.component().inject(this);
    }

    public int getWidth() {
        return getSize().x;
    }

    public int getHeight() {
        return getSize().y;
    }

    public Point getSize() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }

}
