package com.example.teamproject1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReslistconfAdapter extends RecyclerView.Adapter<ReslistconfAdapter.MyViewHolder>{

    private final FirebaseDatabase conDatabaseRef = FirebaseDatabase.getInstance();
    private final DatabaseReference mRootRef = conDatabaseRef.getReference("Visit");
    private final DatabaseReference locationRef = mRootRef.child("Reservation");

    Context context;
    ArrayList<Reservation> reservationList;


    public ReslistconfAdapter(Context context, ArrayList<Reservation> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
    }

    Button btnConf, btnCancel;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_reslistconf,parent,false);
//      MyViewHolder viewHolder = new MyViewHolder(view,onItemClickListener);
        ReslistconfAdapter.MyViewHolder viewHolder = new ReslistconfAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReslistconfAdapter.MyViewHolder holder, int position) {
        Reservation reserPos  = reservationList.get(position);

 //       holder.txName1 .setText(reserPos.getName1());
        holder.txToken1 .setText(reserPos.getIdToken());
        holder.txStartDate.setText(reserPos.getStart_date());
//        holder.txEndDate.setText(reserPos.getEnd_date());
        holder.txCar.setText(reserPos.getCarNum());
        holder.txGoods.setText(reserPos.getGoods());
        holder.txLoc.setText(reserPos.getLocation());
//       holder.txName2.setText(reserPos.getName2());
        holder.txToken2 .setText(reserPos.getIdToken2());

    }

    @Override
    public int getItemCount() {
        return reservationList == null ? 0 : reservationList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txName1, txToken1, txStartDate, txEndDate,
                  txCar, txGoods, txLoc, txName2, txToken2;

        Button btnConf,btnCancel;

        //public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txName1 = itemView.findViewById(R.id.txName1);
            this.txToken1 = itemView.findViewById(R.id.txToken1);
            this.txStartDate = itemView.findViewById(R.id.txEndDate);
            this.txCar = itemView.findViewById(R.id.txCar);
            this.txGoods = itemView.findViewById(R.id.txGoods);
            this.txLoc = itemView.findViewById(R.id.txLoc);
            this.txName2 = itemView.findViewById(R.id.txName2);
            this.txToken2 = itemView.findViewById(R.id.txToken2);

            btnConf = itemView.findViewById((R.id.btnconf));
            btnCancel = itemView.findViewById(R.id.btnCancel);

        }
    } //class MovieViewHolder extends
}
