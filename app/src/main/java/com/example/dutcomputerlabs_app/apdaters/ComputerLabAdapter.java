package com.example.dutcomputerlabs_app.apdaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.models.ComputerLab;

import java.util.List;

public class ComputerLabAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<ComputerLab> list;
    public Context mContext;

    public ComputerLabAdapter(List<ComputerLab> list, Context mContext){
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.computerlab_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ComputerLab computerLab = list.get(position);
        holder.room.setText(computerLab.getName());
        holder.computers.setText(computerLab.getComputers()+"");
        holder.damaged_computer.setText(computerLab.getDamagedComputers()+"");
        holder.air_conditions.setText(computerLab.getAirCons()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class ViewHolder extends RecyclerView.ViewHolder{

    public TextView room, computers, damaged_computer, air_conditions;
    public Button btn_booking;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        room = itemView.findViewById(R.id.room_name);
        computers = itemView.findViewById(R.id.computers);
        damaged_computer = itemView.findViewById(R.id.damaged_computers);
        air_conditions = itemView.findViewById(R.id.air_conditions);
        btn_booking = itemView.findViewById(R.id.btn_booking);
    }
}