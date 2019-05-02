package com.debam.attendance.ui.detailHistory;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.debam.attendance.BaseApp;
import com.debam.attendance.R;
import com.debam.attendance.models.StudentsModel;
import com.debam.attendance.services.NetworkService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentHistoryAdapter extends RecyclerView.Adapter<StudentHistoryAdapter.viewHolder> {

    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;

    List<StudentsModel> list;

    public StudentHistoryAdapter(List<StudentsModel> list, Application app) {
        this.list = list;
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
            h.base.setBackground(ctx.getDrawable(R.color.green));
        } else {
            h.base.setBackground(ctx.getDrawable(R.color.red));
        }
//        h.check.setVisibility(View.GONE);
//        h.cancel.setVisibility(View.GONE);
//
//        h.edit.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            h.nik.setTextColor(ctx.getColor(R.color.white));
            h.fName.setTextColor(ctx.getColor(R.color.white));
        } else {
            h.nik.setTextColor(ctx.getResources().getColor(R.color.white));
            h.fName.setTextColor(ctx.getResources().getColor(R.color.white));
        }
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
//        ImageView edit;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
