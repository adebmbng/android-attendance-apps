package com.debam.attendance.services;

import com.debam.attendance.models.BaseResponse;
import com.debam.attendance.models.DetailKBMResponse;
import com.debam.attendance.models.DetailTeacherResponse;
import com.debam.attendance.models.LoginResponse;
import com.debam.attendance.models.ScheduleTodayResponse;

import okhttp3.Address;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NetworkService {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login/newpwd")
    Call<BaseResponse> firstLogin(
            @Field("token") String token,
            @Field("password") String password,
            @Field("newPassword") String newPassword,
            @Field("repeatPassword") String repeatPassword
    );

    @FormUrlEncoded
    @POST("teacher/detail")
    Call<DetailTeacherResponse> detailUser(
        @Field("token") String token
    );

    @FormUrlEncoded
    @POST("forgot")
    Call<BaseResponse> forgot(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("teacher/edit")
    Call<BaseResponse> editProfile(
            @Field("token") String token,
            @Field("address") String address,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("schools/schedule/kbm/today")
    Call<ScheduleTodayResponse> todayList(
            @Field("token") String token,
            @Field("page") String page,
            @Field("size") String size
    );

    @FormUrlEncoded
    @POST("schools/schedule/kbm/tomorrow")
    Call<ScheduleTodayResponse> tomorrowList(
            @Field("token") String token,
            @Field("page") String page,
            @Field("size") String size
    );

    @FormUrlEncoded
    @POST("schools/schedule/kbm/history")
    Call<ScheduleTodayResponse> historyList(
            @Field("token") String token,
            @Field("page") String page,
            @Field("size") String size
    );

    @FormUrlEncoded
    @POST("schools/schedule/kbm")
    Call<DetailKBMResponse> detailKBM(
            @Field("token") String token,
            @Field("s_id") String s_id
    );

    @FormUrlEncoded
    @POST("subject/presences/details")
    Call<DetailKBMResponse> detailPresence(
            @Field("token") String token,
            @Field("id") String s_id
    );

    @FormUrlEncoded
    @POST("schools/schedule/kbm/detail/history")
    Call<DetailKBMResponse> detailKBMHistory(
            @Field("token") String token,
            @Field("s_id") String s_id
    );

    @Multipart
    @POST("school/presence")
    Call<BaseResponse> setPresence(
            @Part MultipartBody.Part photo1,
            @Part MultipartBody.Part photo2,
            @Part MultipartBody.Part photo3,
            @Part("token") RequestBody token,
            @Part("schedule") RequestBody schedule,
            @Part("description") RequestBody description,
            @Part("students") RequestBody students
    );

    @FormUrlEncoded
    @POST("school/presence/score")
    Call<BaseResponse> setScore(
            @Field("token") String token,
            @Field("id") String s_id,
            @Field("students")String students
    );
}
