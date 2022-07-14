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

public class VisitLocationAdapter extends RecyclerView.Adapter<VisitLocationAdapter.MyViewHolder>{
    private final FirebaseDatabase conDatabaseRef = FirebaseDatabase.getInstance();
    private final DatabaseReference mRootRef = conDatabaseRef.getReference("Visit");
    private final DatabaseReference locationRef = mRootRef.child("visitlocation");

    Context context;
    ArrayList<VisitLocation> visitlocationList;

    String soldvalue; //수정전 값을 넣을 변수

   public VisitLocationAdapter(Context context, ArrayList<VisitLocation> visitlocationList) {
        this.context = context;
        this.visitlocationList = visitlocationList;
    }

    Button btnEdit, btnDelete;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_visitlocation,parent,false);
//      MyViewHolder viewHolder = new MyViewHolder(view,onItemClickListener);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         VisitLocation visitlocation  = visitlocationList.get(position);
         holder.txLoc1.setText(visitlocation.getLocation1());
         //holder.txLoc2.setText(Integer.toString(visitlocationPos.getAge()));
         holder.txLoc2.setText(visitlocation.getLocation2());
         holder.txGrade.setText(visitlocation.getGrade());
         holder.txUsed.setText(visitlocation.getUsed());
         holder.txNote.setText(visitlocation.getNote());


        //수정버튼 실행
        holder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // From ---> To의 Main이동처리
                Intent intentMain = new Intent(context, LocationActivity.class);
                intentMain.putExtra("location1", visitlocation.getLocation1());
                intentMain.putExtra("location2", visitlocation.getLocation2());
                intentMain.putExtra("grade", visitlocation.getGrade());
                intentMain.putExtra("used", visitlocation.getUsed());
                intentMain.putExtra("note", visitlocation.getNote());


                Log.d("SKey>>>", ""+visitlocation.hashCode());
                Log.d("Loc1>>>", ""+visitlocation.getLocation1());
                Log.d("Loc2>>>", ""+visitlocation.getLocation2());
                Log.d("Grade>>>", ""+visitlocation.getGrade());
                Log.d("Used>>>", ""+visitlocation.getUsed());
                Log.d("Note>>>", ""+visitlocation.getNote());

                context.startActivity(intentMain);

            }
        }); // holder.btnEdit.setOnClickListener( //수정화면 이동완료


        //삭제버튼 실행하면 해당 행의 데이터 삭제기능
        holder.btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //AlertDialog.Builder builder = new AlertDialog.Builder(locationlist.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.txLoc1.getContext());
                builder.setTitle("삭제");
                //builder.setMessage("이미 삭제된 데이터이므로 삭제 할 수 없습니다.");
                builder.setMessage("삭제를 하시겠습니까?");
                builder.setPositiveButton("예(YES)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //쿼리 초기화
                       Query query = locationRef.orderByChild("location1").equalTo(visitlocation.getLocation1());

                       query.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                   // locationRef.child(locationRef.getRef().getKey()).removeValue();
                                   dataSnapshot.getRef().removeValue(); //데이터 삭제
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {
                               Toast.makeText(context.getApplicationContext(),"error: " + error.getMessage(),
                                       Toast.LENGTH_SHORT).show();
                           }
                       }); //query.addListenerForSingleValueEvent
                    }
                }); // builder.setPositiveButton(

                builder.setNegativeButton("아니오(Cancel)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //닫기
                        Toast.makeText(context.getApplicationContext(),"삭제취소를 하였습니다..!",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }); //builder.setNegativeButton(
                builder.show();

            }
        }); //btnDelete.setOnClickListener //삭제작업 실행완료

    }

    @Override
    public int getItemCount() {
        return visitlocationList == null ? 0 : visitlocationList.size();
//      return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txLoc1, txLoc2, txGrade, txUsed, txNote;

        Button btnEdit,btnDelete;

        //public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txLoc1 = itemView.findViewById(R.id.txLoc1);
            this.txLoc2 = itemView.findViewById(R.id.txLoc2);
            this.txGrade = itemView.findViewById(R.id.txGrade);
            this.txUsed = itemView.findViewById(R.id.txUsed);
            this.txNote = itemView.findViewById(R.id.txNote);

            btnEdit = itemView.findViewById((R.id.btnEdit));
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    } //class MovieViewHolder extends

}
