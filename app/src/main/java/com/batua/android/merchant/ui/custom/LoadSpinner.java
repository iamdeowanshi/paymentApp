package com.batua.android.merchant.ui.custom;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.batua.android.merchant.R;

/**
 * Created by febinp on 03/03/16.
 */
public class LoadSpinner {

    public static void loadSpinner(Context context, int arrayList, Spinner spinner) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, arrayList, R.layout.custom_spinner_hint);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }
}
