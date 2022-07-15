package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //파이어베이스 인증
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mLocationRef;
    //장소값
    private ArrayList<String> locationName = new ArrayList<String>();
    //VisitLocation 토큰값
    private ArrayList<String> locationToken = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationRef = FirebaseDatabase.getInstance().getReference("Visit").child("visitlocation");
        mFirebaseAuth = FirebaseAuth.getInstance();

        Button btn_logout = findViewById(R.id.btn_logout);
        Button btn_list = findViewById(R.id.btn_list);
        Button btn_goReserve = findViewById(R.id.btn_goReserve);
        Button btn_ReserveList = findViewById(R.id.btn_Reservelist);
        Button btn_ReservePlace = findViewById(R.id.btn_ReservePlace);

        Intent intent = getIntent();
        String email = (String) intent.getSerializableExtra("str");
        locationName.add(email);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();

                Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        //리스트
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
        //방문예약신청
        btn_goReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            locationToken.add(snapshot.getKey());
                            mLocationRef.child(snapshot.getKey()).child("location2").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String value = snapshot.getValue(String.class);
                                    locationName.add(value);
                                    Log.d("장소",value.getClass().getName());
                                    Intent intent = new Intent(MainActivity.this, Reserve_Activity1.class);
//                                    String array_spinner[] =  ;
//                                    array_spinner=new String[locationName.size()];
//                                    for(int i =0;i<locationName.size();i++){
//                                        array_spinner[i] = locationName.get(i);
//                                        Log.d("성공값",value);
//                                    }
                                    intent.putExtra("locationName",locationName);
                                    startActivity(intent);


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        btn_ReserveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Locationlist.class);
                startActivity(intent);
            }
        });
        btn_ReservePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReselistconfActivity.class);
                startActivity(intent);
            }
        });



    }
}