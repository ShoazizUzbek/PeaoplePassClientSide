package com.example.peaoplepassclientside.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.clallbacks.AdapterCallBack;
import com.example.peaoplepassclientside.clallbacks.HomePageCallBacks;
import com.example.peaoplepassclientside.visitorModel.Item;
import com.example.peaoplepassclientside.visitorModel.VisitTo;
import com.example.peaoplepassclientside.visitorModel.Visitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class VisitorsAdapter extends RecyclerView.Adapter<VisitorsAdapter.MyViewHolder> {
    List<Item> itemList;

    Context context;
     AdapterCallBack adapterCallBack;

     public void setAdapterCallBack(AdapterCallBack adapterCallBack){
         this.adapterCallBack = adapterCallBack;
     }

    public VisitorsAdapter(List<Item> drugList, Context context) {
        this.context = context;
        this.itemList = drugList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.visitors_list_item,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Item item = itemList.get(i);
        VisitTo receiver = item.getVisitTo();

        Visitor  visitor = item.getVisitor();
        if (visitor!=null){
            Log.d("newtag", "onBindViewHolder: visitor not null");
            if (visitor.getPhoto()!=null){
                Log.d("newtag", "onBindViewHolder: photo not null");
            }
        }
       // Log.d("photo", "onBindViewHolder: "+visitor.getPhoto());
        Date date = item.getVisitDate();
        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String monthString = (String) DateFormat.format("MMM",date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time =dateFormat.format(date);
        if (monthNumber.equals("01")){
            monthString = "января";
        }else if (monthNumber.equals("02")){
            monthString = "февраля";
        }else if (monthNumber.equals("03")){
            monthString = "марта";
        }else if (monthNumber.equals("04")){
            monthString = "апреля";
        }else if (monthNumber.equals("05")){
            monthString = "мая";
        }else if (monthNumber.equals("06")){
            monthString = "июня";
        }else if (monthNumber.equals("07")){
            monthString = "июля";
        }else if (monthNumber.equals("08")){
            monthString = "августа";
        }else if (monthNumber.equals("09")){
            monthString = "сентября";
        }else if (monthNumber.equals("10")){
            monthString = "октября";
        }else if (monthNumber.equals("11")){
            monthString = "ноября";
        }else if (monthNumber.equals("12")){
            monthString = "декабря";
        }
        myViewHolder.txtDate.setText(day+" "+monthString+" в "+time);
        String state = item.getState();
        Log.d("state", "onBindViewHolder: "+item.getState());
        if (state.equals("COMPLETED")){
            myViewHolder.txtWaiting.setText("визит состоялся");
        }else if (state.equals("INCOMPLETED")){
            myViewHolder.txtWaiting.setText("визит не состоялся");
        }else if (state.equals("NEW")){
            myViewHolder.txtWaiting.setText("ожидание визита");
        }

        if (i%2 == 0){
            myViewHolder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.visitItem));
        }else {
            myViewHolder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }

        myViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallBack.getItem(item);
            }
        });

        String visitorName = item.getVisitorName()+" "+item.getVisitorSurname()+" "+item.getVisitorMiddlename();
        myViewHolder.txtVisitor.setText(visitorName);

        String receiverName = receiver.getName()+" "+receiver.getSurname()+" "+receiver.getMiddlename();
        myViewHolder.txtReceiver.setText(receiverName);
        Font.montSerrat(context,myViewHolder.txtDate);
        Font.montSerrat(context,myViewHolder.txtVisitorString);
        Font.montSerrat(context,myViewHolder.txtVisitor);
        Font.montSerrat(context,myViewHolder.txtReceiverString);
        Font.montSerrat(context,myViewHolder.txtReceiver);
        Font.montSerrat(context,myViewHolder.txtWaiting);


    }




    @Override
    public int getItemCount(){
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        TextView txtDate, txtVisitorString,txtVisitor, txtReceiverString, txtReceiver, txtWaiting;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.visitListItem);
            txtDate = itemView.findViewById(R.id.txt_date_list);
            txtVisitorString = itemView.findViewById(R.id.string_visitor);
            txtVisitor = itemView.findViewById(R.id.txt_visitor_upcoming);
            txtReceiverString = itemView.findViewById(R.id.string_receiver);
            txtReceiver  = itemView.findViewById(R.id.txtReceiverUpcoming);
            txtWaiting = itemView.findViewById(R.id.waitingString);
        }
    }
}
