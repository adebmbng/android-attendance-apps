package com.debam.attendance.ui.detailKbm;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.debam.attendance.BaseApp;
import com.debam.attendance.R;
import com.debam.attendance.models.StudentsModel;
import com.debam.attendance.services.NetworkService;
import com.debam.attendance.ui.home.home.HomeAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.viewHolder> {

    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;

    boolean today,schedule, score, history;

    List<StudentsModel> list;

    public StudentAdapter(List<StudentsModel> list, Application app, boolean today, boolean schedule, boolean score, boolean history) {
        this.today = today;
        this.list = list;
        this.schedule = schedule;
        this.score = score;
        this.history = history;
        ((BaseApp) app).getInjector().inject(this);

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_cell, viewGroup, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder h, int i) {
        StudentsModel item = list.get(i);
        h.nik.setText(item.getNik());
        h.fName.setText(item.getFname()+" "+item.getLname());

        if(item.getPresenceID() == 1){
            h.rAlpha.setChecked(false);
            h.rAtt.setChecked(true);
            h.rIzin.setChecked(false);
            h.rSakit.setChecked(false);
        }

        if(today) {
            h.rAlpha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    h.rAlpha.setChecked(true);
                    h.rAtt.setChecked(false);
                    h.rIzin.setChecked(false);
                    h.rSakit.setChecked(false);
                    item.setPresenceID(3);
                }
            });

            h.rAtt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    h.rAlpha.setChecked(false);
                    h.rAtt.setChecked(true);
                    h.rIzin.setChecked(false);
                    h.rSakit.setChecked(false);
                    item.setPresenceID(1);
                }
            });

            h.rIzin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    h.rAlpha.setChecked(false);
                    h.rAtt.setChecked(false);
                    h.rIzin.setChecked(true);
                    h.rSakit.setChecked(false);
                    item.setPresenceID(2);
                }
            });

            h.rSakit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    h.rAlpha.setChecked(false);
                    h.rAtt.setChecked(false);
                    h.rIzin.setChecked(false);
                    h.rSakit.setChecked(true);
                    item.setPresenceID(4);
                }
            });

            h.rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Log.d("RadioButton", checkedId + "<-------");
                    item.setPresenceID(checkedId);
                }
            });
        } else if(history){
            h.rGroup.setVisibility(View.GONE);
            h.tvStatus.setVisibility(View.VISIBLE);

            switch (item.getPresenceID()){
                case 1:
                    h.tvStatus.setText(ctx.getString(R.string.hadir)+"\n("+item.getScore()+")");
                    break;
                case 2:
                    h.tvStatus.setText(ctx.getString(R.string.izin)+"\n("+item.getScore()+")");
                    break;
                case 3:
                    h.tvStatus.setText(ctx.getString(R.string.alpha)+"\n("+item.getScore()+")");
                    break;
                case 4:
                    h.tvStatus.setText(ctx.getString(R.string.sakit)+"\n("+item.getScore()+")");
                    break;
            }
        } else if(score){
            h.rGroup.setVisibility(View.GONE);
            h.etScore.setVisibility(View.VISIBLE);
            h.etScore.setText(String.valueOf(item.getScore()));
            h.etScore.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if(after == 0){
                        h.etScore.setText("0");
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    item.setScore(Integer.parseInt(h.etScore.getText().toString()));
                }
            });
        } else if(schedule){
            h.action.setVisibility(View.GONE);
        }

//        if(item.isPresence()){
//            if(item.isPresence_status()){
//                h.base.setBackground(ctx.getDrawable(R.color.green));
//            } else {
//                h.base.setBackground(ctx.getDrawable(R.color.red));
//            }
//            h.check.setVisibility(View.GONE);
//            h.cancel.setVisibility(View.GONE);
//
//            h.edit.setVisibility(View.VISIBLE);
//
//            h.edit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    item.setPresence(false);
//                    item.setPresence_status(false);
//                    notifyDataSetChanged();
//                }
//            });
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                h.nik.setTextColor(ctx.getColor(R.color.white));
//                h.fName.setTextColor(ctx.getColor(R.color.white));
//            } else {
//                h.nik.setTextColor(ctx.getResources().getColor(R.color.white));
//                h.fName.setTextColor(ctx.getResources().getColor(R.color.white));
//            }
//        } else {
//
//            h.check.setVisibility(View.VISIBLE);
//            h.cancel.setVisibility(View.VISIBLE);
//            h.edit.setVisibility(View.GONE);
//            h.base.setBackground(ctx.getDrawable(R.color.white));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                h.nik.setTextColor(ctx.getColor(R.color.textColor));
//                h.fName.setTextColor(ctx.getColor(R.color.textColor));
//            } else {
//                h.nik.setTextColor(ctx.getResources().getColor(R.color.textColor));
//                h.fName.setTextColor(ctx.getResources().getColor(R.color.textColor));
//            }
//
//            h.check.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    item.setPresence(true);
//                    item.setPresence_status(true);
//                    notifyDataSetChanged();
//                }
//            });
//
//            h.cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    item.setPresence(true);
//                    item.setPresence_status(false);
//                    notifyDataSetChanged();
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.base)
        RelativeLayout base;
        @BindView(R.id.nik)
        TextView nik;
        @BindView(R.id.fullname)
        TextView fName;
        @BindView(R.id.action)
        LinearLayout action;
//        @BindView(R.id.check)
//        ImageView check;
//        @BindView(R.id.cancel)
//        ImageView cancel;
//        @BindView(R.id.edit)
//        TextView edit;

        @BindView(R.id.radioGroup)
        RadioGroup rGroup;
        @BindView(R.id.radioAlpha)
        RadioButton rAlpha;
        @BindView(R.id.radioAtt)
        RadioButton rAtt;
        @BindView(R.id.radioIzin)
        RadioButton rIzin;
        @BindView(R.id.radioSakit)
        RadioButton rSakit;

        @BindView(R.id.status)
        TextView tvStatus;

        @BindView(R.id.etScore)
        EditText etScore;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
