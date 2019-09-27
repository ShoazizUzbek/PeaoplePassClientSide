package com.example.peaoplepassclientside.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.clallbacks.AdapterCallBack;
import com.example.peaoplepassclientside.model.ResponseEmploye;
import com.example.peaoplepassclientside.visitorModel.Item;
import com.example.peaoplepassclientside.visitorModel.VisitTo;
import com.example.peaoplepassclientside.visitorModel.Visitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ReceiverSelectAdapter extends RecyclerView.Adapter<ReceiverSelectAdapter.MyViewHolder> {
    List<ResponseEmploye.EmployeList> itemList;

    Context context;
     AdapterCallBack adapterCallBack;

     public void setAdapterCallBack(AdapterCallBack adapterCallBack){
         this.adapterCallBack = adapterCallBack;
     }

    public ReceiverSelectAdapter(List<ResponseEmploye.EmployeList> drugList, Context context) {
        this.context = context;
        this.itemList = drugList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.employe_list,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final ResponseEmploye.EmployeList employeList = itemList.get(i);
        myViewHolder.textView.setText(employeList.getSurname()+" "+employeList.getMiddlename()+" "+employeList.getName());
        Font.montSerrat(context,myViewHolder.textView);
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallBack.returnEmploye(employeList);
            }
        });


    }




    @Override
    public int getItemCount(){
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.employe_layout);
            textView = itemView.findViewById(R.id.employe_name);
        }
    }
}
