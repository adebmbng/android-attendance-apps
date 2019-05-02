package com.debam.attendance.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.debam.attendance.BaseApp;
import com.debam.attendance.Config;
import com.debam.attendance.R;
import com.debam.attendance.models.LoginResponse;
import com.debam.attendance.services.NetworkService;
import com.debam.attendance.services.SharedPreferenceHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;

    LoginContract.View view;

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

    public LoginPresenter(Application app, LoginContract.View view) {
        ((BaseApp) app).getInjector().inject(this);
        this.view = view;

        if(SharedPreferenceHelper.getSharedPreferenceBoolean(sp, Config.SP.LOGIN, false)){
            view.onAlreadyLoggedIn();
        }
    }

    @Override
    public void login(String email, String pwd) {

        Log.d(Config.TAG, md5("trrMrhN0Gg"));
        if (!validateData(email, pwd)) {
            return;
        }

        Call<LoginResponse> call = client.login(email, md5(pwd));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    SharedPreferenceHelper.setString(sp, Config.SP.TOKEN, response.body().getToken());
                    SharedPreferenceHelper.setString(sp, Config.SP.FIRST_NAME, response.body().getFirstName());
                    SharedPreferenceHelper.setString(sp, Config.SP.LAST_NAME, response.body().getLastName());
                    SharedPreferenceHelper.setString(sp, Config.SP.EMAIL, response.body().getEmail());
                    if(!response.body().getRole().equalsIgnoreCase( ctx.getString(R.string.teacher))){
                        view.onFailed(ctx.getString(R.string.not_allowed));
                    } else if(response.body().getFirstLogin()){
                        view.onFirstLogin();
                    } else {
                        SharedPreferenceHelper.setSharedPreferenceBoolean(sp, Config.SP.LOGIN, true);
                        view.onSuccess(response.body());
                    }
                } else {
                    view.onFailed(ctx.getString(R.string.username_or_password_wrong));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.onFailed(ctx.getString(R.string.general_error));
            }
        });
    }

    private boolean validateData(String email, String pwd) {
        if(!isEmailValid(email)){
            view.onInvalidEmail();
            return false;
        }

        if(pwd.length() < 4){
            view.onInvalidPassword();
            return false;
        }

        return true;
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
