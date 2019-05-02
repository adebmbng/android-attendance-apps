package com.debam.attendance.ui.home.schedule;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.debam.attendance.BaseApp;
import com.debam.attendance.Config;
import com.debam.attendance.R;
import com.debam.attendance.models.KbmModels;
import com.debam.attendance.services.NetworkService;
import com.debam.attendance.ui.detailKbm.DetailKBMActivity;
import com.debam.attendance.ui.home.HomeActivity;
import com.debam.attendance.ui.home.history.HistoryAdapter;
import com.robertlevonyan.views.expandable.Expandable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.viewHolder> {
    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;

    List<KbmModels> list;

    Context actCtx;

    boolean schedule;

    public ScheduleAdapter(List<KbmModels> list, Application app, Context actCtx, boolean schedule) {
        this.list = list;
        ((BaseApp) app).getInjector().inject(this);
        this.actCtx = actCtx;
        this.schedule = schedule;
    }

    @NonNull
    @Override
    public ScheduleAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_card, viewGroup, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.viewHolder holder, int i) {
        holder.expansionLayout.requestLayout();
        KbmModels item = list.get(i);

        holder.startTime.setText(item.getStartTime().substring(0, 5));
        holder.endTime.setText(item.getEndTime().substring(0, 5));

        holder.title.setText(item.getName());
        holder.textClass.setText(item.getGrade() + " " + item.getMajor() + " " + item.getParam());
        holder.day.setText(item.getDay());
        holder.totalStudent.setText(ctx.getString(R.string.total_student) + " " + item.getStudents());

        if (schedule) {
            holder.tvDayTime.setText(item.getDay());
            if (i != 0 && item.getDay().equalsIgnoreCase(list.get(i - 1).getDay())) {
                holder.layoutTime.setVisibility(View.GONE);
            }
            holder.title.setText(item.getName() + " (" + item.getStartTime().substring(0, 5) + "-" + item.getEndTime().substring(0, 5) + ")");
        } else {
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String outDate, outDate1 = "";

            try {
                Date date = inFormat.parse(item.getPresenceTime());
                Date today = new Date();

                Log.d("debamdate", TimeUnit.DAYS.convert(((today.getTime() - date.getTime())), TimeUnit.MILLISECONDS) + "");
                if(TimeUnit.DAYS.convert(((today.getTime() - date.getTime())), TimeUnit.MILLISECONDS) < 7){
                    holder.btnScore.setVisibility(View.VISIBLE);
                    holder.btnScore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(ctx, DetailKBMActivity.class);
                            Log.d("ID", item.getId());
                            i.putExtra(Config.ID_KBM, item.getId());
                            i.putExtra(Config.STUDENT_SCORE, true);
                            i.putExtra(Config.SCORE_STATUS, true);
                            i.putExtra(Config.FROM_ACT, "history");
//                            i.putExtra(Config.TODAY_STATUS, false);
                            ((HomeActivity) actCtx).startActivityForResult(i, Config.REQ_CODE_HOME);
                        }
                    });
                }

                outDate = outFormat.format(date);
                if (i != 0) {
                    Date date1 = inFormat.parse(list.get(i - 1).getPresenceTime());
                    outDate1 = outFormat.format(date1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                outDate = item.getPresenceTime();
                if (i != 0)
                    outDate1 = list.get(i - 1).getPresenceTime();
            }

            holder.tvDayTime.setText(outDate);
            if (i != 0 && outDate.equalsIgnoreCase(outDate1)) {
                holder.layoutTime.setVisibility(View.GONE);
            }
        }


        holder.btnNext.setText(ctx.getString(R.string.detail));
        holder.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, DetailKBMActivity.class);
                i.putExtra(Config.ID_KBM, item.getId());
                if (!schedule) {
                    i.putExtra(Config.STUDENT_HISTORY, true);
                    i.putExtra(Config.HISTORY_STATUS, true);
                }
                else {
                    i.putExtra(Config.STUDENT_SCHEDULE, true);
                    i.putExtra(Config.SCHEDULE_STATUS, true);
                }
                ((HomeActivity) actCtx).startActivityForResult(i, Config.REQ_CODE_HISTORY);
            }
        });

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

        @BindView(R.id.tvDayTime)
        TextView tvDayTime;

        @BindView(R.id.layoutDateTime)
        LinearLayout layoutTime;
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