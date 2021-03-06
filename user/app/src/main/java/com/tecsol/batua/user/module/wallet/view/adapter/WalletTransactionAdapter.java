package com.tecsol.batua.user.module.wallet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tecsol.batua.user.data.model.User.WalletTransaction;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Arnold Laishram.
 */
public class WalletTransactionAdapter extends RecyclerView.Adapter<WalletTransactionAdapter.WalletTransactionViewHolder> {

    private List<WalletTransaction> walletTransactionList;

    public WalletTransactionAdapter(List<WalletTransaction> walletTransactionList) {
        this.walletTransactionList = walletTransactionList;
    }

    @Override
    public WalletTransactionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(com.batua.android.user.R.layout.list_payments_made, viewGroup, false);
        WalletTransactionViewHolder walletTransactionViewHolder = new WalletTransactionViewHolder(v);

        return walletTransactionViewHolder;
    }

    @Override
    public void onBindViewHolder(WalletTransactionViewHolder walletTransactionViewHolder, int position) {
        WalletTransaction walletTransaction = walletTransactionList.get(position);
        walletTransactionViewHolder.txtAmountPaid.setText(walletTransaction.getAmount());
        walletTransactionViewHolder.txtPaymentDate.setText(walletTransaction.getDate());
        walletTransactionViewHolder.txtOrderNumber.setText(walletTransaction.getOrderNumber());
        walletTransactionViewHolder.txtCardType.setText(walletTransaction.getCardType());
        if (walletTransaction.getCashBackAgainst() != null) {
            walletTransactionViewHolder.cashBackLayout.setVisibility(View.VISIBLE);
            walletTransactionViewHolder.txtCashBackAgainst.setText(walletTransaction.getCashBackAgainst());

            return;
        }
        walletTransactionViewHolder.cashBackLayout.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return walletTransactionList.size();
    }


    public class WalletTransactionViewHolder extends RecyclerView.ViewHolder {
        @Bind(com.batua.android.user.R.id.txt_amount_paid) TextView txtAmountPaid;
        @Bind(com.batua.android.user.R.id.txt_payment_date) TextView txtPaymentDate;
        @Bind(com.batua.android.user.R.id.txt_order_num) TextView txtOrderNumber;
        @Bind(com.batua.android.user.R.id.txt_card_type) TextView txtCardType;
        @Bind(com.batua.android.user.R.id.cash_back_layout) RelativeLayout cashBackLayout;
        @Bind(com.batua.android.user.R.id.txt_cash_back_against) TextView txtCashBackAgainst;

        public WalletTransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
