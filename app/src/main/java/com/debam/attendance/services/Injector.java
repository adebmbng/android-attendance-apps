package com.debam.attendance.services;

import android.support.v7.app.AppCompatActivity;

import com.debam.attendance.BaseActivity;
import com.debam.attendance.network.NetworkClient;
import com.debam.attendance.ui.detailHistory.DetailHistoryPresenter;
import com.debam.attendance.ui.detailHistory.StudentHistoryAdapter;
import com.debam.attendance.ui.detailKbm.DetailKBMPresenter;
import com.debam.attendance.ui.detailKbm.StudentAdapter;
import com.debam.attendance.ui.firstLogin.FirstLoginPresenter;
import com.debam.attendance.ui.forgotPassword.ForgotPasswordPresesnter;
import com.debam.attendance.ui.home.history.HistoryAdapter;
import com.debam.attendance.ui.home.history.HistoryPresenter;
import com.debam.attendance.ui.home.home.HomeAdapter;
import com.debam.attendance.ui.home.home.HomePresenter;
import com.debam.attendance.ui.home.profile.ProfilePresenter;
import com.debam.attendance.ui.home.schedule.ScheduleAdapter;
import com.debam.attendance.ui.home.schedule.SchedulePresenter;
import com.debam.attendance.ui.login.LoginActivity;
import com.debam.attendance.ui.login.LoginPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkClient.class, ModuleHelper.class})
public interface Injector {
    void inject(BaseActivity act);

    void inject(LoginPresenter pres);
    void inject(FirstLoginPresenter pres);
    void inject(HomePresenter pres);
    void inject(SchedulePresenter pres);
    void inject(HistoryPresenter pres);
    void inject(DetailKBMPresenter pres);
    void inject(DetailHistoryPresenter pres);
    void inject(ForgotPasswordPresesnter pres);
    void inject(ProfilePresenter pres);

    void inject(HomeAdapter adapter);
    void inject(HistoryAdapter adapter);
    void inject(ScheduleAdapter adapter);
    void inject(StudentAdapter adapter);
    void inject(StudentHistoryAdapter adapter);

}
