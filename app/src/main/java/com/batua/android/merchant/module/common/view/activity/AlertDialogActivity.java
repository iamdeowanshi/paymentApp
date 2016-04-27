package com.batua.android.merchant.module.common.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.batua.android.merchant.module.base.BaseActivity;

/**
 * Created by febinp on 17/11/15.
 */
public class AlertDialogActivity extends BaseActivity {

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            showDialog("NO Internet","Please Check your internet Connection");
        }

        public void showDialog(String title, String mgs){

        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);

        alertbuilder.setTitle(title)
                .setMessage(mgs)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertbuilder.create();
        alert.show();

    }
}
