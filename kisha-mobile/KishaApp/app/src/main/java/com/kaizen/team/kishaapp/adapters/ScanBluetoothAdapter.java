package com.kaizen.team.kishaapp.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaizen.team.kishaapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class ScanBluetoothAdapter extends RecyclerView.Adapter<ScanBluetoothAdapter.BluetoothNameHolder> {
    private List<String> bluetoothDevices = new ArrayList<>();
    private View.OnClickListener listener;
    private Context context;
    public ScanBluetoothAdapter(Context context, List<String> bluetoothDevices, View.OnClickListener listener){
        this.context = context;
        this.bluetoothDevices = bluetoothDevices;
        this.listener = listener;
    }

    public void setBluetoothDevices(List<String> devices) {
        bluetoothDevices = devices;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BluetoothNameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View parentLayout = LayoutInflater.from(context).inflate(R.layout.bluetooth_selection_row, viewGroup, false);
        BluetoothNameHolder holder = new BluetoothNameHolder(parentLayout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothNameHolder holder, int position) {
        String bluetoothName = bluetoothDevices.get(position);
        holder.bluetoothNameTv.setText(bluetoothName);
        holder.parentLayout.setTag(bluetoothName);
        holder.parentLayout.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return bluetoothDevices.size();
    }

    public class BluetoothNameHolder extends RecyclerView.ViewHolder {
        private TextView bluetoothNameTv;
        private LinearLayout parentLayout;
        public BluetoothNameHolder(@NonNull View itemView) {
            super(itemView);
            bluetoothNameTv = itemView.findViewById(R.id.bluetoothNameTv);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
