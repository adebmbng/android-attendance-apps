package com.debam.attendance.ui.home.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.debam.attendance.BaseFragment;
import com.debam.attendance.R;
import com.debam.attendance.models.KbmModels;
import com.debam.attendance.ui.home.home.HomeAdapter;
import com.debam.attendance.ui.home.schedule.ScheduleAdapter;
import com.debam.attendance.ui.home.schedule.ScheduleContract;
import com.debam.attendance.ui.home.schedule.SchedulePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends BaseFragment implements HistoryContract.View{


    @BindView(R.id.rvHistory)
    RecyclerView rv;
    @BindView(R.id.noHistory)TextView noHistory;

    List<KbmModels> list;
    ScheduleAdapter mAdapter;
    HistoryContract.Presenter presenter;

    public static HistoryFragment newInstance(){
        HistoryFragment fragment =new HistoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        list = new ArrayList<>();
        mAdapter = new ScheduleAdapter(list, getActivity().getApplication(), getContext(), false);
        presenter = new HistoryPresenter(this, getActivity().getApplication());
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
            noHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailed() {
        Toast.makeText(getContext(), getString(R.string.general_error),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLogout() {
        baseLogout();
    }
}
