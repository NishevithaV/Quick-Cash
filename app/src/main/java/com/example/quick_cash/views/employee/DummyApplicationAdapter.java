package com.example.quick_cash.views.employee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_cash.R;

public class DummyApplicationAdapter extends RecyclerView.Adapter<DummyApplicationAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_application_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // preview data to be replaced later
        holder.jobTitle.setText("Front-end Developer");
        holder.companyName.setText("ABC Company");
        holder.status.setText("Accepted");
    }

    @Override
    public int getItemCount() {
        // only 3 cards shown for now
        return 3;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, companyName, status;

        ViewHolder(View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.tv_job_title);
            companyName = itemView.findViewById(R.id.tv_company_name);
            status = itemView.findViewById(R.id.tv_application_status);
        }
    }
}