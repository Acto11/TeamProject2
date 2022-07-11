package com.example.teamproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Reserve_Activity3 extends AppCompatActivity {

    //실시간 데이터베이스
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve3);

        Button btn_reserve_Complete = (Button) findViewById(R.id.btn_reserve_Complete);

        EditText edt_carInfo = (EditText)findViewById(R.id.edt_carInfo);
        EditText edt_carNum = (EditText)findViewById(R.id.edt_carNum);
        EditText text_goods = (EditText)findViewById(R.id.text_goods);

        Intent intent = getIntent();
        ReserveDTO reserveDTO = (ReserveDTO) intent.getSerializableExtra("reserveDTO");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Visit").child("Reservation");


        btn_reserve_Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserveDTO.setCarInfo(edt_carInfo.getText().toString());
                reserveDTO.setCarInfo(edt_carNum.getText().toString());
                reserveDTO.setGoods(text_goods.getText().toString());

                mDatabaseRef.push().setValue(reserveDTO);

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
}