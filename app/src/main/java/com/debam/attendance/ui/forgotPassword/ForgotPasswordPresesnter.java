package com.debam.attendance.ui.forgotPassword;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.debam.attendance.BaseApp;
import com.debam.attendance.models.BaseResponse;
import com.debam.attendance.services.NetworkService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordPresesnter implements ForgotPasswordContract.Presenter {

    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;

    ForgotPasswordContract.View view;

    public ForgotPasswordPresesnter(Application app, ForgotPasswordContract.View view) {
        ((BaseApp) app).getInjector().inject(this);
        this.view = view;
    }

    @Override
    public void forgotPassword(String email) {
        if(!isEmailValid(email)){
            view.onEmailNotValid();
            return;
        }

        Call<BaseResponse> call = client.forgot(email);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                switch (response.code()) {
                    case 200:
                        view.onSucces();
                        break;
                    case 404:
                        view.onEmailnotFound();
                        break;
                    default:
                        view.onFailed();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                view.onFailed();
            }
        });
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
