package com.example.teamproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //파이어베이스 인증
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFirebaseAuth = FirebaseAuth.getInstance();

        Button btn_logout = findViewById(R.id.btn_logout);
        Button btn_list = findViewById(R.id.btn_list);
        Button btn_goReserve = findViewById(R.id.btn_goReserve);

        Intent intent = getIntent();
        ReserveDTO reserveDTO = (ReserveDTO) intent.getSerializableExtra("reserveDTO");

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
                Intent intent = new Intent(MainActivity.this, Reserve_Activity1.class);
                intent.putExtra("reserveDTO",reserveDTO);
                startActivity(intent);
            }
        });


    }
}