package com.debam.attendance.ui.login;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.debam.attendance.BaseActivity;
import com.debam.attendance.BaseApp;
import com.debam.attendance.Config;
import com.debam.attendance.R;
import com.debam.attendance.models.LoginResponse;
import com.debam.attendance.network.NetworkClient;
import com.debam.attendance.services.ModuleHelper;
import com.debam.attendance.ui.firstLogin.FirstLoginActivity;
import com.debam.attendance.ui.forgotPassword.ForgotPasswordActivity;
import com.debam.attendance.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    ProgressDialog dialog;

    ArrayList<String> permissions = new ArrayList<String>();
    ArrayList<String> permissionsToRequest =  new ArrayList<String>();
    ArrayList<String> permissionsRejected = new ArrayList<String>();

    private static final int CAMERA_TAKE_REQUEST = 200;
    private final static int ALL_PERMISSIONS_RESULT = 101;

    @Override
    public void onSuccess(LoginResponse response) {
        dismissLoading();
        log("ok");
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onFirstLogin() {
        dismissLoading();
        etPwd.setText(null);
        startActivity(new Intent(this, FirstLoginActivity.class));
    }

    @Override
    public void onFailed(String msg) {
        dismissLoading();

        tilPassword.setError(msg);
        tilPassword.setErrorEnabled(true);
    }

    private void dismissLoading() {
        dialog.dismiss();
    }

    @Override
    public void onInvalidEmail() {
        log("email");
        dismissLoading();

        tilEmail.setError(getString(R.string.email_error));
        tilEmail.setErrorEnabled(true);
    }

    @Override
    public void onInvalidPassword() {
        log("pwd");
        dismissLoading();
        tilPassword.setError(getString(R.string.password_error));
        tilPassword.setErrorEnabled(true);
    }

    @Override
    public void onAlreadyLoggedIn() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    LoginContract.Presenter presenter;

    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPassword)
    TextInputEditText etPwd;
    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        presenter = new LoginPresenter(getApplication(), this);
        ButterKnife.bind(this);
        disableActionBar();

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.login_loading));
        dialog.setCancelable(false);

        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);

        mRequestPermission();
//
//        etEmail.setText("enggarwinulyohadi@gmail.com");
//        etPwd.setText("0000");
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void mRequestPermission() {
        if (checkCameraExists()) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                        ALL_PERMISSIONS_RESULT);
            }
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            String msg = "These permissions are mandatory for the application. Please allow access.";
                            showMessageOKCancel(msg,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    public boolean checkCameraExists() {
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @OnClick(R.id.btnLogin)
    void login() {
        showLoading();
        tilEmail.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
        presenter.login(etEmail.getText().toString(), etPwd.getText().toString());
    }

    private void showLoading() {

        dialog.show();
    }

    @OnClick(R.id.forgotPassword)
    void forgotPassword(){
        Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(i);
    }

}
