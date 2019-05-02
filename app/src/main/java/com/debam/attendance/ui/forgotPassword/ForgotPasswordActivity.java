package com.debam.attendance.ui.forgotPassword;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.debam.attendance.BaseActivity;
import com.debam.attendance.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordContract.View{

    private ForgotPasswordContract.Presenter presenter;

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;

    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);
        ButterKnife.bind(this);

        presenter = new ForgotPasswordPresesnter(getApplication(), this);
        disableActionBar();
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.please_wait));
        dialog.setCancelable(false);
    }

    @OnClick(R.id.btnPassword)
    void forgotPassword(){
        dialog.show();
        presenter.forgotPassword(etEmail.getText().toString());
    }

    @Override
    public void onEmailnotFound() {
        dialog.dismiss();
        tilEmail.setErrorEnabled(true);
        tilEmail.setError(getString(R.string.email_not_found));
    }

    @Override
    public void onSucces() {
        dialog.dismiss();
        showToast(getString(R.string.success));
        finish();
    }

    @Override
    public void onFailed() {
        dialog.dismiss();
        showToast(getString(R.string.general_error));
    }

    @Override
    public void onEmailNotValid() {
        dialog.dismiss();
        tilEmail.setErrorEnabled(true);
        tilEmail.setError(getString(R.string.email_error));
    }
}
