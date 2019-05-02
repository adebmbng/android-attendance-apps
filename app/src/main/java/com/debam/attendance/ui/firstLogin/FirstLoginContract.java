package com.debam.attendance.ui.firstLogin;

public interface FirstLoginContract {

    public interface View{
        void onPasswordNotMatch();
        void onSucces();
        void onFailed();
        void onFirstPasswordWrong();
    }

    public interface Presenter{
        void updatePassword(String password, String newPwd, String repeatPwd);

    }
}
