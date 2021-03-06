package com.batua.android.merchant.module.dashboard.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.module.merchant.view.activity.MerchantDetailsActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MerchantStatusAdapter extends RecyclerView.Adapter<MerchantStatusAdapter.MerchantStatusViewHolder> {

    private List<Merchant> merchantList;
    private Context context;

    private MerchantClickListener merchantClickListener;

    private static String ACTIVE_STATUS="Active";
    private static String PENDING_STATUS="Pending for approval";
    private static String DRAFTED_STATUS="Drafted";

    public MerchantStatusAdapter(List<Merchant> merchantList) {
        this.merchantList = merchantList;
    }

    @Override
    public MerchantStatusViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        this.context = viewGroup.getContext();

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(com.batua.android.merchant.R.layout.list_merchant_status, viewGroup, false);
        MerchantStatusViewHolder merchantStatusViewHolder = new MerchantStatusViewHolder(v);
        return merchantStatusViewHolder;

    }

    @Override
    public void onBindViewHolder(final MerchantStatusViewHolder viewHolder, final int position) {
        final Merchant merchant = merchantList.get(position);

        viewHolder.txtMerchantTitle.setText(merchant.getName());
        viewHolder.txtMerchantAddress.setText(merchant.getAddress());
        viewHolder.txtMerchantShortCode.setText(merchant.getShortCode());
        if (merchant.getStatus().equals(ACTIVE_STATUS)) {
            viewHolder.view.setBackgroundColor(ContextCompat.getColor(context, com.batua.android.merchant.R.color.green));
        }else if (merchant.getStatus().equals(PENDING_STATUS)) {
            viewHolder.view.setBackgroundColor(ContextCompat.getColor(context, com.batua.android.merchant.R.color.yellow_dark));
        } else if (merchant.getStatus().equals(DRAFTED_STATUS)){
            viewHolder.view.setBackgroundColor(ContextCompat.getColor(context, com.batua.android.merchant.R.color.red_selected));
        }

        viewHolder.itemView.setOnClickListener(new MerchantClickListener(merchant));
    }

    @Override
    public int getItemCount() {
        return merchantList.size();
    }

    public void setItemClickListener(MerchantClickListener merchantClickListener) {
        this.merchantClickListener = merchantClickListener;
    }


    public class MerchantStatusViewHolder extends RecyclerView.ViewHolder {
        @Bind(com.batua.android.merchant.R.id.txt_merchant_title) TextView txtMerchantTitle;
        @Bind(com.batua.android.merchant.R.id.txt_merchant_address) TextView txtMerchantAddress;
        @Bind(com.batua.android.merchant.R.id.txt_merchant_short_code) TextView txtMerchantShortCode;
        @Bind(com.batua.android.merchant.R.id.highlight_view) View view;

        public MerchantStatusViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class MerchantClickListener implements View.OnClickListener{

        private Merchant merchant;

        public MerchantClickListener(Merchant merchant) {
            this.merchant = merchant;
        }

        @Override
        public void onClick(View v) {

            Intent i = new Intent(context, MerchantDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("MerchantDetail", Parcels.wrap(merchant));
            i.putExtras(bundle);
            context.startActivity(i);
        }
    }

}


