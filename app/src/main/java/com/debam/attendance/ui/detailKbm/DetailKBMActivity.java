package com.debam.attendance.ui.detailKbm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.debam.attendance.models.ScoreModel;
import com.debam.attendance.models.StudentsModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailKBMActivity extends BaseActivity implements DetailKBMContract.View {

    private static final int PHOTO1 = 10;
    private static final int PHOTO2 = 20;
    private static final int PHOTO3 = 30;
    DetailKBMContract.Presenter presenter;
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
    Button btnSave;

    @BindView(R.id.layoutStatus)
    LinearLayout layoutStatus;
    @BindView(R.id.tvAllStatus)
    TextView tvAllStatus;
    @BindView(R.id.timeAttendance)
    TextView timeAttendance;

    @BindView(R.id.layoutPhoto1)
    LinearLayout lPhoto1;
    @BindView(R.id.layoutPhoto2)
    LinearLayout lPhoto2;
    @BindView(R.id.layoutPhoto3)
    LinearLayout lPhoto3;

    @BindView(R.id.layoutPhotos)
    LinearLayout lPhotos;
//    @BindView(R.id.tableLayout)
//    AdaptiveTableLayout table;

    File file1, file2, file3;

    @BindView(R.id.nophotosLabel)
    TextView nophotos;

    @BindView(R.id.btnAll)
    Button btnAll;

    List<StudentsModel> list;
    StudentAdapter adapter;
    String idM;
    StdTableAdapter mAdapter;

    ProgressDialog dialog;

    boolean today, schedule, score, history, recent;

    String url1, url2, url3;

    final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_kbm_activity);
//        disableActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        presenter = new DetailKBMPresenter(this, getApplication());
        list = new ArrayList<>();
        adapter = new StudentAdapter(list, getApplication(),
                getIntent().getBooleanExtra(Config.STUDENT_TODAY, false),
                getIntent().getBooleanExtra(Config.STUDENT_SCHEDULE, false),
                getIntent().getBooleanExtra(Config.STUDENT_SCORE, false),
                getIntent().getBooleanExtra(Config.STUDENT_HISTORY, false)
                );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        idM = getIntent().getStringExtra(Config.ID_KBM);
        mAdapter = new StdTableAdapter(this, list);
