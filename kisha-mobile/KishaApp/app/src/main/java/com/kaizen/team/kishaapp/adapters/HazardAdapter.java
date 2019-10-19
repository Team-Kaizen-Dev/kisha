package com.kaizen.team.kishaapp.adapters;


import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kaizen.team.kishaapp.R;
import com.kaizen.team.kishaapp.hazard.HazardCategory;
import com.kaizen.team.kishaapp.hazard.HazardGenerator;

import java.util.List;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class HazardAdapter extends RecyclerView.Adapter<HazardAdapter.HazardRowHolder> {
    private List<HazardCategory> categories = HazardGenerator.getCategories();
    private Context context;
    private View.OnClickListener listener;

    public HazardAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HazardRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(context).inflate(R.layout.main_grid_layout, parent, false);
        HazardRowHolder holder = new HazardRowHolder(parentView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HazardRowHolder holder, int position) {
        HazardCategory category = categories.get(position);
        holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, category.getImageId()));
        ImageViewCompat.setImageTintList(holder.imageView, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.image_color)));
        holder.hazardNameTv.setText(category.getName());
        holder.textViewLayout.setBackgroundColor(ContextCompat.getColor(context, category.getColorId()));
        holder.parentLayout.setTag(category);
        holder.parentLayout.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class HazardRowHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private CardView parentLayout;
        private TextView hazardNameTv;
        private LinearLayout textViewLayout;

        public HazardRowHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            hazardNameTv = itemView.findViewById(R.id.hazardNameTv);
            textViewLayout = itemView.findViewById(R.id.textViewLayout);
        }
    }
}
