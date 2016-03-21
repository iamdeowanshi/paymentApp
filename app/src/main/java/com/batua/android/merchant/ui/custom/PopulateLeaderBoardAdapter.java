package com.batua.android.merchant.ui.custom;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.batua.android.merchant.data.model.LeaderBoardModel;
import com.batua.android.merchant.ui.adapter.LeaderBoardAdapter;

import java.util.List;

/**
 * @author Arnold Laishram.
 */
public class PopulateLeaderBoardAdapter {

    public static void populateAdapter(Context context, List<LeaderBoardModel> leaderBoardModelList, RecyclerView leaderBoardRecyclerView){

        LeaderBoardAdapter leaderBoardAdapter = new LeaderBoardAdapter(leaderBoardModelList);
        LinearLayoutManager llayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        leaderBoardRecyclerView.setLayoutManager(llayout);
        leaderBoardRecyclerView.setAdapter(leaderBoardAdapter);
    }
}
