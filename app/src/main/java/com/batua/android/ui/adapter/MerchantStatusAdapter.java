package com.batua.android.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.batua.android.R;
import com.batua.android.data.model.MerchantStatusModel;
import com.batua.android.ui.activity.MerchantDetailsActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MerchantStatusAdapter extends RecyclerView.Adapter<MerchantStatusAdapter.MerchantStatusViewHolder> {

    private List<MerchantStatusModel> merchantStatusList;
    private Context context;

    private MerchantClickListener merchantClickListener;

    private static String ACTIVE_STATUS="Active";
    private static String PENDING_STATUS="Pending";
    private static String DRAFTED_STATUS="Drafted";

    public MerchantStatusAdapter(List<MerchantStatusModel> merchantStatusList) {
        this.merchantStatusList = merchantStatusList;
    }

    @Override
    public MerchantStatusViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        this.context = viewGroup.getContext();

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_merchant_status, viewGroup, false);
        MerchantStatusViewHolder merchantStatusViewHolder = new MerchantStatusViewHolder(v);
        return merchantStatusViewHolder;

    }

    @Override
    public void onBindViewHolder(final MerchantStatusViewHolder viewHolder, final int position) {
        final MerchantStatusModel merchantStatusModel = merchantStatusList.get(position);

        viewHolder.txtMerchantTitle.setText(merchantStatusModel.getMerchantTitle());
        viewHolder.txtMerchantAddress.setText(merchantStatusModel.getMerchantAddress());
        viewHolder.txtMerchantShortCode.setText(merchantStatusModel.getMerchantShortCode());
        if (merchantStatusModel.getStatus().equals(ACTIVE_STATUS)) {
            viewHolder.view.setBackgroundColor(context.getResources().getColor(R.color.green));
        }else if (merchantStatusModel.getStatus().equals(PENDING_STATUS)) {
            viewHolder.view.setBackgroundColor(context.getResources().getColor(R.color.yellow_dark));
        } else if (merchantStatusModel.getStatus().equals(DRAFTED_STATUS)){
            viewHolder.view.setBackgroundColor(context.getResources().getColor(R.color.red_selected));
        }

        viewHolder.itemView.setOnClickListener(new MerchantClickListener(position, merchantStatusModel.getStatus()));
    }

    @Override
    public int getItemCount() {
        return merchantStatusList.size();
    }

    public void setItemClickListener(MerchantClickListener merchantClickListener) {
        this.merchantClickListener = merchantClickListener;
    }


    public class MerchantStatusViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_merchant_title) TextView txtMerchantTitle;
        @Bind(R.id.txt_merchant_address) TextView txtMerchantAddress;
        @Bind(R.id.txt_merchant_short_code) TextView txtMerchantShortCode;
        @Bind(R.id.highlight_view) View view;

        public MerchantStatusViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class MerchantClickListener implements View.OnClickListener{

        private int position;
        private String status;

        public MerchantClickListener(int position, String status) {
            this.status = status;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Timber.d(position + " " + status);
            Intent i = new Intent(context, MerchantDetailsActivity.class);
            i.putExtra("position", position);
            context.startActivity(i);
        }
    }

}


