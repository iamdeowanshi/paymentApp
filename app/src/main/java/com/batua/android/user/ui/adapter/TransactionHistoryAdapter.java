package com.batua.android.user.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.batua.android.user.R;
import com.batua.android.user.data.model.TransactionHistory;
import com.batua.android.user.ui.activity.PaymentActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * @author Arnold Laishram.
 */
public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder> {

    private List<TransactionHistory> transactionHistoryList;
    private Activity activity;

    public TransactionHistoryAdapter(List<TransactionHistory> transactionHistoryList, Activity activity) {
        this.transactionHistoryList = transactionHistoryList;
        this.activity = activity;
    }

    @Override
    public TransactionHistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_transactions, viewGroup, false);
        TransactionHistoryViewHolder transactionHistoryViewHolder = new TransactionHistoryViewHolder(v);

        return transactionHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(final TransactionHistoryViewHolder transactionHistoryViewHolder, int position) {
        TransactionHistory walletTransaction = transactionHistoryList.get(position);

        transactionHistoryViewHolder.txtAmountPaid.setText(walletTransaction.getAmount());
        transactionHistoryViewHolder.txtPaymentDate.setText(walletTransaction.getDate());
        transactionHistoryViewHolder.txtOrderNumber.setText(walletTransaction.getOrderNumber());
        transactionHistoryViewHolder.txtCardType.setText(walletTransaction.getCardType());
        transactionHistoryViewHolder.txtTransactionRecievedAgainst.setText(walletTransaction.getTransactionAgainst());
        transactionHistoryViewHolder.txtTransactionNumber.setText(walletTransaction.getTransactionId());

        transactionHistoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transactionHistoryViewHolder.btnMakeAnotherPayment.getVisibility() == View.VISIBLE) {
                    transactionHistoryViewHolder.btnMakeAnotherPayment.setVisibility(View.GONE);

                    return;
                }
                transactionHistoryViewHolder.btnMakeAnotherPayment.setVisibility(View.VISIBLE);
            }
        });

        transactionHistoryViewHolder.btnMakeAnotherPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paymentIntent = new Intent(activity, PaymentActivity.class);
                activity.startActivity(paymentIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionHistoryList.size();
    }


    public class TransactionHistoryViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_amount_paid) TextView txtAmountPaid;
        @Bind(R.id.txt_payment_date) TextView txtPaymentDate;
        @Bind(R.id.txt_order_num) TextView txtOrderNumber;
        @Bind(R.id.txt_card_type) TextView txtCardType;
        @Bind(R.id.txt_transaction_num) TextView txtTransactionNumber;
        @Bind(R.id.txt_transaction_recieved_against) TextView txtTransactionRecievedAgainst;
        @Bind(R.id.btn_make_another_payment) Button btnMakeAnotherPayment;

        public TransactionHistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
