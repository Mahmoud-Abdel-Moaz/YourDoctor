package com.example.healthyapp.ui.Diseases;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.healthyapp.R;
import com.example.healthyapp.pojo.Disease;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;


public class DiseaseAdupter extends RecyclerView.Adapter<DiseaseAdupter.DiseaseViewHolder> {

    private List<Disease> itemsList = new ArrayList<>();

    private Context context;

    private String lang;

    public DiseaseAdupter(List<Disease> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    public DiseaseAdupter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiseaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseViewHolder holder, final int position) {

        //بشوف اللغة لو كانت ar بكتب الاسم عربي لو كانت en بكتب الاسم انجليزي

        Paper.init(context);
        lang= Paper.book().read("language","en");
        if (lang.equals("ar")){
            holder.disease_name.setText(itemsList.get(position).getAr_name());
        }else{
            holder.disease_name.setText(itemsList.get(position).getEn_name());
        }

        //عشان لما يدوس على مرض معين ياخد اسمه و يروح على صفحة التفاصيل
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context.getApplicationContext(),DiseaseDetailsActivity.class);
                intent.putExtra("name",itemsList.get(position).getEn_name());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setitemsList(List<Disease> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }

    public class DiseaseViewHolder extends RecyclerView.ViewHolder {
        TextView disease_name;
        public DiseaseViewHolder(@NonNull View itemView) {
            super(itemView);
            disease_name=itemView.findViewById(R.id.medicine_name);
        }
    }
}
