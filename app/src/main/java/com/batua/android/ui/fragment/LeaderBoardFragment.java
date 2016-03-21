package com.batua.android.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.batua.android.app.base.BaseFragment;
import com.batua.android.data.model.LeaderBoardModel;
import com.batua.android.ui.adapter.LeaderBoardAdapter;

import java.util.List;

/**
 * @author Arnold Laishram.
 */
public abstract class LeaderBoardFragment extends BaseFragment {

    private LeaderBoardAdapter leaderBoardAdapter;

    abstract RecyclerView getLeaderBoardRecyclerView();

    abstract List<LeaderBoardModel> getLeaderBoardList();

    protected void populateAdapter() {
        leaderBoardAdapter = new LeaderBoardAdapter(getLeaderBoardList());
        LinearLayoutManager llayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        getLeaderBoardRecyclerView().setLayoutManager(llayout);
        getLeaderBoardRecyclerView().setAdapter(leaderBoardAdapter);
    }

    public LeaderBoardAdapter getLeaderBoardAdapter() {
        return leaderBoardAdapter;
    }
}
