package com.example.quick_cash.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quick_cash.models.Job;
import com.example.quick_cash.R;

import java.util.List;

public class JobAdapter extends ArrayAdapter<Job> {

    private final int resource;

    /**
     * Instantiates a new Job adapter.
     *
     * @param context  the context
     * @param resource the resource
     * @param jobs     the jobs
     */
    public JobAdapter(Context context, int resource, List<Job> jobs) {
        super(context, resource, jobs);
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

        Job job = getItem(position);

        TextView titleText = view.findViewById(R.id.jobTitleText);
        TextView employerText = view.findViewById(R.id.employerText);
        TextView categoryText = view.findViewById(R.id.categoryText);

        if (job != null) {
            titleText.setText(job.getTitle());
            UserIdMapper.getName(job.getUserID(), employerText::setText);
            categoryText.setText(job.getCategory());
        }

        return view;
    }
}
