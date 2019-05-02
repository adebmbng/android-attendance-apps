package com.debam.attendance.ui.detailHistory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.debam.attendance.BaseActivity;
import com.debam.attendance.Config;
import com.debam.attendance.R;
import com.debam.attendance.models.DetailKBMResponse;
import com.debam.attendance.models.PresenceModel;
import com.debam.attendance.models.StudentsModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailHistoryActivity extends BaseActivity implements DetailHistoryContract.View {

    private static final int PHOTO1 = 10;
    private static final int PHOTO2 = 20;
    private static final int PHOTO3 = 30;
    DetailHistoryContract.Presenter presenter;
    @BindView(R.id.rvStudents)
    RecyclerView rv;
    @BindView(R.id.subName)
    TextView subName;
    @BindView(R.id.subClass)
    TextView subClass;
    @BindView(R.id.subDesc)
    TextView subDesc;
    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.description)
    EditText desc;

    @BindView(R.id.photo1)
    ImageView photo1;
    @BindView(R.id.photo2)
    ImageView photo2;
    @BindView(R.id.photo3)
    ImageView photo3;

    @BindView(R.id.btnSave)
    Button btnPresence;
    @BindView(R.id.photoBase)
    LinearLayout photoBase;

    File file1, file2, file3;

    List<StudentsModel> list;
    StudentHistoryAdapter adapter;
    String idM;

    final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_kbm_activity);
//        disableActionBar();
        ButterKnife.bind(this);
        presenter = new DetailHistoryPresenter(this, getApplication());
        list = new ArrayList<>();
        adapter = new StudentHistoryAdapter(list, getApplication());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        idM = getIntent().getStringExtra(Config.ID_KBM);
        presenter.loadData(idM);
        initView();
    }

    private void initView() {
        btnPresence.setVisibility(View.GONE);
        photoBase.setVisibility(View.GONE);
        desc.setEnabled(false);
    }



    @Override
    public void onLoad(DetailKBMResponse response) {
        list.clear();
        list.addAll(response.getStds());
        adapter.notifyDataSetChanged();

        setTitle(response.getKbm().getName());
        subName.setVisibility(View.GONE);
        subName.setText(response.getKbm().getName());
        subClass.setText(String.format("%s %s %s", response.getKbm().getGrade(), response.getKbm().getMajor(), response.getKbm().getParam()));
        subDesc.setText(response.getKbm().getDescription());
        time.setText(response.getKbm().getPresenceTime());
        desc.setText(response.getKbm().getOverall());
    }

    @Override
    public void onFailed() {
        Log.d(Config.TAG, "error");
    }
    @Override
    public void successPresence() {
        showToast(getString(R.string.success));
        Intent intent = new Intent();
        intent.putExtra(Config.FROM_ACT, "history");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    @Override
    public void failedPresence() {
        showToast(getString(R.string.failed));
    }
}
