package com.batua.android.merchant.module.merchant.util;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by febinp on 03/03/16.
 */
public class SpinnerLoad {

    public static void loadSpinner(Context context, int arrayList, Spinner spinner) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, arrayList, com.batua.android.merchant.R.layout.custom_spinner_hint);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }
}
