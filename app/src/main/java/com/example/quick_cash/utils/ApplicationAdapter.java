package com.example.quick_cash.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Application;
import com.example.quick_cash.models.Job;

import java.util.List;

/**
 * The type Application adapter.
 */
public class ApplicationAdapter extends ArrayAdapter<Application> {

    private static class ViewHolder {
        /**
         * The Job title text.
         */
        TextView jobTitleText;
        /**
         * The Status text.
         */
        TextView statusText;
        /**
         * The Applicant text.
         */
        TextView applicantText;
        /**
         * The Bound app id.
         */
        String boundAppId;
    }

    private final int resource;

    /**
     * Instantiates a new Application adapter.
     *
     * @param context  the context
     * @param resource the resource
     * @param apps     the apps
     */
    public ApplicationAdapter(Context context, int resource, List<Application> apps) {
        super(context, resource, apps);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.jobTitleText = convertView.findViewById(R.id.jobTitleTextApps);
            holder.statusText = convertView.findViewById(R.id.statusTextApps);
            holder.applicantText = convertView.findViewById(R.id.applicantTextApps);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Application app = getItem(position);
        if (app != null) {
            holder.boundAppId = app.getJobId() + "-" + app.getApplicantId();

            holder.statusText.setText(app.getStatus());
            if (app.getStatus().equalsIgnoreCase("declined")) {
                holder.statusText.setTextColor(Color.RED);
            } else if (app.getStatus().equalsIgnoreCase("accepted")) {
                holder.statusText.setTextColor(Color.GREEN);
            }

            holder.jobTitleText.setText("Loading...");
            holder.applicantText.setText("Loading...");

            final String currentBoundId = holder.boundAppId;

            UserIdMapper.getName(app.getApplicantId(), name -> {
                if (holder.boundAppId.equals(currentBoundId)) {
                    holder.applicantText.setText(name != null ? name : "Unknown");
                }
            });

            JobIdMapper.getTitle(app.getJobId(), title -> {
                if (holder.boundAppId.equals(currentBoundId)) {
                    holder.jobTitleText.setText(title != null ? title : "Unknown");
                }
            });
        }

        return convertView;
    }
}
