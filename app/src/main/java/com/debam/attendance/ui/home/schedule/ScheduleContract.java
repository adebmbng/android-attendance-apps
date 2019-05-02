package com.debam.attendance.ui.home.schedule;

import com.debam.attendance.models.KbmModels;

import java.util.List;

public interface ScheduleContract {
    interface View{
        void onLoaded(List<KbmModels> models);
        void onFailed();
        void onLogout();
    }

    interface Presenter{
        void loadList();
        void onDetail(String id);
    }
}
