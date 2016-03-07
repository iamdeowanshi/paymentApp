package com.batua.android.ui.custom;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.batua.android.data.model.MerchantStatusModel;
import com.batua.android.ui.adapter.MerchantStatusAdapter;

import java.util.List;

/**
 * Created by febinp on 03/03/16.
 */
public class PopulateMerchantStatusAdapter {

    public static void populateAdapter(Context context, List<MerchantStatusModel> merchantStatusModelList, RecyclerView merchantStatusRecyclerView){

        MerchantStatusAdapter merchantStatusAdapter = new MerchantStatusAdapter(merchantStatusModelList);
        LinearLayoutManager llayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        merchantStatusRecyclerView.setLayoutManager(llayout);
        merchantStatusRecyclerView.setAdapter(merchantStatusAdapter);
    }
}
