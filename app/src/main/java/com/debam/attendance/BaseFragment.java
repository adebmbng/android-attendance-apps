package com.debam.attendance;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.debam.attendance.ui.login.LoginActivity;

public class BaseFragment extends Fragment {
    public void baseLogout(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        getActivity().finish();
    }
}
