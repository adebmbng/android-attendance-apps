package com.debam.attendance.services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.debam.attendance.Config;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleHelper {
    Application application;

    public ModuleHelper(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Context provideContext(){
        return application.getApplicationContext();
    }

}
