package com.debam.attendance.ui.home.home;

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
import com.debam.attendance.models.ScheduleTodayResponse;
import com.debam.attendance.services.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeContract.View {

    public static final int FULLNAME = 0;
    @BindView(R.id.rvKbm)
    RecyclerView rv;
    @BindView(R.id.rvRecent)
    RecyclerView rvRecent;
    @BindView(R.id.fullname)
    TextView tvFullname;

    @BindView(R.id.noToday)
    TextView noToday;
    @BindView(R.id.noRecent)
    TextView noRecent;

    List<KbmModels> list, listRecent;
    HomeAdapter mAdapter;
    HomeAdapter mRecentAdapter;
    HomeContract.Presenter presenter;

    public static HomeFragment newInstance() {
        HomeFragment home = new HomeFragment();
        return home;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        list = new ArrayList<>();
        listRecent = new ArrayList<>();
        mAdapter = new HomeAdapter(list, getActivity().getApplication(), true, getContext());
        mRecentAdapter = new HomeAdapter(listRecent, getActivity().getApplication(), false, getContext());
        presenter = new HomePresenter(this, getActivity().getApplication());
        ButterKnife.bind(this, view);

        presenter.initView();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mAdapter);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        rvRecent.setLayoutManager(layoutManager2);
        rvRecent.setItemAnimator(new DefaultItemAnimator());
        rvRecent.setAdapter(mRecentAdapter);

        presenter.loadList();
        return view;
    }

    public void reload() {
        presenter.loadList();
    }

    @Override
    public void initView(List<String> obj) {
        tvFullname.setText(obj.get(FULLNAME));
    }

    @Override
    public void onLogout() {
        baseLogout();
    }

    @Override
    public void onLoaded(List<KbmModels> models, List<KbmModels> recent) {
        list.clear();
        list.addAll(models);
        listRecent.clear();
        listRecent.addAll(recent);

        if(list.size()==0){
            noToday.setVisibility(View.VISIBLE);
        }

        if(listRecent.size() == 0){
            noRecent.setVisibility(View.VISIBLE);
        }

        mRecentAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {
        Log.d(Config.TAG, "error");
    }


}
