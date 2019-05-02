package com.debam.attendance.ui.home.profile;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.debam.attendance.BaseApp;
import com.debam.attendance.Config;
import com.debam.attendance.models.BaseResponse;
import com.debam.attendance.models.DetailTeacherResponse;
import com.debam.attendance.models.TeacherModel;
import com.debam.attendance.services.NetworkService;
import com.debam.attendance.services.SharedPreferenceHelper;
import com.debam.attendance.ui.home.schedule.ScheduleContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {

    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;

    ProfileContract.View view;

    public ProfilePresenter(ProfileContract.View view, Application app) {
        ((BaseApp) app).getInjector().inject(this);
        this.view = view;
    }

    @Override
    public void loadProfile() {
        Call<DetailTeacherResponse> call = client.detailUser(SharedPreferenceHelper.getString(sp, Config.SP.TOKEN, null));
        call.enqueue(new Callback<DetailTeacherResponse>() {
            @Override
            public void onResponse(Call<DetailTeacherResponse> call, Response<DetailTeacherResponse> response) {
                switch (response.code()){
                    case 200:
                        view.onLoaded(response.body().getUser());
                        break;
                    case 401:
                        SharedPreferenceHelper.setString(sp, Config.SP.TOKEN, "");
                        SharedPreferenceHelper.setSharedPreferenceBoolean(sp, Config.SP.LOGIN, false);
                        view.onLogout();
                        break;
                }
            }

            @Override
            public void onFailure(Call<DetailTeacherResponse> call, Throwable t) {
                view.onFailed();
            }
        });

    }

    @Override
    public void editProfile(TeacherModel user) {
        Call<BaseResponse> call = client.editProfile(SharedPreferenceHelper.getString(sp, Config.SP.TOKEN, null), user.getAddress(), user.getPhone1());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                switch (response.code()){
                    case 200:
                        view.onSuccessEdit();
                        break;
                    case 401:
                        SharedPreferenceHelper.setString(sp, Config.SP.TOKEN, "");
                        SharedPreferenceHelper.setSharedPreferenceBoolean(sp, Config.SP.LOGIN, false);
                        view.onLogout();
                        break;
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                view.onFailed();

            }
        });
    }

    @Override
    public void doLogout() {
        SharedPreferenceHelper.setString(sp, Config.SP.TOKEN, "");
        SharedPreferenceHelper.setSharedPreferenceBoolean(sp, Config.SP.LOGIN, false);
        view.onLogout();
    }
}
