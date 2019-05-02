package com.debam.attendance;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class BaseActivity extends AppCompatActivity {
    @Inject
    Retrofit client;
    @Inject
    SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApp) getApplication()).getInjector().inject(this);
        Log.d(Config.TAG, client.baseUrl().toString());
    }

    public void disableActionBar() {
        getSupportActionBar().hide();
    }
    public void enableActionBar() { getSupportActionBar().show(); }
    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void log(String msg) {
        if (Config.debug)
            Log.d(Config.TAG, msg);
    }


}
