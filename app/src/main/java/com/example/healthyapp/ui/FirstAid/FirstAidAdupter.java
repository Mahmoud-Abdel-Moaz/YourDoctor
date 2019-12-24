package com.example.healthyapp.ui.FirstAid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.healthyapp.R;
import com.example.healthyapp.pojo.FirstAid;


import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;


public class FirstAidAdupter extends RecyclerView.Adapter<FirstAidAdupter.FirstAidViewHolder> {

    private List<FirstAid> itemsList = new ArrayList<>();

    private Context context;

    String lang;

    public FirstAidAdupter(List<FirstAid> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    public FirstAidAdupter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public FirstAidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FirstAidViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FirstAidViewHolder holder, final int position) {

        Paper.init(context);
        lang= Paper.book().read("language","en");
        if (lang.equals("ar")){
            holder.firstAid_name.setText(itemsList.get(position).getAr_name());
        }else{
            holder.firstAid_name.setText(itemsList.get(position).getEn_name());
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(), FirstAidDetailesActivity.class);
                intent.putExtra("name",itemsList.get(position).getEn_name());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setitemsList(List<FirstAid> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }

    public class FirstAidViewHolder extends RecyclerView.ViewHolder {
        TextView firstAid_name;
        public FirstAidViewHolder(@NonNull View itemView) {
            super(itemView);
            firstAid_name=itemView.findViewById(R.id.medicine_name);
        }
    }
}
