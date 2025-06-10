package com.example.teamproject_roubithome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {

    private final List<String> routineList;

    public RoutineAdapter(List<String> routineList) {
        this.routineList = routineList;
    }

    @NonNull
    @Override
    public RoutineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_routine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineAdapter.ViewHolder holder, int position) {
        holder.tvRoutineName.setText(routineList.get(position));
    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoutineName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoutineName = itemView.findViewById(R.id.tvRoutineName);
        }
    }
}