package com.debam.attendance.ui.home.profile;

import com.debam.attendance.models.KbmModels;
import com.debam.attendance.models.TeacherModel;

import java.util.List;

public interface ProfileContract {
    interface View{
        void onLoaded(TeacherModel user);
        void onFailed();
        void onSuccessEdit();
        void onLogout();
    }

    interface Presenter{
        void loadProfile();
        void editProfile(TeacherModel user);
        void doLogout();
    }
}
