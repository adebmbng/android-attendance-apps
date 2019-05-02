package com.debam.attendance.ui.login;

import com.debam.attendance.models.LoginResponse;

public interface LoginContract {
    interface View{
        void onSuccess(LoginResponse response);
        void onFirstLogin();
        void onFailed(String msg);
        void onInvalidEmail();
        void onInvalidPassword();
        void onAlreadyLoggedIn();
    }

    interface Presenter{
        void login(String email, String pwd);
    }
}
