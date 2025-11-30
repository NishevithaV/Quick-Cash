package com.example.quick_cash.utils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Application;

import java.util.List;

public class EmployeeApplicationAdapter extends ArrayAdapter<Application> {

    private final LayoutInflater inflater;
    private final int resource;
    private final OnItemActionListener listener;

    public interface OnItemActionListener {
        void onMarkCompleted(Application app, int position);
        void onCardClicked(Application app, int position); // optional
    }

    public EmployeeApplicationAdapter(Context context,
                                      int resource,
                                      List<Application> apps,
                                      OnItemActionListener listener) {
        super(context, resource, apps);
        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Application app = getItem(position);
        if (app == null) return convertView;
        holder.jobTitle.setText("Loading...");
        holder.company.setText("Loading...");
        // Bind data
        JobIdMapper.getTitle(app.getJobId(), new JobIdMapper.JobInfoCallback() {
            @Override
            public void onInfoLoaded(String info) {
                holder.jobTitle.setText(info);
            }
        });
        JobIdMapper.getEmployer(app.getJobId(), new JobIdMapper.JobInfoCallback() {
            @Override
            public void onInfoLoaded(String info) {
                UserIdMapper.getName(info, new UserIdMapper.UserNameCallback() {
                    @Override
                    public void onNameLoaded(String name) {
                        holder.company.setText(name);
                    }
                });
            }
        });
        holder.status.setText(app.getStatus());

                // Style status badge
        String status = app.getStatus().toLowerCase();

        if (status.equals("accepted")) {
            holder.status.setTextColor(0xFF00FF00);
        }
        else if (status.equals("declined")) {
            holder.status.setTextColor(0xFFFF4444);
        }
        else {
            holder.status.setTextColor(0xFFFFFFFF);
        }

        // Button click
        holder.markCompleted.setOnClickListener(v -> {
            if (listener != null) listener.onMarkCompleted(app, position);
        });

        // Whole card click (optional)
        convertView.setOnClickListener(v -> {
            if (listener != null) listener.onCardClicked(app, position);
        });

        return convertView;
    }

    static class ViewHolder {
        TextView jobTitle, company, status;
        Button markCompleted;

        ViewHolder(View view) {
            jobTitle = view.findViewById(R.id.tv_job_title);
            company = view.findViewById(R.id.tv_company_name);
            status = view.findViewById(R.id.tv_application_status);
            markCompleted = view.findViewById(R.id.btn_mark_completed);
        }
    }
}
