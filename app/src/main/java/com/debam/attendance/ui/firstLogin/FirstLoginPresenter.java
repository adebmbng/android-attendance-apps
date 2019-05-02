package com.debam.attendance.ui.firstLogin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.debam.attendance.BaseApp;
import com.debam.attendance.Config;
import com.debam.attendance.models.BaseResponse;
import com.debam.attendance.services.NetworkService;
import com.debam.attendance.services.SharedPreferenceHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstLoginPresenter implements FirstLoginContract.Presenter {

    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;
    FirstLoginContract.View view;

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



    public FirstLoginPresenter(Application app, FirstLoginContract.View view) {
        ((BaseApp) app).getInjector().inject(this);
        this.view = view;
    }


    @Override
    public void updatePassword(String password, String newPwd, String repeatPwd) {
        String token = SharedPreferenceHelper.getString(sp, Config.SP.TOKEN, null);
        Call<BaseResponse> call = client.firstLogin(token, md5(password), md5(newPwd), md5(repeatPwd));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                switch (response.code()) {
                    case 200:
                        view.onSucces();
                        break;
                    case 400:
                        view.onPasswordNotMatch();
                        break;
                    case 401:
                        view.onFirstPasswordWrong();
                        break;
                    default:
                        view.onFailed();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                view.onFailed();
                Log.d(Config.TAG, t.toString());
            }
        });
    }
}
