package com.debam.attendance.ui.detailKbm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.debam.attendance.BaseActivity;
import com.debam.attendance.Config;
import com.debam.attendance.R;

import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullScreenPicture extends BaseActivity {

    @BindView(R.id.fullImage)
    ImageView fullImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_picture);
        ButterKnife.bind(this);

        Glide.with(this).load(Config.BASE_URL + Config.GETIMAGE + URLEncoder.encode(getIntent().getStringExtra(Config.URL_PHOTO))).error(Glide.with(this).load(getDrawable(R.drawable.baseline_error_24))).into(fullImage);

    }
}
