package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReselistconfActivity extends AppCompatActivity {
    //private FirebaseAuth LocFirebaseAuth;
    //RealtimeDatabase 연동
    private final FirebaseDatabase conDatabaseRef = FirebaseDatabase.getInstance();
    private final DatabaseReference mRootRef = conDatabaseRef.getReference("Visit");
    private final DatabaseReference locationRef = mRootRef.child("Reservation");

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private ArrayList<Reservation> reservationList;
    private LinearLayoutManager manager;
    private ReslistconfAdapter reslistconfAdapter;
    //private ReserveDTO reservedto;
    private Reservation reservation;
//    int position;

    Button btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reselistconf);

        btnView = findViewById(R.id.btnView);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reservationList = new ArrayList<>();
        reslistconfAdapter = new ReslistconfAdapter(this, reservationList);
        recyclerView.setAdapter(reslistconfAdapter);


        databaseReference = FirebaseDatabase.getInstance().getReference("Visit").child("Reservation");

        databaseReference.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { // 실시간데이터베이스에서 데이터 받아오는 곳
                //기존 배열리스트 초기화
                reservationList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) { //반복문으로 list에 데이터 추출
                    Reservation reservation = dataSnapshot.getValue(Reservation.class); //미리만든 데이터 객체에 넣는 역할

                    reservationList.add(reservation); //담은 데이터를 배열리스트에 넣고 리사이클러뷰로 전달예정
                }
                reslistconfAdapter.notifyDataSetChanged(); //리스트 저장 및 새로고침 기능
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //에러발생시
                Log.e("Locationlist", String.valueOf(error.toException()));
            }
        });

        reslistconfAdapter = new ReslistconfAdapter(this, reservationList);
        recyclerView.setAdapter(reslistconfAdapter); //RecyclerView에 어댑터 연결

    }
}