//        table.setAdapter(mAdapter);
//        table.setHeaderFixed(true);
//        table.setSolidRowHeader(true);
        today = getIntent().getBooleanExtra(Config.TODAY_STATUS, false);
        schedule = getIntent().getBooleanExtra(Config.SCHEDULE_STATUS, false);
        score = getIntent().getBooleanExtra(Config.SCORE_STATUS, false);
        history =getIntent().getBooleanExtra(Config.HISTORY_STATUS, false);
        recent =getIntent().getBooleanExtra(Config.RECENT_STATUS, false);
        Log.d("ID", idM);
        Log.d("debam", today +" "+schedule);
        if (today) {
            btnAll.setVisibility(View.VISIBLE);
            presenter.loadData(idM);
        } else if(schedule){
            presenter.loadData(idM);
            layoutStatus.setVisibility(View.GONE);
            btnAll.setVisibility(View.GONE);
            subDesc.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
            lPhotos.setVisibility(View.GONE);
        } else if(score){
            presenter.loadDetail(idM);
            btnAll.setVisibility(View.GONE);
            layoutStatus.setVisibility(View.VISIBLE);
        } else if(history){
            presenter.loadDetail(idM);
            btnAll.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
            layoutStatus.setVisibility(View.VISIBLE);
//            subDesc.setVisibility(View.GONE);
//            lPhotos.setVisibility(View.GONE);
//            desc.setVisibility(View.GONE);
        } else if(recent){
            presenter.loadDetail(idM);
            layoutStatus.setVisibility(View.VISIBLE);
            btnAll.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
        }

        Glide.with(this).load(getDrawable(R.drawable.ic_add)).into(photo1);
        Glide.with(this).load(getDrawable(R.drawable.ic_add)).into(photo2);
        Glide.with(this).load(getDrawable(R.drawable.ic_add)).into(photo3);

    }

    @OnClick(R.id.btnAll)
    void presenceAll() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPresenceID(1);
        }
        adapter.notifyDataSetChanged();
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";
        File image = new File(Environment.getExternalStorageDirectory(), imageFileName);

        return image;
    }

    @OnClick(R.id.btnSave)
    void setPresence() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.send_Data));
        dialog.setCancelable(false);
        dialog.show();
        if(score){
            Gson gson = new Gson();
            List<ScoreModel> presenceModelList = new ArrayList<ScoreModel>();
            for (StudentsModel s : list
            ) {
                ScoreModel p = new ScoreModel();
                p.setNik(s.getNik());
                p.setScore(s.getScore());
                presenceModelList.add(p);
            }

            presenter.setScore(idM, gson.toJson(presenceModelList));
        } else {

            Gson gson = new Gson();
            List<PresenceModel> presenceModelList = new ArrayList<PresenceModel>();
            for (StudentsModel s : list
            ) {
                PresenceModel p = new PresenceModel();
                p.setNik(s.getNik());
                if (s.getPresenceID() == 0) {
                    showToast(getString(R.string.data_not_complete));
                    dialog.dismiss();
                    return;
                }
                p.setPresence(s.getPresenceID());
                presenceModelList.add(p);
            }
            presenter.setPresence(idM, desc.getText().toString(), file1, file2, file3, gson.toJson(presenceModelList));
        }
    }

    @OnClick(R.id.photo1)
    void loadPhoto1() {
        if (today && !schedule) showImage(PHOTO1);
        else if (!today) showFullschreen(url1);
    }

    private void showFullschreen(String url) {
        Intent i = new Intent(DetailKBMActivity.this, FullScreenPicture.class);
        i.putExtra(Config.URL_PHOTO, url);
        startActivity(i);
    }

    @OnClick(R.id.photo2)
    void loadPhoto2() {
        if (today && !schedule) showImage(PHOTO2);
        else if (!today) showFullschreen(url2);
    }

    @OnClick(R.id.photo3)
    void loadPhoto3() {
        if (today && !schedule) showImage(PHOTO3);
        else if (!today) showFullschreen(url3);
    }

    public void showImage(int reqCode) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent pictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                        //Create a file to store the image

                        switch (reqCode) {
                            case PHOTO1:
                                try {
                                    file1 = createImageFile();
                                } catch (IOException ex) {
                                    Log.e(Config.TAG, ex.toString());
                                }
                                if (file1 != null) {
                                    Uri photoURI = FileProvider.getUriForFile(DetailKBMActivity.this, getApplicationContext().getPackageName() + ".provider", file1);
                                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                            photoURI);
                                    startActivityForResult(pictureIntent,
                                            reqCode);
                                }
                                break;
                            case PHOTO2:
                                try {
                                    file2 = createImageFile();
                                } catch (IOException ex) {
                                    Log.e(Config.TAG, ex.toString());
                                }
                                if (file2 != null) {
                                    Uri photoURI = FileProvider.getUriForFile(DetailKBMActivity.this, getApplicationContext().getPackageName() + ".provider", file2);
                                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                            photoURI);
                                    startActivityForResult(pictureIntent,
                                            reqCode);
                                }
                                break;
                            case PHOTO3:
                                try {
                                    file3 = createImageFile();
                                } catch (IOException ex) {
                                    Log.e(Config.TAG, ex.toString());
                                }
                                if (file3 != null) {
                                    Uri photoURI = FileProvider.getUriForFile(DetailKBMActivity.this, getApplicationContext().getPackageName() + ".provider", file3);
                                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                            photoURI);
                                    startActivityForResult(pictureIntent,
                                            reqCode);
                                }
                        }

                    }
