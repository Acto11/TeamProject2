package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_Activity extends AppCompatActivity {

    //파이어베이스 인증
    private FirebaseAuth mFirebaseAuth;
    //실시간 데이터베이스
    private DatabaseReference mDatabaseRef;
    private EditText EtEmail,EtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Visit");

        EtEmail = findViewById(R.id.et_email);
        EtPwd = findViewById(R.id.et_pwd);


        Button btn_register = findViewById(R.id.btn_register);
        Button btn_login = findViewById(R.id.btn_login);
        ReserveDTO reserveDTO = new ReserveDTO();
        //로그인 클릭시
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = EtEmail.getText().toString();
                String strPwd = EtPwd.getText().toString();


                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //로그인 성공시
                        if(task.isSuccessful()){

                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("str",strEmail);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_SHORT).show();


                        }
                    }
                });
            }
        });

        //로그인창 회원가입 클릭시 인텐트 이동
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register_Activity.class);
                startActivity(intent);
            }
        });
    }
}