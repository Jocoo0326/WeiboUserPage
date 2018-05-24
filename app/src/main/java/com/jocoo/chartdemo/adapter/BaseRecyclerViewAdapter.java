package com.jocoo.chartdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jocoo.chartdemo.adapter.AbstractViewHolder;

public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<AbstractViewHolder> {
    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
