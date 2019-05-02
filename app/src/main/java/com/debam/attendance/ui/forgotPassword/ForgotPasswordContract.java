package com.debam.attendance.ui.forgotPassword;

public interface ForgotPasswordContract {
    public interface View{
        void onEmailnotFound();
        void onSucces();
        void onFailed();
        void onEmailNotValid();
    }

    public interface Presenter{
        void forgotPassword(String email);
    }
}
