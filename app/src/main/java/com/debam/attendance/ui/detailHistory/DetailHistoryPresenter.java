package com.debam.attendance.ui.detailHistory;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.debam.attendance.BaseApp;
import com.debam.attendance.Config;
import com.debam.attendance.models.BaseResponse;
import com.debam.attendance.models.DetailKBMResponse;
import com.debam.attendance.services.NetworkService;
import com.debam.attendance.services.SharedPreferenceHelper;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailHistoryPresenter implements DetailHistoryContract.Presenter {
    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;

    DetailHistoryContract.View view;

    public DetailHistoryPresenter(DetailHistoryContract.View view, Application app) {
        this.view = view;
        ((BaseApp) app).getInjector().inject(this);
    }

    @Override
    public void loadData(String id) {
        String token = SharedPreferenceHelper.getString(sp, Config.SP.TOKEN, null);
        Call<DetailKBMResponse> call = client.detailKBMHistory(token, id);
        call.enqueue(new Callback<DetailKBMResponse>() {
            @Override
            public void onResponse(Call<DetailKBMResponse> call, Response<DetailKBMResponse> response) {
                switch (response.code()) {
                    case 200:
                        view.onLoad(response.body());
                        break;
                    default:
                        view.onFailed();
                }
            }

            @Override
            public void onFailure(Call<DetailKBMResponse> call, Throwable t) {
                view.onFailed();
            }
        });
    }

    @Override
    public void setPresence(String id, String description, File photo1, File photo2, File photo3, String students) {
        MultipartBody.Part p1 = null;
        MultipartBody.Part p2 = null;
        MultipartBody.Part p3 = null;
        if(photo1!=null){
            RequestBody file = RequestBody.create(MediaType.parse("image/*"), photo1);
            p1 = MultipartBody.Part.createFormData("photo1", photo1.getName(), file);
        }
        if(photo2!=null){
            RequestBody file = RequestBody.create(MediaType.parse("image/*"), photo2);
            p2 = MultipartBody.Part.createFormData("photo2", photo2.getName(), file);
        }
        if(photo3!=null){
            RequestBody file = RequestBody.create(MediaType.parse("image/*"), photo3);
            p3 = MultipartBody.Part.createFormData("photo3", photo3.getName(), file);
        }

        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), SharedPreferenceHelper.getString(sp, Config.SP.TOKEN, null));
        RequestBody schedule = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody std = RequestBody.create(MediaType.parse("text/plain"), students);

        Call<BaseResponse> call = client.setPresence(p1, p2, p3, token, schedule, desc, std);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                switch (response.code()) {
                    case 200:
                        view.successPresence();
                        break;
                    default:
                        view.failedPresence();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                view.failedPresence();
                Log.d(Config.TAG, t.toString());
            }
        });
    }
}
