package com.debam.attendance.ui.detailKbm;

import com.debam.attendance.models.DetailKBMResponse;

import java.io.File;

public interface DetailKBMContract {
    interface View {
        void onLoad(DetailKBMResponse response);
        void onFailed();
        void successPresence();
        void successScore();
        void failedPresence();
    }

    interface Presenter {
        void loadData(String id);
        void loadDetail(String id);
        void setScore(String id, String students);
        void setPresence(String id, String desctiption, File photo1, File photo2, File photo3, String students);
    }
}
