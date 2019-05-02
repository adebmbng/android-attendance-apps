package com.debam.attendance.ui.home.schedule;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.debam.attendance.BaseApp;
import com.debam.attendance.Config;
import com.debam.attendance.models.ScheduleTodayResponse;
import com.debam.attendance.services.NetworkService;
import com.debam.attendance.services.SharedPreferenceHelper;
import com.debam.attendance.ui.home.home.HomeContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulePresenter implements ScheduleContract.Presenter {
    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;
    ScheduleContract.View view;

    public SchedulePresenter(ScheduleContract.View view, Application app) {
        ((BaseApp) app).getInjector().inject(this);
        this.view = view;
    }

    @Override
    public void loadList() {
        Call<ScheduleTodayResponse> call = client.tomorrowList(SharedPreferenceHelper.getString(sp, Config.SP.TOKEN, null), "1", "100");
        call.enqueue(new Callback<ScheduleTodayResponse>() {
            @Override
            public void onResponse(Call<ScheduleTodayResponse> call, Response<ScheduleTodayResponse> response) {
                switch (response.code()){
                    case 200:
                        view.onLoaded(response.body().getKbm());
                        break;
                    case 401:
                        SharedPreferenceHelper.setString(sp, Config.SP.TOKEN, "");
                        SharedPreferenceHelper.setSharedPreferenceBoolean(sp, Config.SP.LOGIN, false);
                        view.onLogout();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ScheduleTodayResponse> call, Throwable t) {
                view.onFailed();
            }
        });
    }

    @Override
    public void onDetail(String id) {

    }
}
