package com.example.healthyapp.ui.Medicine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.healthyapp.R;
import com.example.healthyapp.pojo.Medicine;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MedicineAdupter extends RecyclerView.Adapter<MedicineAdupter.MedicineViewHolder> {

    private List<Medicine> itemsList = new ArrayList<>();

    private Context context;
    private String lang;

    public MedicineAdupter(List<Medicine> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    public MedicineAdupter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicineViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, final int position) {

        Paper.init(context);
        lang= Paper.book().read("language","en");
        if (lang.equals("ar")){
            holder.medicine_name.setText(itemsList.get(position).getAr_name());
        }else{
            holder.medicine_name.setText(itemsList.get(position).getEn_name());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(),MedicineDetailsActivity.class);
                intent.putExtra("name",itemsList.get(position).getEn_name());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setitemsList(List<Medicine> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }

    public class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView medicine_name;
        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);

            medicine_name=itemView.findViewById(R.id.medicine_name);

        }
}}
