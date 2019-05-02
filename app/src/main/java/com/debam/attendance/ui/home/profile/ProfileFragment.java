package com.debam.attendance.ui.home.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.debam.attendance.Config;
import com.debam.attendance.R;
import com.debam.attendance.models.TeacherModel;
import com.debam.attendance.ui.firstLogin.FirstLoginActivity;
import com.debam.attendance.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private ProfileContract.Presenter presenter;

    @BindView(R.id.name)
    TextView fullname;
    @BindView(R.id.nip)
    TextView nis;

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.tilPhone)
    TextInputLayout tilPhone;
    @BindView(R.id.tilBirthday)
    TextInputLayout tilBirthday;
    @BindView(R.id.tilSchool)
    TextInputLayout tilSchool;

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etBirthday)
    EditText etBirthday;
    @BindView(R.id.etSchool)
    EditText etSchool;

    ProgressDialog dialog;

    TeacherModel user;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @OnClick(R.id.btnLogout)
    void logOut(){
        presenter.doLogout();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this, view);
        presenter = new ProfilePresenter(this, getActivity().getApplication());
        presenter.loadProfile();

        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.please_wait));
        dialog.setCancelable(false);
        return view;
    }

    @OnClick(R.id.btnSave)
    void save(){
        dialog.show();
        user.setAddress(etBirthday.getText().toString());
        user.setPhone1(etPhone.getText().toString());
        presenter.editProfile(user);
    }

    @Override
    public void onLoaded(TeacherModel user) {
        this.user = user;
        fullname.setText(user.getFname()+" "+user.getLname());
        nis.setText(user.getNis());

        etEmail.setText(user.getEmail());
        etEmail.setEnabled(false);

        etPhone.setText(user.getPhone1());
        etBirthday.setText(user.getAddress());
        etSchool.setText(user.getSchool());

    }

    @Override
    public void onFailed() {
        if(getContext() != null) {
            Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_LONG).show();
        }
        if(dialog != null){
            dialog.dismiss();
        }
    }

    @OnClick(R.id.btnChangePassword)
    void ChangePassword(){
        Intent i = new Intent(getContext(), FirstLoginActivity.class);
        i.putExtra(Config.EDIT_PASSWORD, true);
        startActivity(i);
    }

    @Override
    public void onSuccessEdit() {
        Toast.makeText(getContext(), getString(R.string.success), Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }

    @Override
    public void onLogout() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        getActivity().finish();
    }
}
