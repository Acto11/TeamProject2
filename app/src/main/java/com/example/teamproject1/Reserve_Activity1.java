package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Reserve_Activity1 extends AppCompatActivity {
    //파이어베이스 인증
    private FirebaseAuth mFirebaseAuth;
    //실시간 데이터베이스
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve1);

        Button register = (Button) findViewById(R.id.btn_reserve1);
        Button confirm = (Button) findViewById(R.id.btn_Confirm);
        EditText edt_Email = (EditText)findViewById(R.id.edt_Email);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Visit");

        //담당자 확인버튼
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //객체생성
                ReserveDTO reserveDTO = new ReserveDTO();
                mDatabaseRef.child("UserAccount").child("7gmf0eo68vTS8q7nbwFgCu4N2Hu2").child("emailId").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        Toast.makeText(Reserve_Activity1.this, value, Toast.LENGTH_SHORT).show();
                        if(edt_Email.getText().toString().equals(value)){
                            register.setEnabled(true);
                            Toast.makeText(Reserve_Activity1.this, "담당자 확인처리 되었습니다.", Toast.LENGTH_SHORT).show();
                            HashMap reserve  = new HashMap<>();
                            reserve.put("manageNum",edt_Email);


                        }else{
                            register.setEnabled(false);
                            Toast.makeText(Reserve_Activity1.this, "담당자 정보를 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
    public void writeNewReserve(String string){
        ReserveDTO reserveDTO = new ReserveDTO();
        mDatabaseRef.push().setValue(string);
    }
}