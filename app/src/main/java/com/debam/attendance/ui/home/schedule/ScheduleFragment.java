package com.debam.attendance.ui.home.schedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debam.attendance.BaseFragment;
import com.debam.attendance.Config;
import com.debam.attendance.R;
import com.debam.attendance.models.KbmModels;
import com.debam.attendance.ui.home.home.HomeAdapter;
import com.debam.attendance.ui.home.home.HomeContract;
import com.debam.attendance.ui.home.home.HomePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends BaseFragment implements ScheduleContract.View {

    @BindView(R.id.rvSchedule)
    RecyclerView rv;
    @BindView(R.id.noSchedule)
    TextView noSchedule;

    List<KbmModels> list;
    ScheduleAdapter mAdapter;
    ScheduleContract.Presenter presenter;

    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_fragment, container, false);
        list = new ArrayList<>();
        mAdapter = new ScheduleAdapter(list, getActivity().getApplication(), getContext(), true);
        presenter = new SchedulePresenter(this, getActivity().getApplication());
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mAdapter);
        presenter.loadList();
        return view;
    }

    @Override
    public void onLoaded(List<KbmModels> models) {
        list.clear();
        list.addAll(models);
        mAdapter.notifyDataSetChanged();

        if(list.size() == 0){
            noSchedule.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailed() {
        Log.d(Config.TAG, "error");
    }

    @Override
    public void onLogout() {
        baseLogout();
    }
}