//                    startActivityForResult(takePicture, reqCode);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, reqCode + 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case PHOTO1:
                    if (resultCode == RESULT_OK) {
                        Log.d("path", file1.getAbsolutePath());
                        Glide.with(this).load(file1.getAbsolutePath()).into(photo1);
                        Log.d(Config.TAG, file1.getAbsolutePath());
                    }

                    break;
                case PHOTO2:
                    if (resultCode == RESULT_OK) {
                        Glide.with(this).load(file2.getAbsolutePath()).into(photo2);
                    }

                    break;
                case PHOTO3:
                    if (resultCode == RESULT_OK) {
                        Glide.with(this).load(file3.getAbsolutePath()).into(photo3);
                    }

                    break;
                case PHOTO1 + 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        file1 = new File(getRealPathFromURI(this, selectedImage));
                        Glide.with(this).load(selectedImage).into(photo1);
                    }
                    break;

                case PHOTO2 + 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        file2 = new File(getRealPathFromURI(this, selectedImage));
                        Glide.with(this).load(selectedImage).into(photo2);
                    }
                    break;

                case PHOTO3 + 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        file3 = new File(getRealPathFromURI(this, selectedImage));
                        Glide.with(this).load(selectedImage).into(photo3);

                    }
                    break;
            }
        }
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e("URI", "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onLoad(DetailKBMResponse response) {
        list.clear();
        list.addAll(response.getStds());
        adapter.notifyDataSetChanged();

        setTitle(getString(R.string.detail));
        subName.setText(response.getKbm().getName());
        subClass.setText(String.format("%s %s %s", response.getKbm().getGrade(), response.getKbm().getMajor(), response.getKbm().getParam()));
//        subDesc.setText(response.getKbm().getDescription());
        time.setText(String.format("%s, %s - %s (%d %s)", response.getKbm().getDay(), response.getKbm().getStartTime(), response.getKbm().getEndTime(), response.getKbm().getDuration(), response.getKbm().getDurationValue()));

//        mAdapter = new StdTableAdapter(this, list);
//        table.setHeaderFixed(true);
//        table.setSolidRowHeader(true);

        mAdapter.notifyDataSetChanged();

//        table.setAdapter(mAdapter);

        //    table.notifyDataSetChanged();

        if (!today) {
            desc.setText(response.getKbm().getKeterangan());
            desc.setEnabled(false);
            timeAttendance.setText(response.getKbm().getTimeAttendance());
            tvAllStatus.setText(getString(R.string.all_status, response.getHadir(), response.getIzin(), response.getSakit(), response.getAlpha()));

            if (response.getKbm().getPhoto1() != null) {
                Log.d("encode", Config.BASE_URL + Config.GETIMAGE + URLEncoder.encode(response.getKbm().getPhoto1()));
                url1 = response.getKbm().getPhoto1();
                Glide.with(this).load(Config.BASE_URL + Config.GETIMAGE + URLEncoder.encode(response.getKbm().getPhoto1())).error(Glide.with(this).load(getDrawable(R.drawable.baseline_error_24))).into(photo1);
            } else {
                lPhoto1.setVisibility(View.GONE);
            }
            if (response.getKbm().getPhoto2() != null) {
                Log.d("encode", Config.BASE_URL + Config.GETIMAGE + URLEncoder.encode(response.getKbm().getPhoto2()));
                url2 = response.getKbm().getPhoto2();
                Glide.with(this).load(Config.BASE_URL + Config.GETIMAGE + URLEncoder.encode(response.getKbm().getPhoto2())).error(Glide.with(this).load(getDrawable(R.drawable.baseline_error_24))).into(photo2);
            } else {
                lPhoto2.setVisibility(View.GONE);
            }
            if (response.getKbm().getPhoto3() != null) {
                Log.d("encode", Config.BASE_URL + Config.GETIMAGE + URLEncoder.encode(response.getKbm().getPhoto3()));
                url3 = response.getKbm().getPhoto3();
                Glide.with(this).load(Config.BASE_URL + Config.GETIMAGE + URLEncoder.encode(response.getKbm().getPhoto3())).error(Glide.with(this).load(getDrawable(R.drawable.baseline_error_24))).into(photo3);
            } else {
                lPhoto3.setVisibility(View.GONE);
            }

            if (response.getKbm().getPhoto3() == null &&
                    response.getKbm().getPhoto1() == null &&
                    response.getKbm().getPhoto2() == null) {
                nophotos.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onFailed() {
        dialog.dismiss();
        Log.d(Config.TAG, "error");
    }

    @Override
    public void successPresence() {
        dialog.dismiss();
        showToast(getString(R.string.success));
        Intent intent = new Intent();
        intent.putExtra(Config.FROM_ACT, "home");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void successScore() {
        dialog.dismiss();
        showToast(getString(R.string.success));
        Intent intent = new Intent();
        intent.putExtra(Config.FROM_ACT, getIntent().getStringExtra(Config.FROM_ACT));
        setResult(RESULT_OK, intent);
        finish();
    }

    //
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void failedPresence() {
        dialog.dismiss();
        showToast(getString(R.string.failed));
    }
}
