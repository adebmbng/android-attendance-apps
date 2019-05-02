package com.debam.attendance.ui.home.home;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.debam.attendance.BaseApp;
import com.debam.attendance.Config;
import com.debam.attendance.R;
import com.debam.attendance.models.KbmModels;
import com.debam.attendance.models.ScheduleTodayResponse;
import com.debam.attendance.services.NetworkService;
import com.debam.attendance.ui.detailKbm.DetailKBMActivity;
import com.debam.attendance.ui.home.HomeActivity;
import com.robertlevonyan.views.expandable.Expandable;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewHolder> {
    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;

    List<KbmModels> list;
    boolean today;

    Context actCtx;


    public HomeAdapter(List<KbmModels> list, Application app, boolean today, Context actCtx) {
        this.list = list;
        ((BaseApp) app).getInjector().inject(this);
        this.today = today;
        this.actCtx= actCtx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_card_view, viewGroup, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int i) {
        holder.expansionLayout.requestLayout();
        KbmModels item = list.get(i);

        holder.startTime.setText(item.getStartTime().substring(0, 5));
        holder.endTime.setText(item.getEndTime().substring(0, 5));

        holder.title.setText(item.getName());
        holder.textClass.setText(item.getGrade() + " " + item.getMajor() + " " + item.getParam());
        holder.day.setText(item.getDay());

        holder.totalStudent.setText(ctx.getString(R.string.total_student)+" "+item.getStudents());

        holder.title.setText(item.getName() + " ("+item.getStartTime().substring(0, 5)+"-"+item.getEndTime().substring(0, 5)+")");

        if (!today) {
            holder.btnScore.setVisibility(View.VISIBLE);
            holder.btnScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, DetailKBMActivity.class);
                    Log.d("ID", item.getId());
                    i.putExtra(Config.ID_KBM, item.getId());
                    i.putExtra(Config.SCORE_STATUS, true);
                    i.putExtra(Config.STUDENT_SCORE, true);
                    i.putExtra(Config.FROM_ACT, "home");
                    ((HomeActivity) actCtx).startActivityForResult(i, Config.REQ_CODE_HOME);
                }
            });

            holder.btnNext.setText(ctx.getString(R.string.detail));
            holder.btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, DetailKBMActivity.class);
                    i.putExtra(Config.ID_KBM, item.getId());
                    i.putExtra(Config.STUDENT_HISTORY, true);
                    i.putExtra(Config.RECENT_STATUS, true);
                    ((HomeActivity) actCtx).startActivityForResult(i, Config.REQ_CODE_HOME);
                }
            });
        } else {

            holder.btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, DetailKBMActivity.class);
                    Log.d("ID", item.getId());
                    i.putExtra(Config.ID_KBM, item.getId());
                    i.putExtra(Config.TODAY_STATUS, true);
                    i.putExtra(Config.STUDENT_TODAY, true);
                    ((HomeActivity) actCtx).startActivityForResult(i, Config.REQ_CODE_HOME);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textTitle)
        TextView title;
        @BindView(R.id.textClass)
        TextView textClass;
        @BindView(R.id.textDay)
        TextView day;
        @BindView(R.id.textStartTime)
        TextView startTime;
        @BindView(R.id.textEndTime)
        TextView endTime;
        @BindView(R.id.expandable)
        Expandable expansionLayout;
        @BindView(R.id.btnPresence)
        Button btnNext;
        @BindView(R.id.dateSeparator)
        TextView dateSeparator;
        @BindView(R.id.textTotalStudent)
        TextView totalStudent;

        @BindView(R.id.btnScore)
        Button btnScore;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
