package com.debam.attendance;

import android.app.Application;

import com.debam.attendance.network.NetworkClient;
import com.debam.attendance.services.DaggerInjector;
import com.debam.attendance.services.Injector;
import com.debam.attendance.services.ModuleHelper;

public class BaseApp extends Application {
    Injector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = DaggerInjector.builder()
                .networkClient(new NetworkClient())
                .moduleHelper(new ModuleHelper(this))
                .build();

    }

    public Injector getInjector() {
        return injector;
    }
}
