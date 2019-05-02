package com.debam.attendance.ui.detailHistory;

import com.debam.attendance.models.DetailKBMResponse;

import java.io.File;

public interface DetailHistoryContract {
    interface View {
        void onLoad(DetailKBMResponse response);
        void onFailed();
        void successPresence();
        void failedPresence();
    }

    interface Presenter {
        void loadData(String id);
        void setPresence(String id, String desctiption, File photo1, File photo2, File photo3, String students);
    }
}
