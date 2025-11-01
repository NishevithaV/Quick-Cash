package com.example.quick_cash.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quick_cash.Models.Job;
import com.example.quick_cash.R;

import java.util.List;

public class JobAdapter extends ArrayAdapter<Job> {

    private int resource;

    public JobAdapter(Context context, int resource, List<Job> jobs) {
        super(context, resource, jobs);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
            employerText.setText(job.getUserID());
            categoryText.setText(job.getCategory());
        }

        return view;
    }
}
