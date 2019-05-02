package com.debam.attendance.ui.firstLogin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;

import com.debam.attendance.BaseActivity;
import com.debam.attendance.Config;
import com.debam.attendance.R;
import com.debam.attendance.ui.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstLoginActivity extends BaseActivity implements FirstLoginContract.View {

    FirstLoginContract.Presenter presenter;

    @BindView(R.id.etOldPassword)
    TextInputEditText etOld;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.etRepeatPassword)
    TextInputEditText etRepeat;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;
    @BindView(R.id.tilOldPassword)
    TextInputLayout tilOld;
    @BindView(R.id.tilRepeatPassword)
    TextInputLayout tilRepeat;
    @BindView(R.id.btnSkip)
    TextView btnSkip;

    ProgressDialog dialog;

    @OnClick(R.id.btnSkip)
    void skip() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(getString(R.string.skip_title))
                .setMessage(getString(R.string.skip_dialoge))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(FirstLoginActivity.this, HomeActivity.class));
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login_activity);
        ButterKnife.bind(this);

        disableActionBar();
        presenter = new FirstLoginPresenter(getApplication(), this);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.login_loading));
        dialog.setCancelable(false);

        if(getIntent().getBooleanExtra(Config.EDIT_PASSWORD, false)){
            btnSkip.setVisibility(View.GONE);
            dialog.setMessage(getString(R.string.please_wait));
        }
    }

    private void dismissLoading() {
        dialog.dismiss();
    }

    private void showLoading() {

        dialog.show();
    }

    @OnClick(R.id.btnPassword)
    void update() {
        showLoading();
        tilRepeat.setErrorEnabled(false);
        tilOld.setErrorEnabled(false);
        presenter.updatePassword(
                etOld.getText().toString(),
                etPassword.getText().toString(),
                etRepeat.getText().toString()
        );
    }

    @Override
    public void onPasswordNotMatch() {
        dismissLoading();
        tilRepeat.setErrorEnabled(true);
        tilRepeat.setError(getString(R.string.not_match));
    }

    @Override
    public void onSucces() {
        dismissLoading();
        finish();
    }

    @Override
    public void onFailed() {
        dismissLoading();
        showToast(getString(R.string.general_error));
    }

    @Override
    public void onFirstPasswordWrong() {
        dismissLoading();
        tilOld.setErrorEnabled(true);
        tilOld.setError(getString(R.string.password_error));
    }
}
