package com.batua.android.merchant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.LeaderBoardModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Arnold Laishram.
 */
public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder>{

    private List<LeaderBoardModel> leaderBoardModelList;

    public LeaderBoardAdapter(List<LeaderBoardModel> leaderBoardModelList) {
        this.leaderBoardModelList = leaderBoardModelList;
    }

    @Override
    public LeaderBoardViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_leaderboard, viewGroup, false);
        LeaderBoardViewHolder leaderBoardViewHolder = new LeaderBoardViewHolder(v);

        return leaderBoardViewHolder;

    }

    @Override
    public void onBindViewHolder(LeaderBoardViewHolder leaderBoardViewHolder, int position) {
        LeaderBoardModel leaderBoardModel = leaderBoardModelList.get(position);
        leaderBoardViewHolder.txtMerchantName.setText(leaderBoardModel.getMerchantName());
        leaderBoardViewHolder.txtMerchantPoint.setText(leaderBoardModel.getPoints()+"");
    }

    @Override
    public int getItemCount() {
        return leaderBoardModelList.size();
    }


    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_merchant_name) TextView txtMerchantName;
        @Bind(R.id.txt_merchant_point) TextView txtMerchantPoint;

        public LeaderBoardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
