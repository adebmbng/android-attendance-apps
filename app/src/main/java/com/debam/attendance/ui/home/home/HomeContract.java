package com.debam.attendance.ui.home.home;

import com.debam.attendance.models.KbmModels;

import java.util.ArrayList;
import java.util.List;

public interface HomeContract {

    interface View{
        void onLoaded(List<KbmModels> models, List<KbmModels> recent);
        void onFailed();
        void initView(List<String> obj);
        void onLogout();
    }

    interface Presenter{
        void initView();
        void loadList();
        void onDetail(String id);
    }
}
