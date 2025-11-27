package com.example.quick_cash.utils;

import android.content.Context;
import android.graphics.Color;
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

public class ApplicationAdapter extends ArrayAdapter<Application> {

    private final int resource;

    /**
     * Instantiates a new Job adapter.
     *
     * @param context  the context
     * @param resource the resource
     * @param apps     the jobs
     */
    public ApplicationAdapter(Context context, int resource, List<Application> apps) {
        super(context, resource, apps);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(resource, parent, false);
        }

        Application app = getItem(position);

        TextView jobTitleText = view.findViewById(R.id.jobTitleTextApps);
        TextView statusText = view.findViewById(R.id.statusTextApps);
        TextView applicantText = view.findViewById(R.id.applicantTextApps);

        if (app != null) {
            String status = app.getStatus();
            statusText.setText(status);
            if (status.equalsIgnoreCase("declined")) {
                statusText.setTextColor(Color.RED);
            } else if (status.equalsIgnoreCase("accepted")) {
                statusText.setTextColor(Color.GREEN);
            }
            UserIdMapper.getName(app.getApplicantId(), applicantText::setText);
            JobIdMapper.getTitle(app.getJobId(), jobTitleText::setText);
        }

        return view;
    }
}
