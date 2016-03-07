package com.batua.android.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.R;
import com.batua.android.app.base.BaseFragment;
import com.batua.android.data.model.LeaderBoardModel;
import com.batua.android.ui.custom.PopulateLeaderBoardAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author Arnold Laishram.
 */
public class MonthlyLeaderBoardFragment extends BaseFragment {

    private View view;
    private List<LeaderBoardModel> leaderBoardModelList = new ArrayList<LeaderBoardModel>();

    @Bind(R.id.leaderboard_monthly_recycler_view) RecyclerView leaderBoardMonthlyRecyclerView;

    public MonthlyLeaderBoardFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_monthly_leader, null);

        onViewCreated(view, null);

        leaderBoardModelList.add(new LeaderBoardModel("John", 10));
        leaderBoardModelList.add(new LeaderBoardModel("Wick",8));
        leaderBoardModelList.add(new LeaderBoardModel("Swazeneger",7));

        PopulateLeaderBoardAdapter.populateAdapter(getContext(), leaderBoardModelList, leaderBoardMonthlyRecyclerView);

        return view;
    }
}
