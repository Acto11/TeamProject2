package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Reserve_Activity1 extends AppCompatActivity {
    //파이어베이스 인증
    private FirebaseAuth mFirebaseAuth;
    //실시간 데이터베이스
    private DatabaseReference mDatabaseRef;
    //id 토큰값
    private ArrayList<String> checkToken = new ArrayList<String>();
    //이메일값
    private ArrayList<String> checkId = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve1);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Visit").child("UserAccount");

        Button register = (Button) findViewById(R.id.btn_reserve1);
        Button confirm = (Button) findViewById(R.id.btn_Confirm);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        EditText edt_Email = (EditText)findViewById(R.id.edt_Email);

        Intent intent = getIntent();
        ReserveDTO reserveDTO = (ReserveDTO) intent.getSerializableExtra("reserveDTO");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            checkToken.add(snapshot.getKey());
                            Log.d("타입", snapshot.getKey());
                            Log.d("결과값", snapshot.getValue().toString());
                            //checkId.add(snapshot.getValue().toString());
                        }
                        for (int i = 0; i < checkToken.size(); i++) {
                            mDatabaseRef.child(checkToken.get(i)).child("emailId").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String value = snapshot.getValue(String.class);
                                    checkId.add(value);
                                    confirm.setEnabled(true);

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
        //담당자 확인버튼
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt_Email.getText().toString();

                if (checkId.contains(email)) {
                    register.setEnabled(true);
                    Toast.makeText(Reserve_Activity1.this, "담당자 확인처리 되었습니다.", Toast.LENGTH_SHORT).show();


                } else {
                    register.setEnabled(false);
                    Toast.makeText(Reserve_Activity1.this, "담당자 정보를 확인해 주세요", Toast.LENGTH_SHORT).show();
                }

            }
        }); //confirm button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Reserve_Activity2.class);
                intent.putExtra("reserveDTO",reserveDTO);
                startActivity(intent);
            }
        });

    }

}