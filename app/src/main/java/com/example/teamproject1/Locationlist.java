package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Locationlist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<VisitLocation> visitlocationList;
    DatabaseReference databaseReference;
    VisitLocationAdapter visitlocationAdapter;

    Button btnEdit, btnDelete;

    //휴대폰 Back버튼을 누르면 이전화면으로 돌아가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Locationlist.this, LocationActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationlist);

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        recyclerView = findViewById(R.id.recyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference("Visit").child("visitlocation");
        visitlocationList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        visitlocationAdapter = new VisitLocationAdapter(this, visitlocationList);
        recyclerView.setAdapter(visitlocationAdapter);

        databaseReference.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { // 실시간데이터베이스에서 데이터 받아오는 곳
                //기존 배열리스트 초기화
                visitlocationList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) { //반복문으로 list에 데이터 추출
                    VisitLocation visitlocation = dataSnapshot.getValue(VisitLocation.class); //미리만든 데이터 객체에 넣는 역할

                    visitlocationList.add(visitlocation); //담은 데이터를 배열리스트에 넣고 리사이클러뷰로 전달예정

                }
                visitlocationAdapter.notifyDataSetChanged(); //리스트 저장 및 새로고침 기능
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               //에러발생시
                Log.e("Locationlist", String.valueOf(error.toException()));
            }
        });

        visitlocationAdapter = new VisitLocationAdapter(this, visitlocationList);
        recyclerView.setAdapter(visitlocationAdapter); //RecyclerView에 어댑터 연결

    }
}