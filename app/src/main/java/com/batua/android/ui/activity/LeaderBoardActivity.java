package com.batua.android.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;
import com.batua.android.ui.adapter.LeaderBoardFragmentPagerAdapter;

import butterknife.Bind;

/**
 * @author Arnold Laishram.
 */
public class LeaderBoardActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.leaderboard_tab_layout) TabLayout leaderBoardTabLayout;
    @Bind(R.id.leaderboard_viewpager) ViewPager leaderBoardViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        setToolBar();

        loadFragments();

    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadFragments() {
        leaderBoardViewPager.setAdapter(new LeaderBoardFragmentPagerAdapter(getSupportFragmentManager()));
        leaderBoardTabLayout.post(new Runnable() {
            @Override
            public void run() {
                leaderBoardTabLayout.setupWithViewPager(leaderBoardViewPager);
            }
        });
    }
}
