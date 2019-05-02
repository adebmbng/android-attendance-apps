package com.debam.attendance.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import com.debam.attendance.BaseActivity;
import com.debam.attendance.Config;
import com.debam.attendance.R;
import com.debam.attendance.ui.home.history.HistoryAdapter;
import com.debam.attendance.ui.home.history.HistoryFragment;
import com.debam.attendance.ui.home.home.HomeFragment;
import com.debam.attendance.ui.home.profile.ProfileFragment;
import com.debam.attendance.ui.home.schedule.ScheduleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    private ActionBar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView navigationView;


    Fragment home, history, schedule, profile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);

        toolbar = getSupportActionBar();
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        toolbar.setTitle(getString(R.string.home));

        prepareFragment();
    }

    private void prepareFragment() {
        home = HomeFragment.newInstance();
        schedule = ScheduleFragment.newInstance();
        history = HistoryFragment.newInstance();
        profile = ProfileFragment.newInstance();

        loadFragment(home);

    }


    @Override
    public void onBackPressed() {
        if (getParent() != null) {
            getParent().onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    enableActionBar();
                    toolbar.setTitle(getString(R.string.home));
                    loadFragment(home);
                    return true;
                case R.id.schedule:
                    enableActionBar();
                    toolbar.setTitle(getString(R.string.schedule));
                    loadFragment(schedule);
                    return true;
                case R.id.profile:
                    disableActionBar();
                    toolbar.setTitle(getString(R.string.profile));
                    loadFragment(profile);
                    return true;

                case R.id.history:
                    enableActionBar();
                    toolbar.setTitle(getString(R.string.history));
                    loadFragment(history);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadFragmentAfterEvent(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Config
                    .REQ_CODE_HOME:
                Log.d("onActivityResult", "back "+resultCode);
                if (resultCode == RESULT_OK) {
                    Log.d("onActivityResult", "result");
                    if (data.getStringExtra(Config.FROM_ACT).equals("home")) {
                        Log.d("onActivityResult", "home");
                        home = HomeFragment.newInstance();
                        loadFragmentAfterEvent(home);
                    } else {
                        history = HistoryFragment.newInstance();
                        loadFragmentAfterEvent(history);
                    }
                    break;
                }
            case Config
                    .REQ_CODE_HISTORY:
                Log.d("onActivityResult", "back "+resultCode);
                if (resultCode == RESULT_OK) {
                    Log.d("onActivityResult", "result");
                    if (data.getStringExtra(Config.FROM_ACT).equals("home")) {
                        Log.d("onActivityResult", "home");
                        home = HomeFragment.newInstance();
                        loadFragmentAfterEvent(home);
                    } else {
                        history = HistoryFragment.newInstance();
                        loadFragmentAfterEvent(history);
                    }
                    break;
                }
        }
    }
}
