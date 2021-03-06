package com.debam.attendance.ui.home.history;

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
import com.debam.attendance.services.NetworkService;
import com.debam.attendance.ui.detailHistory.DetailHistoryActivity;
import com.debam.attendance.ui.detailKbm.DetailKBMActivity;
import com.debam.attendance.ui.home.HomeActivity;
import com.robertlevonyan.views.expandable.Expandable;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder> {
    @Inject
    NetworkService client;
    @Inject
    SharedPreferences sp;
    @Inject
    Context ctx;

    List<KbmModels> list;

    Context actCtx;

    public HistoryAdapter(List<KbmModels> list, Application app, Context actCtx) {
        this.list = list;
        ((BaseApp) app).getInjector().inject(this);
        this.actCtx = actCtx;
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


        holder.btnNext.setText(ctx.getString(R.string.detail));
        holder.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, DetailKBMActivity.class);
                i.putExtra(Config.ID_KBM, item.getId());
                i.putExtra(Config.TODAY_STATUS, false);
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



        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
